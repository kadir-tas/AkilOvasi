package com.company.akilovasi.databinding.adapters;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Notification;

public class NotificationBindingAdapter {
    @BindingAdapter({"notificationType"})
    public void changeColor(CardView cardView , Notification.Type type){
        switch (type){
            case Default:
                cardView.setBackgroundResource(R.color.notification_default);
                break;
            case RemindAnalysis:
                cardView.setBackgroundResource(R.color.notification_remind_analysis);
                break;
            case RemindCare:
                cardView.setBackgroundResource(R.color.notification_remind_care);
                break;
            case GeneralNotification:
                cardView.setBackgroundResource(R.color.notification_generic);
                break;
            default:
                cardView.setBackgroundResource(R.color.notification_type_error);
        }
    }
}
