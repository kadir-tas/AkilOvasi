package com.company.akilovasi.di.modules.blog;

import com.company.akilovasi.data.local.dao.BlogDao;
import com.company.akilovasi.data.remote.api.BlogService;
import com.company.akilovasi.data.remote.repositories.BlogRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.BlogRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BlogModule {
    @Provides
    @Singleton
    BlogRepository provideBlogRepository(BlogService blogService , BlogDao blogDao){
        return new BlogRepositoryImpl(blogService,blogDao);
    }
}
