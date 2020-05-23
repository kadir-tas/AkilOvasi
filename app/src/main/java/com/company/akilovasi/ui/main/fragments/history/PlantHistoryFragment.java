package com.company.akilovasi.ui.main.fragments.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.Status;
import com.company.akilovasi.data.local.entities.AnalysisResult;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.databinding.FragmentPlantHistoryBinding;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.analysisresult.AnalysisResultActivity;
import com.company.akilovasi.ui.common.fullscreen.PlantFullImageFragment;
import com.company.akilovasi.ui.main.MainActivity;
import com.company.akilovasi.ui.main.adapters.PlantHistoryAdapter;
import com.company.akilovasi.ui.main.adapters.PlantProblemAdapter;
import com.company.akilovasi.ui.main.callbacks.AnalysisCallback;
import com.company.akilovasi.ui.main.callbacks.PlantHistoryClick;
import com.company.akilovasi.ui.main.callbacks.PlantProblemClick;
import com.company.akilovasi.ui.plantanalysis.PlantAnalysisActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class PlantHistoryFragment extends BaseFragment<PlantHistoryFragmentViewModel, FragmentPlantHistoryBinding> implements AnalysisCallback , PlantHistoryClick, PlantProblemClick {

    public static final String TAG = "PlantHistoryFragment";

    private PlantHistoryAdapter mPlantHistoryAdapter;
    private PlantProblemAdapter mPlantProblemAdapter;
    private RecyclerView mHistoryRecyclerView;
    private FragmentActivity mActivity;
    private Plant plant;
    private LinearLayoutManager linearLayoutManager;
    private int currentPage = 0;
    private boolean finalPage = false;

    @Inject
    Picasso picasso;

    public static PlantHistoryFragment newInstance(Plant plant) {
        Bundle args = new Bundle();
        args.putSerializable("plant", plant);
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
    public DataBindingComponent getBindingComponent() {
        return null;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() != null) {
            plant = (Plant) getArguments().getSerializable("plant");
        }
        mActivity = getActivity();
        Log.d(TAG, "onCreate: Created");
        return dataBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBinding.setAnalysisCallback(this);
        dataBinding.setUserPlantId(plant.getUserPlantId());
        dataBinding.plantProblemsButton.setEnabled(false);
        dataBinding.historyButton.setEnabled(true);
        setFragmentHeader();

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mHistoryRecyclerView = dataBinding.plantDetailRcView;
        mHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        mHistoryRecyclerView.setHasFixedSize(true);

        initProblemsRecyclerView();
        dataBinding.historyButton.setOnClickListener(v -> {
            dataBinding.historyButton.setEnabled(false);
            dataBinding.plantProblemsButton.setEnabled(true);
            initHistoryRecyclerView();
            Log.d(TAG, "onViewCreated: initHistoryRecyclerView done");
            requestNextPage();
        });
        dataBinding.plantProblemsButton.setOnClickListener(v -> {
            dataBinding.historyButton.setEnabled(true);
            dataBinding.plantProblemsButton.setEnabled(false);
            initProblemsRecyclerView();
        });
        Log.d(TAG, "onViewCreated: initObservers done");
    }

    private void setFragmentHeader() {
        dataBinding.setPlant(plant);
    }

    private void requestNextPage() {
        dataBinding.setLoading(true);
        //Log.d(TAG, "requestNextPage: Begin " + currentPage);
        LiveData<Resource<List<PlantHistory>>> liveData = viewModel.getPlantHistoryPaged(plant.getUserPlantId(),currentPage);

        liveData.observe(getViewLifecycleOwner(), listResource -> {
            //Log.d(TAG, "requestNextPage: Callback called for " + currentPage);
            switch (listResource.status){
                case SUCCESS:
                    liveData.removeObservers(getViewLifecycleOwner());
                    Log.d(TAG, "requestNextPage: Succsess");
                    if(listResource.data != null){
                        if(listResource.data.size() == 0){
                            //Log.d(TAG, "requestNextPage: Final page " + currentPage);
                            finalPage = true;
                        }else{
                            //for(PlantHistory p : listResource.data)
                                //Log.d(TAG, "requestNextPage: " + p.getId() + " " + p.getPageId());
                            mPlantHistoryAdapter.addData(listResource.data);
                        }
                    }
                    currentPage += 1;
                    dataBinding.setLoading(false);
                    break;
                case ERROR:
                    liveData.removeObservers(getViewLifecycleOwner());
                    //Toast.makeText(mActivity, R.string.common_err, Toast.LENGTH_SHORT).show();
                    //Probably final page
                    Log.d(TAG, "requestNextPage: Error");
                    finalPage = true;
                    break;
                case LOADING:
                    Log.d(TAG, "requestNextPage: Loading");
                    dataBinding.setLoading(true);
                    break;
            }
        });
    }

    // This method works only when the plant "history" button is clicked
    private void initHistoryRecyclerView(){
        Log.d(TAG, "initHistoryRecyclerView: ");

        RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dataBinding.getLoading() || finalPage)
                    return;
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    Log.d(TAG, "onScrolled: This called");
                    requestNextPage();
                }
            }
        };
        mHistoryRecyclerView.addOnScrollListener(mScrollListener);
        mPlantHistoryAdapter = new PlantHistoryAdapter(picasso, this);
        mHistoryRecyclerView.setAdapter(mPlantHistoryAdapter);
    }

    // This method works once when the fragment is first opened. After that, it only works when the "plant problem" button is clicked.
    private void initProblemsRecyclerView() {
        viewModel.getAnalysisResults(plant.getUserPlantId()).observe(getViewLifecycleOwner(), notifications -> {
            mPlantProblemAdapter.setData(notifications);
        });

        mPlantProblemAdapter = new PlantProblemAdapter();
        mHistoryRecyclerView.setAdapter(mPlantProblemAdapter);
    }

    @Override
    public void onAnalysisClicked(Long userPlantId) {
        Log.d(TAG, "AnalysisClicked");
        Intent intent = new Intent(mActivity, PlantAnalysisActivity.class);
        intent.putExtra(PlantAnalysisActivity.PARAM_USER_PLANT, userPlantId);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    @Override
    public void onPlantHistoryImageClick(Long plantHistoryId) {
        Log.d(TAG, "onPlantHistoryImageClick: " + plantHistoryId);
        PlantFullImageFragment fragment = new PlantFullImageFragment(PlantFullImageFragment.USER_PLANT_HISTORY,plantHistoryId);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, PlantFullImageFragment.TAG).commit();
    }

    @Override
    public void onPlantHistoryClick(PlantHistory plantHistory) {
        Intent intent = new Intent(getContext(), AnalysisResultActivity.class);
        intent.putExtra("plantHistory", plantHistory);
        getContext().startActivity(intent);
    }

    @Override
    public void onCancelClicked(AnalysisResult analysisResult) {
        viewModel.deleteProblem(analysisResult);
    }

    @Override
    public void onInterfereClicked(AnalysisResult analysisResult) {
        viewModel.deleteProblem(analysisResult);
    }
}
