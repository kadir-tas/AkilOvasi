package com.company.akilovasi.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.databinding.ActivityMainBinding;
import com.company.akilovasi.di.SecretPrefs;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.common.fullscreen.PlantFullImageFragment;
import com.company.akilovasi.ui.login.LoginActivity;
import com.company.akilovasi.ui.main.adapters.BannerAdapter;
import com.company.akilovasi.ui.main.adapters.PlantAdapter;
import com.company.akilovasi.ui.main.callbacks.AddPlantClick;
import com.company.akilovasi.ui.main.callbacks.ItemBannerClick;
import com.company.akilovasi.ui.main.callbacks.ItemPlantClick;
import com.company.akilovasi.ui.main.callbacks.LogoutButtonClick;
import com.company.akilovasi.ui.main.callbacks.NotificationClick;
import com.company.akilovasi.ui.main.callbacks.ProfileButtonClick;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;
import com.company.akilovasi.ui.main.fragments.profile.ProfileFragment;
import com.company.akilovasi.ui.notification.NotificationFragment;
import com.company.akilovasi.ui.notification.callback.NotificationItemOnClick;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;
import com.company.akilovasi.ui.plantanalysis.PlantAnalysisActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.company.akilovasi.util.CustomLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_TOKEN;


public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements ItemBannerClick, ItemPlantClick, AddPlantClick, LogoutButtonClick, ProfileButtonClick , NotificationClick , NotificationItemOnClick {

    private static final String TAG = "MainActivity";
    private BannerAdapter mBannerAdapter;
    private PlantAdapter mPlantAdapter;

    private RecyclerView mBannerRecyclerView;
    private RecyclerView mPlantsRecyclerView;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    Picasso picasso;

    private boolean plantsLoaded = false;
    private boolean bannersLoaded = false;

    @Override
    public Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding.waitScreen.setVisibility(View.VISIBLE);
        dataBinding.content.wrapper.setAddPlantClick(this);
        dataBinding.leftMenu.setLogoutClick(this);
        dataBinding.leftMenu.setProfileClick(this);
        dataBinding.leftMenu.setNotificationClick(this);
        dataBinding.content.wrapper.bottomAppbar.setNavigationOnClickListener(v -> dataBinding.main.openDrawer(Gravity.LEFT));
        initBannerRecyclerView();
        initPlantRecyclerView();
        subscribeObservers();

        //Upon entering the application get all the latest notifications
        //If any notification is received while app is running, it will handled by the FCMService but no top bar notifications is shown check out FCMService for more details
        //If any notification is received while app is not running, FCM will show user notification in the top bar so when the user clicks it, they will be redirect to here causing polling of the notifications
        //When notifications are polled they can be accessed NotificationRepository.getAllNotifications call if needed
        pollNotifications();

        notifyRemoteWithFcmToken();
        registerFCMNotificationTopic();
    }

    private void notifyRemoteWithFcmToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if(!task.isSuccessful() || task.getResult() == null){
                Log.e(TAG, "Firebase Error: " + task.getException());
                return;
            }

            //Register fcm token for our backend server so that it knows which device to send push notifications
            viewModel.updateFcmToken( task.getResult().getToken() ).observe(this,responseResource -> {
                switch (responseResource.status){
                    case SUCCESS:
                        Log.d(TAG, "Updated FCm Token:");
                        break;
                    case ERROR:
                        break;
                    case LOADING:
                        break;
                }
            });
        });
    }

    private void registerFCMNotificationTopic(){
        //Subscribe to this topic so whenever general push notification is fried from this topic we can get the message
        FirebaseMessaging.getInstance().subscribeToTopic("app-notifications")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed to app-notifications";
                        if (!task.isSuccessful()) {
                            msg = "Failed to subscribe app-notifications";
                        }
                        Log.d(TAG, msg);

                    }
                });
    }

    private void pollNotifications(){
        MediatorLiveData<Resource<Response<List<Notification>>>> liveData = viewModel.pollNotifications();
        liveData.observe(this, responseResource -> {
            switch (responseResource.status){
                case SUCCESS:
                    liveData.removeObservers(this);
                    Log.d(TAG, "pollNotifications: Recent notifications has been polled");
                    break;
                case ERROR:
                    liveData.removeObservers(this);
                    Log.d(TAG, "pollNotifications: Failed to poll recent notifications");
                    break;
                case LOADING:
                    break;
            }
        });
    }

    @Override
    protected DataBindingComponent getDataBindingComponent() {
        return null;
    }

    private void subscribeObservers() {
        viewModel.getAllActiveBanners()
                .observe(this, listResource -> {
                    Log.v("MSGG", listResource.message + "");
                    Log.v("MSGG", listResource.data + "");
                    Log.v("MSGG", listResource.status + "");
                    mBannerAdapter.setData(listResource.data);
                    viewModel.getAllActiveBanners().removeObservers(MainActivity.this);
                    plantsLoaded = true;
                    if (bannersLoaded) {
                        dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                    }

                });

        viewModel.getAllPlants()
                .observe(this, listResource -> {
                    if (listResource.data != null && listResource.data.size() == 0) {
                        dataBinding.content.wrapper.plantRecyclerView.noPlantFrameLayout.setVisibility(View.VISIBLE);
                        dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView.setVisibility(View.INVISIBLE);
                        bannersLoaded = true;
                        if (plantsLoaded) {
                            dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                        }
                    } else if (listResource.data != null) {
                        dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView.setVisibility(View.VISIBLE);
                        dataBinding.content.wrapper.plantRecyclerView.noPlantFrameLayout.setVisibility(View.INVISIBLE);
                        mPlantAdapter.setData(listResource.data);
                        viewModel.getAllPlants().removeObservers(MainActivity.this);
                        dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                    }
                });
    }


    /**
     * Init main banners recyclerview
     */
    private void initBannerRecyclerView() {

        mBannerRecyclerView = dataBinding.content.wrapper.recyclerView;
        mBannerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mBannerRecyclerView);
        mBannerRecyclerView.setHasFixedSize(true);

        mBannerAdapter = new BannerAdapter(this, picasso);
        mBannerRecyclerView.setAdapter(mBannerAdapter);
    }

    /**
     * Init Main user plants recyclerview
     */
    private void initPlantRecyclerView() {

        mPlantsRecyclerView = dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView;
        mPlantsRecyclerView.setLayoutManager(new CustomLayoutManager(this,2, CustomLayoutManager.VERTICAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mPlantsRecyclerView);
        mPlantsRecyclerView.setHasFixedSize(true);

        mPlantAdapter = new PlantAdapter(this, picasso);
        mPlantsRecyclerView.setAdapter(mPlantAdapter);
    }

    @Override
    public void onBannerClicked(Banner banner) {
        if(banner.getBannerLinkType().equals("link")){
            String url = banner.getBannerLink();
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }


    @Override
    public void onPlantClick(Plant plant) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, PlantHistoryFragment.newInstance(plant), PlantHistoryFragment.TAG).commit();
    }

    @Override
    public void onPlantImageClick(Long userPlantId) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantFullImageFragment.TAG);
        if(f != null){
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }

        PlantFullImageFragment fragment = new PlantFullImageFragment(PlantFullImageFragment.USER_PLANT, userPlantId);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, PlantFullImageFragment.TAG).commit();
    }

    @Override
    public void onBackPressed() {
        //TODO: this part becomes unmanagle
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        Fragment f2 = getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
        Fragment f3 = getSupportFragmentManager().findFragmentByTag(PlantFullImageFragment.TAG);
        Fragment f4 = getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
        if(f3 != null){
            Log.d(TAG, "onBackPressed: f3");
            getSupportFragmentManager().beginTransaction().remove(f3).commit();
        }else if (f != null) {
            Log.d(TAG, "onBackPressed: f");
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        } else if (f2 != null) {
            Log.d(TAG, "onBackPressed: f2");
            dataBinding.main.closeDrawer(Gravity.LEFT);
            getSupportFragmentManager().beginTransaction().remove(f2).commit();
        } else if (f4 != null) {
            Log.d(TAG, "onBackPressed: f4");
            dataBinding.main.closeDrawer(Gravity.LEFT);
            getSupportFragmentManager().beginTransaction().remove(f4).commit();
        }else if(dataBinding.main.isDrawerOpen(Gravity.LEFT)){
            dataBinding.main.closeDrawer(Gravity.LEFT);
        }
        else{
            Log.d(TAG, "onBackPressed: else");
            super.onBackPressed();
        }
    }

    @Override
    public void onAddPlantClick() {
//        dataBinding.main.openDrawer(Gravity.LEFT);
//        dataBinding.leftMenu.menu.transitionToStart();
//        dataBinding.leftMenu.menu.transitionToEnd();

//        dataBinding.main.setState(R.id.end, ConstraintSet.WRAP_CONTENT, ConstraintSet.WRAP_CONTENT);

        startActivity(new Intent(MainActivity.this, PlantCategoryActivity.class));
        finish();
    }

    @Override
    public void onLogoutButtonClicked() {

        viewModel.logout().observe(MainActivity.this, responseResource -> {
            if (responseResource != null) {
                switch (responseResource.status) {
                    case SUCCESS:
                        if (responseResource.data != null && responseResource.data.getSuccess()) {
                            secretPreferences.edit().remove(ACCESS_TOKEN).apply();
                            secretPreferences.edit().remove(REFRESH_TOKEN).apply();
                            secretPreferences.edit().remove(ApiConstants.USER_ID).apply();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case ERROR:
                        Log.e(TAG, "onChanged: Error" + responseResource.message);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case LOADING:
                        Log.d(TAG, "onChanged: Loading...");
                        break;
                }
            }
        });
    }

    @Override
    public void onProfileButtonClicked() {

        Fragment f = getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
        if (f == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ProfileFragment.newInstance(), ProfileFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProfileFragment.newInstance(), ProfileFragment.TAG).commit();
        }
    }

    @Override
    public void onNotificationButtonClicked() {
        Log.d(TAG, "onNotificationButtonClicked: ");
        Fragment f = getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
        if (f == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,NotificationFragment.newInstance(), NotificationFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, NotificationFragment.newInstance(), NotificationFragment.TAG).commit();
        }
    }

    @Override
    public void onNotificationItemClick(Notification notification) {
        Log.d(TAG, "onNotificationItemClick: ");
        switch (notification.getType()){
            case Default:
                //Do nothing
                break;
            case RemindAnalysis:
                Intent intent = new Intent(this, PlantAnalysisActivity.class);
                intent.putExtra( PlantAnalysisActivity.PARAM_USER_PLANT, notification.getUserPlantId().toString());
                /*Sending as string because PlantAnalysisActivity checks for string input*/
                startActivity(intent);
                finish();
                break;
            case RemindCare:
                //TODO:
                break;
            case AnalysisResult:
                //TODO:
                break;
            case GeneralNotification:
                //TODO:
                break;
        }
    }

    //TEST CODE
    @Override
    public void onNotificationDismissClick(Notification notification) {
        Log.d(TAG, "onNotificationDismissClick: ");
        NotificationFragment f = (NotificationFragment)getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
        if(f == null) return;
        AsyncTask.execute(() -> f.viewModel.deleteNotification(notification));
    }
}

