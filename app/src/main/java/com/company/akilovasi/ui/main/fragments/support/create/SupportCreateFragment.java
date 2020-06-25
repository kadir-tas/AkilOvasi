package com.company.akilovasi.ui.main.fragments.support.create;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.adapters.SpinnerBindingAdapter;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.databinding.FragmentSupportBinding;
import com.company.akilovasi.databinding.FragmentSupportCreateBinding;
import com.company.akilovasi.ui.BaseFragment;

import java.util.List;

public class SupportCreateFragment extends BaseFragment<SupportCreateFragmentViewModel, FragmentSupportCreateBinding> {
    private static final String TAG = "SupportCreateFragment";
    public static SupportCreateFragment newInstance(){
        return new SupportCreateFragment();
    }

    @Override
    public Class<SupportCreateFragmentViewModel> getViewModel() {
        return SupportCreateFragmentViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_support_create;
    }

    @Override
    public DataBindingComponent getBindingComponent() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initObservers();
        return dataBinding.getRoot();
    }

    private void fillPlantSpinner(List<Plant> plants){
        final Spinner plantNameSpinner = dataBinding.plantNameSpinner;
        final ArrayAdapter<SpinnerContainer> pairArrayAdapter = new ArrayAdapter<>( getContext(), R.layout.support_simple_spinner_dropdown_item );

        for (Plant p : plants) {
            pairArrayAdapter.add(new SpinnerContainer( p.getUserPlantName() , p.getId() ));
        }

        plantNameSpinner.setAdapter(pairArrayAdapter);
    }

    private void initObservers(){
        dataBinding.setLoading(true);

        final LiveData<Resource<List<Plant>>> allUserPlants = viewModel.getAllUserPlants();
        allUserPlants.observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status){
                case SUCCESS:
                    if(listResource.data != null){
                        fillPlantSpinner(listResource.data);
                    }
                    dataBinding.setLoading(false);
                    allUserPlants.removeObservers(getViewLifecycleOwner());
                    break;
                case ERROR:
                    dataBinding.setLoading(false);
                    allUserPlants.removeObservers(getViewLifecycleOwner());
                    break;
                case LOADING:
                    break;
            }
        });

    }

    @NonNull
    @Override
    public String toString() {
        return "Destek Oluştur";
    }

    static class SpinnerContainer{
        private final String text;
        private final Long id;

        public SpinnerContainer(String text, Long id) {
            this.text = text;
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public Long getId() {
            return id;
        }

        @NonNull
        @Override
        public String toString() {
            return text;
        }
    }
}
