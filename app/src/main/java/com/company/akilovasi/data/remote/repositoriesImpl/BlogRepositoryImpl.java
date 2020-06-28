package com.company.akilovasi.data.remote.repositoriesImpl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.BlogDao;
import com.company.akilovasi.data.local.entities.Blog;
import com.company.akilovasi.data.local.entities.BlogPreview;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.BlogService;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.BlogRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class BlogRepositoryImpl implements BlogRepository {
    private static final String TAG = "BlogRepositoryImpl";

    private final BlogService blogService;
    private final BlogDao blogDao;
    private final Retrofit retrofit;

    @Inject
    public BlogRepositoryImpl(Retrofit retrofit, BlogDao blogDao) {
        this.retrofit = retrofit;
        this.blogDao = blogDao;
        blogService = this.retrofit.create(BlogService.class);
    }

    @Override
    public LiveData<Resource<List<BlogPreview>>> getBlogPreviewPaged(int pageId) {
        return new NetworkBoundResource<List<BlogPreview>, Response<List<BlogPreview>>>() {

            @Override
            protected void saveCallResult(@NonNull Response<List<BlogPreview>> item) {
                if(item.getSuccess()){
                    blogDao.saveBlogPreviews(item.getResults());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<BlogPreview> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData< List<BlogPreview>>  loadFromDb() {
                return blogDao.getBlogPreviewPaged(pageId);
            }

            @NonNull
            @Override
            protected Call< Response<List<BlogPreview>> > createCall() {
                return blogService.getBlogPreviewPaged(pageId);
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<Blog>> getBlog(Long blogId) {
        return new NetworkBoundResource<Blog, Response<Blog>>() {

            @Override
            protected void saveCallResult(@NonNull Response<Blog> item) {
                if(item.getSuccess()){
                    final ArrayList<Blog> blogArrayList = new ArrayList<Blog>();
                    blogArrayList.add(item.getResults());
                    blogDao.saveBlog(blogArrayList);
                }
            }
            @Override
            protected boolean shouldFetch(@Nullable Blog data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Blog>  loadFromDb() {
                return blogDao.getBlog(blogId);
            }

            @NonNull
            @Override
            protected Call< Response<Blog>> createCall() {
                return blogService.getBlog(blogId);
            }
        }.getAsLiveData();
    }
}
