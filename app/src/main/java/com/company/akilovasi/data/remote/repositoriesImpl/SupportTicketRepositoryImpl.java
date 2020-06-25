package com.company.akilovasi.data.remote.repositoriesImpl;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.local.dao.SupportTicketDao;
import com.company.akilovasi.data.local.entities.SupportTicket;
import com.company.akilovasi.data.remote.repositories.SupportTicketRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SupportTicketRepositoryImpl implements SupportTicketRepository {

    //TODO: SupportTicketService Needed this is just offline repo for testing purposes

    private Retrofit retrofit;
    private SupportTicketDao supportTicketDao;

    @Inject
    public SupportTicketRepositoryImpl(Retrofit retrofit, SupportTicketDao supportTicketDao) {
        this.supportTicketDao = supportTicketDao;
        this.retrofit = retrofit;
    }

    @Override
    public LiveData<List<SupportTicket>> getSupportTickets() {
        return supportTicketDao.loadSupportTickets();
    }

    @Override
    public void temp_saveTickets(List<SupportTicket> supportTickets) {
        supportTicketDao.saveSupportTickets(supportTickets);
    }
}
