package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Blog;
import com.company.akilovasi.data.local.entities.BlogPreview;

import java.util.List;

public interface BlogRepository {
    LiveData<Resource<List<BlogPreview>>> getBlogPreviewPaged(int pageId);
    LiveData<Resource<Blog>> getBlog(Long blogId);
}
