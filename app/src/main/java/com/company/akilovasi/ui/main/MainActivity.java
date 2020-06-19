package com.company.akilovasi.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterViewFlipper;
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
import com.company.akilovasi.ui.analysisresult.AnalysisResultActivity;
import com.company.akilovasi.ui.common.fullscreen.PlantFullImageFragment;
import com.company.akilovasi.ui.login.LoginActivity;
import com.company.akilovasi.ui.main.adapters.BannerAdapter;
import com.company.akilovasi.ui.main.adapters.BannerFlipperAdapter;
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
import com.company.akilovasi.util.OnSwipeTouchListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.company.akilovasi.util.CustomLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_TOKEN;


public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements ItemBannerClick, ItemPlantClick, AddPlantClick, LogoutButtonClick, ProfileButtonClick, NotificationClick, NotificationItemOnClick {

    private static final String TAG = "MainActivity";
    private BannerAdapter mBannerAdapter;
    private PlantAdapter mPlantAdapter;
    private BannerFlipperAdapter mBannerFlipperAdapter;

    private AdapterViewFlipper mBannerRecyclerView;
    private RecyclerView mPlantsRecyclerView;

    private int oldPos = 0;
    private boolean isAnimationDirectionToStart = true;

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
    protected DataBindingComponent getDataBindingComponent() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setSupportActionBar(dataBinding.content.wrapper.toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dataBinding.waitScreen.setVisibility(View.VISIBLE);
        dataBinding.content.wrapper.setAddPlantClick(this);
        dataBinding.leftMenu.setLogoutClick(this);
        dataBinding.leftMenu.setProfileClick(this);
        dataBinding.leftMenu.setNotificationClick(this);
        dataBinding.content.wrapper.plantRecyclerView.hamburgerMenu.setOnClickListener( v -> dataBinding.main.openDrawer(Gravity.LEFT));
       // dataBinding.content.wrapper.bottomAppbar.setNavigationOnClickListener(v -> ;
     //   initBannerRecyclerView();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void subscribeObservers() {
       /* viewModel.getAllActiveBanners()
                .observe(this, listResource -> {
                    mBannerFlipperAdapter.setData(listResource.data);
                    viewModel.getAllActiveBanners().removeObservers(MainActivity.this);
                    bannersLoaded = true;
                    if (plantsLoaded) {
                        dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                    }

                });
*/
        viewModel.getAllPlants()
                .observe(this, listResource -> {
                    if (listResource.data != null && listResource.data.size() == 0) {
                        dataBinding.content.wrapper.plantRecyclerView.noPlantFrameLayout.setVisibility(View.VISIBLE);
                        dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView.setVisibility(View.INVISIBLE);
                        plantsLoaded = true;
                        if (bannersLoaded) {
                            dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                        }
                    } else if (listResource.data != null) {
                        dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView.setVisibility(View.VISIBLE);
                        dataBinding.content.wrapper.plantRecyclerView.noPlantFrameLayout.setVisibility(View.INVISIBLE);
                        mPlantAdapter.setData(listResource.data);
                        viewModel.getAllPlants().removeObservers(MainActivity.this);
                        dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "subscribeObservers: Plants Loaded");
                    }
                });
    }

    /**
     * Init main banners recyclerview
     */
    private void initBannerRecyclerView() {
      //  mBannerRecyclerView = dataBinding.content.wrapper.recyclerView;

        //creating adapter object
        mBannerFlipperAdapter = new BannerFlipperAdapter(this,picasso);


        //adding it to adapterview flipper
        mBannerRecyclerView.setAdapter(mBannerFlipperAdapter);
        mBannerRecyclerView.setFlipInterval(8000);
        mBannerRecyclerView.startFlipping();

        mBannerRecyclerView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeRight() {
                Log.d(TAG, "onSwipeRight: ");
            }

            @Override
            public void onSwipeLeft() {
                Log.d(TAG, "onSwipeLeft: ");
            }

            @Override
            public void onSwipeTop() {
                Log.d(TAG, "onSwipeTop: ");
            }

            @Override
            public void onSwipeBottom() {
                Log.d(TAG, "onSwipeBottom: ");
            }
        });
    }

    /**
     * Init Main user plants recyclerview
     */
    private void initPlantRecyclerView() {

        mPlantsRecyclerView = dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView;
        //mPlantsRecyclerView.setLayoutManager( new GridLayoutManager(this ,2) );
        mPlantsRecyclerView.setLayoutManager(new CustomLayoutManager(this, 2, CustomLayoutManager.VERTICAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mPlantsRecyclerView);
        mPlantsRecyclerView.setHasFixedSize(true);

        //Keeping the last item position to manage the onclick of the plant. See onPlantClick to understand this logic
        mPlantsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               /* View v = snapHelper.findSnapView(recyclerView.getLayoutManager());
                if (v != null) {
                    oldPos = Objects.requireNonNull(recyclerView.getLayoutManager()).getPosition(v);
                }*/
//                if(dataBinding.content.wrapper.motionLayout.getProgress() < 0.3){
//                    recyclerView.smoothScrollToPosition(0);
//                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mPlantAdapter = new PlantAdapter(this, picasso);
        mPlantsRecyclerView.setAdapter(mPlantAdapter);
    }

    private void notifyRemoteWithFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful() || task.getResult() == null) {
                Log.e(TAG, "Firebase Error: " + task.getException());
                return;
            }

            //Register fcm token for our backend server so that it knows which device to send push notifications
            viewModel.updateFcmToken(task.getResult().getToken()).observe(this, responseResource -> {
                switch (responseResource.status) {
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

    private void registerFCMNotificationTopic() {
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

    private void pollNotifications() {
        MediatorLiveData<Resource<Response<List<Notification>>>> liveData = viewModel.pollNotifications();
        liveData.observe(this, responseResource -> {
            switch (responseResource.status) {
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
    public void onBannerClicked(Banner banner) {
        if (banner.getBannerLinkType().equals("link")) {
            String url = banner.getBannerLink();
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }


    @Override
    public void onPlantClick(Plant plant, int position) {

        //This if check for snap to clicked item.
        // Current layout is grid layout with 2 span. Thus, I checked also the oldPos + 1. Because each snap contains 2 items
        if (position != oldPos && position != oldPos + 1) {
            mPlantsRecyclerView.smoothScrollToPosition(position);
            oldPos = position;
        }
        else {
            Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
            if (f != null) {
                getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, PlantHistoryFragment.newInstance(plant), PlantHistoryFragment.TAG).commit();
        }
    }

    @Override
    public void onPlantImageClick(Long userPlantId) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantFullImageFragment.TAG);
        if (f != null) {
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
        if (f3 != null) {
            Log.d(TAG, "onBackPressed: f3");
            getSupportFragmentManager().beginTransaction().remove(f3).commit();
        } else if (f != null) {
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
        } else if (dataBinding.main.isDrawerOpen(Gravity.LEFT)) {
            dataBinding.main.closeDrawer(Gravity.LEFT);
        } else {
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
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, NotificationFragment.newInstance(), NotificationFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, NotificationFragment.newInstance(), NotificationFragment.TAG).commit();
        }
    }

    @Override
    public void onNotificationItemClick(Notification notification) {
        Log.d(TAG, "onNotificationItemClick: " + notification.getType());
        switch (notification.getType()) {
            case Default:
                //Do nothing
                break;
            case RemindAnalysis:{
                Intent intent = new Intent(this, PlantAnalysisActivity.class);
                intent.putExtra(PlantAnalysisActivity.PARAM_USER_PLANT, notification.getUserPlantId().toString());
                /*Sending as string because PlantAnalysisActivity checks for string input*/
                startActivity(intent);
                finish();
            }break;
            case RemindCare:{
                LiveData<Resource<List<Plant>>> liveData =  viewModel.getAllPlants();
                liveData.observe(this, listResource -> {
                    if(listResource.data != null){
                        for(Plant p : listResource.data)
                            if(p.getUserPlantId().equals( notification.getUserPlantId() ))
                            {
                                Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
                                if (f != null) {
                                    getSupportFragmentManager().beginTransaction().remove(f).commit();
                                }
                                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, PlantHistoryFragment.newInstance(p), PlantHistoryFragment.TAG).commit();
                                liveData.removeObservers(this);
                                break;
                            }
                    }
                });
            }
            break;
            case AnalysisResult:{
                Intent intent = new Intent(MainActivity.this, AnalysisResultActivity.class);
                intent.putExtra(AnalysisResultActivity.PARAM_USER_PLANT_HISTORY, notification.getUserPlantId());
                startActivity(intent);
                finish();
            }
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
        NotificationFragment f = (NotificationFragment) getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
        if (f == null) return;
        AsyncTask.execute(() -> f.viewModel.deleteNotification(notification));
    }
}

