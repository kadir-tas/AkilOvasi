package com.company.akilovasi.ui.main.fragments.blog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.BlogPreview;
import com.company.akilovasi.databinding.FragmentBlogBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.main.fragments.blog.adapter.BlogRecyclerViewAdapter;
import com.company.akilovasi.ui.main.fragments.blog.callbacks.OnBlogClicked;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class BlogFragment extends BaseFragment<BlogViewModel, FragmentBlogBinding> implements OnBlogClicked {
    public static final String TAG = "BlogFragment";

    public static BlogFragment newInstance(){
        return new BlogFragment();
    }

    @Inject
    Picasso picasso;
    private int currentPage;
    private boolean finalPage;
    private BlogRecyclerViewAdapter blogRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView blogRecyclerView;

    @Override
    public Class<BlogViewModel> getViewModel() {
        return BlogViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_blog;
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
        iniRecyclerView();
        requestNextPage();
        return dataBinding.getRoot();
    }

    private void iniRecyclerView(){
        currentPage = 0;
        blogRecyclerView = dataBinding.recyclerView;
        linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dataBinding.getLoading() || finalPage)
                    return;
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    requestNextPage();
                }
            }
        };
        blogRecyclerView.setLayoutManager(linearLayoutManager);
        blogRecyclerView.addOnScrollListener(mScrollListener);
        blogRecyclerViewAdapter = new BlogRecyclerViewAdapter(picasso, this);
        blogRecyclerView.setAdapter(blogRecyclerViewAdapter);
    }

    private void requestNextPage() {
        dataBinding.setLoading(true);
        //Log.d(TAG, "requestNextPage: Begin " + currentPage);
        LiveData<Resource<List<BlogPreview>>> liveData = viewModel.getBlogPreviewPaged(currentPage);

        liveData.observe(getViewLifecycleOwner(), listResource -> {
            //Log.d(TAG, "requestNextPage: Callback called for " + currentPage);
            switch (listResource.status){
                case SUCCESS:
                    liveData.removeObservers(getViewLifecycleOwner());
                    if(listResource.data != null){
                        if(listResource.data.size() == 0){
                            finalPage = true;
                        }else{
                            blogRecyclerViewAdapter.addData(listResource.data);
                        }
                    }
                    currentPage += 1;
                    dataBinding.setLoading(false);
                    break;
                case ERROR:
                    liveData.removeObservers(getViewLifecycleOwner());
                    //Probably final page
                    finalPage = true;
                    break;
                case LOADING:
                    dataBinding.setLoading(true);
                    break;
            }
        });
    }

    @Override
    public void onClick(BlogPreview blogPreview) {
        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(BlogContentFragment.TAG);
        if (f == null) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, BlogContentFragment.newInstance(blogPreview.getBlogId()), BlogContentFragment.TAG)
                    .addToBackStack("main")
                    .commit();
        }
    }
}
