package com.company.akilovasi.ui.main.fragments.support.mysupports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;

import com.company.akilovasi.R;
import com.company.akilovasi.databinding.FragmentMySupportsBinding;
import com.company.akilovasi.ui.BaseFragment;

public class MySupportsFragment extends BaseFragment<MySupportsFragmentViewModel, FragmentMySupportsBinding> {

    public static MySupportsFragment newInstance(){
        return new MySupportsFragment();
    }

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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public String toString() {
        return "Desteklerim";
    }
}
