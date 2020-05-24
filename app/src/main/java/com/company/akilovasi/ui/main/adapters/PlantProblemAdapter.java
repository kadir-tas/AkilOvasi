package com.company.akilovasi.ui.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.AnalysisResult;
import com.company.akilovasi.databinding.ItemRvPlantProblemsBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseAdapter;
import com.company.akilovasi.ui.main.callbacks.PlantProblemClick;

import java.util.ArrayList;
import java.util.List;

public class PlantProblemAdapter extends BaseAdapter<PlantProblemAdapter.PlantProblemViewHolder, AnalysisResult> {
    private static final String TAG = "PlantProblemAdapter";
    private List<AnalysisResult> analysisWarnings;

    private PlantProblemClick plantProblemClick;

    public PlantProblemAdapter(PlantProblemClick plantProblemClick) {
        analysisWarnings = new ArrayList<>();
        this.plantProblemClick = plantProblemClick;
    }

    @Override
    public void setData(List<AnalysisResult> analysisWarnings) {
        this.analysisWarnings = analysisWarnings;
        notifyDataSetChanged();
    }

    public void addData(List<AnalysisResult> analysisWarnings){
        this.analysisWarnings.addAll(analysisWarnings);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantProblemAdapter.PlantProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvPlantProblemsBinding itemRvPlantProblemsBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_rv_plant_problems, parent, false, new BindingComponent(null));
        return new PlantProblemAdapter.PlantProblemViewHolder(itemRvPlantProblemsBinding.getRoot(), itemRvPlantProblemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantProblemViewHolder holder, int position) {
        holder.onBind(analysisWarnings.get(position));
    }

    @Override
    public int getItemCount() {
        if(analysisWarnings == null)
            return 0;
        return analysisWarnings.size();
    }

    public class PlantProblemViewHolder extends RecyclerView.ViewHolder {

        ItemRvPlantProblemsBinding itemRvPlantProblemsBinding;

        public PlantProblemViewHolder(@NonNull View itemView, ItemRvPlantProblemsBinding itemRvPlantProblemsBinding) {
            super(itemView);
            this.itemRvPlantProblemsBinding = itemRvPlantProblemsBinding;
        }

        public void onBind(AnalysisResult analysisResult) {
            itemRvPlantProblemsBinding.setWarning(analysisResult);
            itemRvPlantProblemsBinding.setWarningCancel(plantProblemClick);
            itemRvPlantProblemsBinding.executePendingBindings();
        }
    }
}