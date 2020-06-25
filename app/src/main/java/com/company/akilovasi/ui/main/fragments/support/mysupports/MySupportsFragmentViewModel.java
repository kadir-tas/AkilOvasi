package com.company.akilovasi.ui.main.fragments.support.mysupports;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.local.entities.SupportTicket;
import com.company.akilovasi.data.remote.repositories.SupportTicketRepository;

import java.util.List;

import javax.inject.Inject;

public class MySupportsFragmentViewModel extends ViewModel {

    private SupportTicketRepository supportTicketRepository;

    @Inject
    public MySupportsFragmentViewModel(SupportTicketRepository supportTicketRepository) {
        this.supportTicketRepository = supportTicketRepository;
    }

    public LiveData<List<SupportTicket>> getAllSupportTickets(){
        return supportTicketRepository.getSupportTickets();
    }
}
