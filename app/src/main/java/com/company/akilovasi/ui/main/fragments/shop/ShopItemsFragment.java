package com.company.akilovasi.ui.main.fragments.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.databinding.FragmentShopItemsBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.main.fragments.shop.adapter.ShopItemsAdapter;
import com.company.akilovasi.ui.main.fragments.shop.callback.OnShopItemClicked;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class ShopItemsFragment extends BaseFragment<ShopViewModel , FragmentShopItemsBinding> implements OnShopItemClicked {
    public static final String TAG = "ShopItemsFragment";
    private final String category;
    private final String subCategory;

    public static ShopItemsFragment newInstance(String category , String subCategory){
        return new ShopItemsFragment(category, subCategory);
    }

    @Inject
    Picasso picasso;

    RecyclerView recyclerView;
    ShopItemsAdapter shopItemsAdapter;

    public ShopItemsFragment(String category, String subCategory){
        this.category = category;
        this.subCategory = subCategory;
    }

    @Override
    public Class<ShopViewModel> getViewModel() {
        return ShopViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_shop_items;
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
        recyclerView = dataBinding.recyclerView;
        initRecyclerView();
        return dataBinding.getRoot();
    }

    private void initRecyclerView() {
        dataBinding.setLoading(true);
        dataBinding.setSubCategoryName( subCategory );
        final LiveData<Resource<List<ShopItem>>> itemsLiveData = viewModel.loadPlantByCategoryAndSubCategories(category, subCategory);
        itemsLiveData.observe(getViewLifecycleOwner() , listResource -> {
            switch (listResource.status){
                case SUCCESS:
                    final List<ShopItem> items = listResource.data;
                    if(items != null && items.size() != 0){
                        shopItemsAdapter = new ShopItemsAdapter(picasso, this);
                        recyclerView.setAdapter(shopItemsAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                        shopItemsAdapter.setData(items);
                    }
                    itemsLiveData.removeObservers(getViewLifecycleOwner());
                    dataBinding.setLoading(false);
                    break;
                case ERROR:
                    itemsLiveData.removeObservers(getViewLifecycleOwner());
                    Toast.makeText(getContext(), R.string.common_err, Toast.LENGTH_SHORT).show();
                    dataBinding.setLoading(false);
                    break;
                case LOADING:
                    dataBinding.setLoading(true);
                    break;
            }
        });

    }

    @Override
    public void onClick(ShopItem shopItem) {
        Log.d(TAG, "onClick: Clicked on " + shopItem.getLink());
    }
}
