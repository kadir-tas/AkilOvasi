package com.company.akilovasi.ui.notification.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.databinding.ItemNotificationBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.notification.NotificationViewModel;
import com.company.akilovasi.ui.notification.callback.NotificationItemOnClick;

import java.util.ArrayList;
import java.util.List;

public class NotificationRvAdapter extends RecyclerView.Adapter<NotificationRvAdapter.NotificationViewHolder> {

    private List<Notification> notifications = new ArrayList<>(); // TO prevent nulls

    public NotificationItemOnClick notificationItemOnClick;

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemNotificationBinding notificationBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_notification, parent, false, new BindingComponent(null)
        );
        return new NotificationViewHolder(notificationBinding.getRoot(), notificationBinding);
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return notifications.isEmpty();
    }

    public Notification getNotification(int index){
        return notifications.get(index);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.binding.setOnclick(notificationItemOnClick);
        holder.binding.setNotification(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;
        NotificationViewHolder(@NonNull View itemView, ItemNotificationBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}