package com.company.akilovasi.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.databinding.ItemRvPlantMainBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseAdapter;
import com.company.akilovasi.ui.main.callbacks.ItemPlantClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends BaseAdapter<PlantAdapter.PlantViewHolder, Plant> {

    private List<Plant> plants;

    private ItemPlantClick itemPlantClick;

    private Picasso picasso;

    public PlantAdapter(ItemPlantClick itemPlantClick, Picasso picasso) {
        plants = new ArrayList<>();
        this.itemPlantClick = itemPlantClick;
        this.picasso = picasso;
    }


    @Override
    public void setData(List<Plant> list) {
        this.plants = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantAdapter.PlantViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemRvPlantMainBinding itemRvPlantMainBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_rv_plant_main, viewGroup, false, new BindingComponent(picasso));
        return new PlantAdapter.PlantViewHolder(itemRvPlantMainBinding.getRoot(), itemRvPlantMainBinding);
    }

    @Override
    public void onBindViewHolder(PlantAdapter.PlantViewHolder viewHolder, int i) {
        viewHolder.onBind(plants.get(i), itemPlantClick);
    }

    @Override
    public int getItemCount() {
        if(plants == null)
            return 0;
        return plants.size();
    }

    class PlantViewHolder extends RecyclerView.ViewHolder{

        ItemRvPlantMainBinding itemRvPlantMainBinding;

        public PlantViewHolder(@NonNull View rowView, ItemRvPlantMainBinding itemRvPlantMainBinding) {
            super(rowView);
            this.itemRvPlantMainBinding = itemRvPlantMainBinding;
        }

        public void onBind(Plant plant, ItemPlantClick itemPlantClick) {
            itemRvPlantMainBinding.setPlant(plant);
            itemRvPlantMainBinding.setItemPlantClick(itemPlantClick);
            itemRvPlantMainBinding.executePendingBindings();
        }
    }
}

