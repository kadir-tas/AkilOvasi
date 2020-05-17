package com.company.akilovasi.ui.common.fullscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;

import com.company.akilovasi.R;
import com.company.akilovasi.databinding.FragmentFullImageBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class PlantFullImageFragment extends BaseFragment<PlantFullImageViewModel, FragmentFullImageBinding> {

    public static final String TAG = "PlantFullImageFragment";
    public static String USER_PLANT ="user-plant";
    public static String USER_PLANT_HISTORY ="user-plant-history";

    @Inject
    Picasso picasso;

    private String type;
    private Long id;

    public PlantFullImageFragment(String type, Long id){
        this.type = type;
        this.id = id;
    }

    @Override
    public Class<PlantFullImageViewModel> getViewModel() {
        return PlantFullImageViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if(type.equals(USER_PLANT)){
            dataBinding.setPlant(id);
        }else if(type.equals(USER_PLANT_HISTORY)){
            dataBinding.setHistoricalPlant(id);
        }

        return v;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_full_image;
    }

    @Override
    public DataBindingComponent getBindingComponent() {
        return new BindingComponent(picasso);
    }
}
