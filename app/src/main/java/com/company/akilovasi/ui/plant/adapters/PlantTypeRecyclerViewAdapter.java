package com.company.akilovasi.ui.plant.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.databinding.ItemListMainBinding;
import com.company.akilovasi.databinding.ListItemPlantTypeBinding;
import com.company.akilovasi.ui.BaseAdapter;
import com.company.akilovasi.ui.plant.callbacks.PlantTypeRecyclerViewCallback;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PlantTypeRecyclerViewAdapter extends BaseAdapter<PlantTypeRecyclerViewAdapter.PlantTypeViewHolder, PlantType> {

    private List<PlantType> plantTypes;

    private final PlantTypeRecyclerViewCallback plantTypeRecyclerViewCallback;
    private LayoutInflater layoutInflater;

    public PlantTypeRecyclerViewAdapter(@NonNull PlantTypeRecyclerViewCallback plantTypeRecyclerViewCallback) {
        plantTypes = new ArrayList<>();
        this.plantTypeRecyclerViewCallback= plantTypeRecyclerViewCallback;
    }

    @Override
    public void setData(List<PlantType> plantTypes) {
        this.plantTypes = plantTypes;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public PlantTypeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        ListItemPlantTypeBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_item_plant_type, viewGroup, false);
        return new PlantTypeViewHolder(binding,plantTypeRecyclerViewCallback);
    }

    @Override
    public void onBindViewHolder(PlantTypeViewHolder viewHolder, int i) {
        viewHolder.onBind(plantTypes.get(i));
    }

    @Override
    public int getItemCount() {
        return plantTypes.size();
    }

    static class PlantTypeViewHolder extends RecyclerView.ViewHolder {

        ListItemPlantTypeBinding binding;

        private PlantTypeViewHolder(ListItemPlantTypeBinding binding, PlantTypeRecyclerViewCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onPlantTypeClicked(binding.getPlantType(), binding.imageViewCover));
        }

        public void onBind(PlantType plantType) {
            binding.setPlantType(plantType);
            binding.executePendingBindings();
        }
    }
}
