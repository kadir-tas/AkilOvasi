package com.company.akilovasi.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.databinding.ItemRvPlantHistoryBinding;
import com.company.akilovasi.ui.BaseAdapter;
import com.company.akilovasi.ui.main.callbacks.AnalysisCallback;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class PlantHistoryAdapter extends BaseAdapter<PlantHistoryAdapter.PlantHistoryViewHolder, PlantHistory> {

    private List<PlantHistory> plantHistories;

    public PlantHistoryAdapter() {
        plantHistories = new ArrayList<>();
    }

    @Override
    public void setData(List<PlantHistory> plantHistories) {
        this.plantHistories = plantHistories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPlantHistoryBinding itemRvPlantHistoryBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_rv_plant_history, parent, false);
        return new PlantHistoryAdapter.PlantHistoryViewHolder(itemRvPlantHistoryBinding.getRoot(), itemRvPlantHistoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantHistoryViewHolder holder, int position) {
        holder.onBind(plantHistories.get(position));
    }

    @Override
    public int getItemCount() {
        if(plantHistories == null)
            return 0;
        return plantHistories.size();
    }


    public class PlantHistoryViewHolder extends RecyclerView.ViewHolder {

        ItemRvPlantHistoryBinding itemRvPlantHistoryBinding;

        public PlantHistoryViewHolder(@NonNull View itemView, ItemRvPlantHistoryBinding itemRvPlantHistoryBinding) {
            super(itemView);
            this.itemRvPlantHistoryBinding = itemRvPlantHistoryBinding;
        }

        public void onBind(PlantHistory plantHistory) {
            itemRvPlantHistoryBinding.setPlantHistory(plantHistory);
            itemRvPlantHistoryBinding.executePendingBindings();
        }
    }
}
