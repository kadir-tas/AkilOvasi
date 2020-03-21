package com.company.akilovasi.ui.plant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.databinding.ItemPlantTypeBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.plant.callbacks.ItemPlantTypeClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlantCategoryAdapter  extends RecyclerView.Adapter<PlantCategoryAdapter.PlantCategoryViewHolder>{

    private static final String TAG = "PlantCategoryAdapter";

    private List<PlantType> plantTypeList;
    private ItemPlantTypeClick itemPlantTypeClick;

    private Picasso picasso;

    public PlantCategoryAdapter(Picasso picasso){
        plantTypeList = new ArrayList<>();
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public PlantCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlantTypeBinding itemPlantTypeBinding=
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_plant_type, parent, false, new BindingComponent(picasso));
        PlantCategoryViewHolder holder = new PlantCategoryViewHolder(itemPlantTypeBinding.getRoot(), itemPlantTypeBinding);
        return holder;
    }

    public void setData(List<PlantType> plantTypes){
        this.plantTypeList = plantTypes;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantCategoryViewHolder holder, int position) {
        holder.plantTypeBinding.setPlantType( plantTypeList.get(position) );
        holder.plantTypeBinding.setItemPlantTypeClick( itemPlantTypeClick );
    }

    @Override
    public int getItemCount() {
        return plantTypeList.size();
    }

    public void setItemPlantTypeClick(ItemPlantTypeClick itemPlantTypeClick){
        this.itemPlantTypeClick = itemPlantTypeClick;
    }

    class PlantCategoryViewHolder extends RecyclerView.ViewHolder{

        public ItemPlantTypeBinding plantTypeBinding;

        public PlantCategoryViewHolder(@NonNull View itemView , ItemPlantTypeBinding itemPlantTypeBinding) {
            super(itemView);
            this.plantTypeBinding = itemPlantTypeBinding;
        }
    }
}
