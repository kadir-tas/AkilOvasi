package com.company.akilovasi.ui.main.fragments.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.company.akilovasi.R;
import com.company.akilovasi.databinding.FragmentSupportBinding;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.common.FragmentPagerAdapter;
import com.company.akilovasi.ui.main.fragments.support.create.SupportCreateFragment;
import com.company.akilovasi.ui.main.fragments.support.mysupports.MySupportsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(SupportCreateFragment.newInstance());
        fragments.add(MySupportsFragment.newInstance());
        final FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getParentFragmentManager());
        fragmentPagerAdapter.setPlantCategoryFragments( fragments );
        dataBinding.setPagerAdapter(fragmentPagerAdapter);
        return dataBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
