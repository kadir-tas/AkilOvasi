package com.company.akilovasi.ui.main.fragments.blog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Blog;
import com.company.akilovasi.databinding.FragmentBlogContentBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class BlogContentFragment extends BaseFragment<BlogViewModel , FragmentBlogContentBinding> {
    public static final String TAG = "BlogContentFragment";
    private final Long blogId;

    public static BlogContentFragment newInstance(Long blogId){
        return new BlogContentFragment(blogId);
    }

    @Inject
    Picasso picasso;

    public BlogContentFragment(Long blogId){
        this.blogId = blogId;
    }

    @Override
    public Class<BlogViewModel> getViewModel() {
        return BlogViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_blog_content;
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
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
    }

    public void initObservers(){
        dataBinding.setLoading(true);
        final LiveData<Resource<Blog>> blog = viewModel.getBlog(blogId);
        blog.observe(getViewLifecycleOwner() , blogResource -> {
            switch (blogResource.status){
                case SUCCESS:
                    final Blog data = blogResource.data;
                    if(data != null){
                        dataBinding.setBlog(data);
                    }else {
                        Toast.makeText(getContext(), R.string.common_err, Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                    dataBinding.setLoading(false);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), R.string.common_err, Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                    dataBinding.setLoading(false);
                    break;
                case LOADING:
                    dataBinding.setLoading(true);
                    break;
            }
        });
    }
}
