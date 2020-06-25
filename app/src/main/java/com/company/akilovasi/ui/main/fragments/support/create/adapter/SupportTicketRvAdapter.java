package com.company.akilovasi.ui.main.fragments.support.create.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.SupportTicket;
import com.company.akilovasi.databinding.ItemSupportTicketBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class SupportTicketRvAdapter extends RecyclerView.Adapter<SupportTicketRvAdapter.SuppotTicketViewHolder>{

    private List< SupportTicket > supportTickets;
    private Picasso picasso;

    public SupportTicketRvAdapter(Picasso picasso) {
        this.picasso = picasso;
        supportTickets = new ArrayList<>();
    }

    public void setData(List<SupportTicket> supportTickets) {
        this.supportTickets = supportTickets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SuppotTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSupportTicketBinding itemSupportTicketBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_support_ticket, parent, false, new BindingComponent(picasso));
        return new SuppotTicketViewHolder(itemSupportTicketBinding.getRoot(), itemSupportTicketBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SuppotTicketViewHolder holder, int position) {
        holder.binding.setSupportTicket( supportTickets.get(position) );
    }

    @Override
    public int getItemCount() {
        return supportTickets.size();
    }

    static class SuppotTicketViewHolder extends RecyclerView.ViewHolder{
        ItemSupportTicketBinding binding;

        SuppotTicketViewHolder(@NonNull View itemView, ItemSupportTicketBinding itemSupportTicketBinding) {
            super(itemView);
            this.binding = itemSupportTicketBinding;
        }
    }
}
