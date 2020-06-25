package com.company.akilovasi.ui.main.fragments.support;

import androidx.databinding.DataBindingComponent;

import com.company.akilovasi.R;
import com.company.akilovasi.databinding.FragmentSupportBinding;
import com.company.akilovasi.ui.BaseFragment;

public class SupportFragment extends BaseFragment<SupportFragmentViewModel , FragmentSupportBinding> {
    public static final String TAG = "SupportFragment";

    public static SupportFragment newInstance(){
        return new SupportFragment();
    }

    @Override
    public Class<SupportFragmentViewModel> getViewModel() {
        return SupportFragmentViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_support;
    }

    @Override
    public DataBindingComponent getBindingComponent() {
        return null;
    }
}
