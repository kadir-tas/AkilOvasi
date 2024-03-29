package com.company.akilovasi.ui.notification;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.databinding.FragmentNotificationBinding;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.notification.adapters.NotificationRvAdapter;
import com.company.akilovasi.ui.notification.adapters.SwipeController;
import com.company.akilovasi.ui.notification.callback.NotificationItemOnClick;

import java.util.Objects;

public class NotificationFragment extends BaseFragment<NotificationViewModel , FragmentNotificationBinding>{
    public static final String TAG = "NotificationFragment";

    public static NotificationFragment newInstance(){
        return new NotificationFragment();
    }

    private NotificationFragment(){

    }

    private NotificationRvAdapter adapter;
    private NotificationItemOnClick notificationItemOnClick;

    @Override
    public Class<NotificationViewModel> getViewModel() {
        return NotificationViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_notification;
    }

    @Override
    public DataBindingComponent getBindingComponent() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); /*view model injected*/
        Log.d(TAG, "onCreate: Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
    }

    private void initObservers(){
        adapter = new NotificationRvAdapter();
        adapter.notificationItemOnClick = notificationItemOnClick;
        dataBinding.setAdapter(adapter);
        dataBinding.emptyView.setVisibility( View.VISIBLE );
        dataBinding.remove.setOnClickListener(v ->  viewModel.removeAllNotifications());
        dataBinding.goBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        new ItemTouchHelper(new SwipeController() {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                Notification notification = dataBinding.getAdapter().getNotification(pos);
                viewModel.deleteNotification(notification);
            }
        }).attachToRecyclerView(dataBinding.notificationsRv);
        viewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            if(notifications.size() > 0) dataBinding.emptyView.setVisibility(View.INVISIBLE);
            else dataBinding.emptyView.setVisibility(View.VISIBLE);
            dataBinding.getAdapter().fitNotifications(notifications);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof NotificationItemOnClick){
            this.notificationItemOnClick = (NotificationItemOnClick)context;
        }else{
            Log.e(TAG, "onAttach: Requires Instance of NotificationItemOnClick");
        }
    }
}
