package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.Blog;
import com.company.akilovasi.data.local.entities.BlogPreview;

import java.util.List;

@Dao
public interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveBlog(List<Blog> plants);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveBlogPreviews(List<BlogPreview> plants);

    @Query("SELECT * FROM blog WHERE blogId=:blogId")
    LiveData<Blog> getBlog(Long blogId);

    @Query("SELECT * FROM blogPreview WHERE pageId=:pageId ORDER BY date DESC")
    LiveData<List<BlogPreview>> getBlogPreviewPaged(int pageId);

    @Query("DELETE FROM blog")
    void clearBlog();

    @Query("DELETE FROM blogPreview")
    void clearBlogPreview();
}
