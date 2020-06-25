package com.company.akilovasi.ui.main.fragments.support.mysupports;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.SupportTicket;
import com.company.akilovasi.databinding.FragmentMySupportsBinding;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.main.fragments.support.create.adapter.SupportTicketRvAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class MySupportsFragment extends BaseFragment<MySupportsFragmentViewModel, FragmentMySupportsBinding> {
    private static final String TAG = "MySupportsFragment";
    public static MySupportsFragment newInstance(){
        return new MySupportsFragment();
    }

    @Inject
    public Picasso picasso;

    private SupportTicketRvAdapter supportTicketRvAdapter;


    @Override
    public Class<MySupportsFragmentViewModel> getViewModel() {
        return MySupportsFragmentViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my_supports;
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
        supportTicketRvAdapter = new SupportTicketRvAdapter(picasso);
        dataBinding.supportTicketRv.setAdapter(supportTicketRvAdapter);
        dataBinding.supportTicketRv.setLayoutManager(new LinearLayoutManager(getContext()));
        initObservers();
        return dataBinding.getRoot();
    }

    private void initObservers(){
        final LiveData<List<SupportTicket>> allSupportTickets = viewModel.getAllSupportTickets();
        allSupportTickets.observe(getViewLifecycleOwner() , supportTickets -> {
            if(supportTickets != null){
                Log.d(TAG, "initObservers: tickets are not null size is " + supportTickets.size());
                supportTicketRvAdapter.setData( supportTickets );
            }
        });


    }

    @NonNull
    @Override
    public String toString() {
        return "Desteklerim";
    }
}
