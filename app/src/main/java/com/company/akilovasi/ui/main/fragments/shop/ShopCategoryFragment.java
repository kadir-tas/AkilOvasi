package com.company.akilovasi.ui.main.fragments.shop;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.databinding.FragmentShopCategoriesBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.main.fragments.shop.adapter.ShopCategoryAdapter;
import com.company.akilovasi.ui.main.fragments.shop.callback.OnShopItemSubCategoryClicked;
import com.company.akilovasi.ui.main.fragments.shop.callback.OnSpawnShopItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ShopCategoryFragment extends BaseFragment<ShopViewModel , FragmentShopCategoriesBinding> implements OnShopItemSubCategoryClicked {

    private final String category;
    private RecyclerView recyclerView;
    private ShopCategoryAdapter shopCategoryAdapter;
    private OnSpawnShopItems spawnShopItems;
    public static ShopCategoryFragment newInstance(String category,OnSpawnShopItems spawnShopItems){
        return new ShopCategoryFragment(category ,spawnShopItems);
    }

    @Inject
    Picasso picasso;

    public ShopCategoryFragment(String category, OnSpawnShopItems spawnShopItems){
        this.category = category;
        this.spawnShopItems = spawnShopItems;
    }

    @Override
    public Class<ShopViewModel> getViewModel() {
        return ShopViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_shop_categories;
    }

    @Override
    public DataBindingComponent getBindingComponent() {
        return new BindingComponent(picasso);
    }

    @NonNull
    @Override
    public String toString() {
        return category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView();
        return dataBinding.getRoot();
    }

    private void initRecyclerView() {
        recyclerView = dataBinding.recyclerView;
        final LiveData<Resource<List<ShopItem>>> shopItems = viewModel.loadByCategories(category);
        shopItems.observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status){
                case SUCCESS:
                    final List<ShopItem> data = listResource.data;
                    if(data != null && data.size() != 0){
                        shopCategoryAdapter = new ShopCategoryAdapter(picasso , this);
                        final ArrayList<String> createdSubCategories = new ArrayList<String>();
                        ArrayList<Pair<String, Long>> categoryImagePairs = new ArrayList<>();
                        for (ShopItem item : data) {
                            if(createdSubCategories.contains( item.getSubCategory() )) continue;
                            createdSubCategories.add(item.getSubCategory());
                            categoryImagePairs.add(new Pair<>( item.getSubCategory() , item.getId() ));
                        }
                        shopCategoryAdapter.setData(categoryImagePairs);
                        recyclerView.setAdapter(shopCategoryAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 3));
                    }
                    dataBinding.setLoading(false);
                    break;
                case ERROR:
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
    public void onClick(String subCategory) {
        spawnShopItems.onSpawnShopItems(category, subCategory);
    }
}
