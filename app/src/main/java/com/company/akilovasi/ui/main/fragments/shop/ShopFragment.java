package com.company.akilovasi.ui.main.fragments.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.databinding.FragmentShopBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.common.FragmentPagerAdapter;
import com.company.akilovasi.ui.main.fragments.shop.adapter.ShopCategoryAdapter;
import com.company.akilovasi.ui.main.fragments.shop.callback.OnSpawnShopItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ShopFragment extends BaseFragment<ShopViewModel , FragmentShopBinding> implements OnSpawnShopItems {

    public static final String TAG = "ShopFragment";
    public static ShopFragment newInstance(){
        return new ShopFragment();
    }

    private FragmentPagerAdapter fragmentPagerAdapter;

    @Inject
    Picasso picasso;

    @Override
    public Class<ShopViewModel> getViewModel() {
        return ShopViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_shop;
    }

    @Override
    public DataBindingComponent getBindingComponent() {
        return new BindingComponent(picasso);
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
        fragmentPagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager());
        return dataBinding.getRoot();
    }

    private void initObservers() {
        dataBinding.setLoading(true);
        final LiveData<Resource<List<ShopItem>>> allItems = viewModel.getAllItems();
        allItems.observe(getViewLifecycleOwner() , listResource -> {
            switch (listResource.status){
                case SUCCESS:
                    final List<ShopItem> data = listResource.data;
                    if(data != null && data.size() != 0){
                        initViewPager();
                    }
                    else{
                        Toast.makeText(getContext(), R.string.common_err, Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                    allItems.removeObservers(getViewLifecycleOwner());
                    dataBinding.setLoading(false);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), R.string.common_err, Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                    allItems.removeObservers(getViewLifecycleOwner());
                    dataBinding.setLoading(false);
                    break;
                case LOADING:
                    dataBinding.setLoading(true);
                    break;
            }
        });
    }

    private void initViewPager() {
        final LiveData<List<String>> listOfCategory = viewModel.getListOfCategory();
        listOfCategory.observe(getViewLifecycleOwner() , strings -> {
            final ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
            for (String category : strings) {
                fragmentArrayList.add(ShopCategoryFragment.newInstance(category, this));
            }
            fragmentPagerAdapter.setPlantCategoryFragments(fragmentArrayList);
            dataBinding.setPagerAdapter(fragmentPagerAdapter);
            listOfCategory.removeObservers(getViewLifecycleOwner());
        });
    }

    @Override
    public void onSpawnShopItems(String category, String subCategory) {
        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(ShopItemsFragment.TAG);
        if (f == null) {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.shop_fragment_container, ShopItemsFragment.newInstance(category, subCategory), ShopItemsFragment.TAG).addToBackStack("main").commit();
        } else {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SupportFragment.newInstance(), SupportFragment.TAG).commit();
        }
    }
}
