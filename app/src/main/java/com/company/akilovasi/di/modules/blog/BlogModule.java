package com.company.akilovasi.di.modules.blog;

import com.company.akilovasi.data.local.dao.BlogDao;
import com.company.akilovasi.data.remote.api.BlogService;
import com.company.akilovasi.data.remote.repositories.BlogRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.BlogRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class BlogModule {
    @Provides
    @Singleton
    BlogRepository provideBlogRepository(Retrofit retrofit, BlogDao blogDao){
        return new BlogRepositoryImpl(retrofit,blogDao);
    }
}
