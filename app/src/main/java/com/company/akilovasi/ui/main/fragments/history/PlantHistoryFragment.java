package com.company.akilovasi.ui.main.fragments.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.databinding.FragmentPlantHistoryBinding;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.main.adapters.PlantHistoryAdapter;
import com.company.akilovasi.ui.main.callbacks.AnalysisCallback;
import com.company.akilovasi.ui.plantanalysis.PlantAnalysis;

import java.util.List;

public class PlantHistoryFragment extends BaseFragment<PlantHistoryFragmentViewModel, FragmentPlantHistoryBinding> implements AnalysisCallback {

    public static final String TAG = "PlantHistoryFragment";

    private PlantHistoryAdapter mPlantHistoryAdapter;
    private RecyclerView mHistoryRecyclerView;
    private FragmentActivity mActivity;
    private long userPlantId;


    public static PlantHistoryFragment newInstance(Long userPlantId) {
        Bundle args = new Bundle();
        args.putLong("userPlantId", userPlantId);
        PlantHistoryFragment fragment = new PlantHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Class<PlantHistoryFragmentViewModel> getViewModel() {
        return PlantHistoryFragmentViewModel.class;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_plant_history;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "FRAGMENT CREATE");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        userPlantId = getArguments().getLong("userPlantId");
        mActivity = getActivity();
        Log.d(TAG, "onCreate: Created");
        return dataBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBinding.setAnalysisCallback(this);
        dataBinding.setUserPlantId(userPlantId);
        initHistoryRecyclerView();
        Log.d(TAG, "onViewCreated: initHistoryRecyclerView done");
        initObservers();
        Log.d(TAG, "onViewCreated: initObservers done");
    }


    private void initObservers() {
        viewModel.getPlantHistory(userPlantId).observe(getViewLifecycleOwner(), listResource -> {
            Log.d(TAG, "" + listResource.message);
            Log.d(TAG, "" + listResource.data);
            Log.d(TAG, "" + listResource.status);
            Log.d(TAG, "ID" + userPlantId);
            Log.d(TAG, "DATA NULLL");

            mPlantHistoryAdapter.setData(listResource.data);
        });
        Log.d(TAG, "FINISH OBSERVE");
    }


    private void initHistoryRecyclerView(){
        Log.d(TAG, "İnitRec");

        mHistoryRecyclerView = dataBinding.plantHistory;
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mHistoryRecyclerView.setHasFixedSize(true);

        mPlantHistoryAdapter = new PlantHistoryAdapter();
        mHistoryRecyclerView.setAdapter(mPlantHistoryAdapter);
    }


    @Override
    public void onAnalysisClicked(Long userPlantId) {

        Log.d(TAG, "AnalysisClicked");

        Intent intent = new Intent(mActivity, PlantAnalysis.class);
        intent.putExtra("userPlantId", userPlantId);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}