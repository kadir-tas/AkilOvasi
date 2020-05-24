package com.company.akilovasi.ui.notification.adapters;

import android.util.Log;
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
    private static final String TAG = "NotificationRvAdapter";
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

    /**
     * Fits the new coming notifications to current notification
     * resulting actions is the same as setting notifications = notifications however only notifies data set about the removed data so no remove animations can play
     * @param notifications
     */
    public void fitNotifications(List<Notification> notifications) {
        if(notifications.isEmpty()){
            this.notifications.clear();
            notifyDataSetChanged();
        }
        else if(isEmpty()){
            this.notifications = notifications;
            notifyDataSetChanged();
        }else{
            List<Integer> tobeRemoved = new ArrayList<>();
            int index = 0;
            for(Notification old : this.notifications){
                if(!notifications.contains(old)){
                    tobeRemoved.add(index);

                }
                index++;
            }

            for(Integer i : tobeRemoved){
                this.notifications.remove(i.intValue());
                notifyItemRemoved(i);
            }


            int insertIndex = this.notifications.size();
            for(Notification newNotf : notifications){
                if(!this.notifications.contains(newNotf)){
                    this.notifications.add(newNotf);
                    notifyItemInserted(insertIndex);
                    insertIndex++;
                }
            }
        }
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