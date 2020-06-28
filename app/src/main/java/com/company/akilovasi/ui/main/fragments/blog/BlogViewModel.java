package com.company.akilovasi.ui.main.fragments.blog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Blog;
import com.company.akilovasi.data.local.entities.BlogPreview;
import com.company.akilovasi.data.remote.repositories.BlogRepository;

import java.util.List;

import javax.inject.Inject;

public class BlogViewModel extends ViewModel {
    private final BlogRepository blogRepository;

    @Inject
    public BlogViewModel(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public LiveData<Resource<List<BlogPreview>>> getBlogPreviewPaged(int pageId){
        return blogRepository.getBlogPreviewPaged(pageId);
    }

    public LiveData<Resource<Blog>> getBlog(Long blogId){
        return blogRepository.getBlog(blogId);
    }
}
