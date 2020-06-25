package com.company.akilovasi.databinding.adapters;

import android.util.Log;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;

import com.company.akilovasi.R;

public class SupportTicketBindingAdapter {
    private static final String TAG = "SupportTicketBindingAda";
    @BindingAdapter({"supportTicketStatus"})
    public void changeColor(CardView cardView , String type){
        Log.d(TAG, "changeColor: This called " + type);
        switch (type){
            case "created":
                cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.neon_green));
                break;
            case "completed":
                cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.blue));
                break;
            case "inprogress":
                cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.notification_remind_care));
                break;
            default:
                cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.notification_type_error));
        }
    }
}
