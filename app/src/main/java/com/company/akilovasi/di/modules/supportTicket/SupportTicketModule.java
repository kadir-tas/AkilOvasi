package com.company.akilovasi.di.modules.supportTicket;

import com.company.akilovasi.data.local.dao.SupportTicketDao;
import com.company.akilovasi.data.local.entities.SupportTicket;
import com.company.akilovasi.data.remote.repositories.SupportTicketRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.SupportTicketRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class SupportTicketModule {
    @Provides
    @Singleton
    SupportTicketRepository provideSupportTicketRepository(Retrofit retrofit, SupportTicketDao supportTicketDao){
        return new SupportTicketRepositoryImpl(retrofit, supportTicketDao);
    }
}
