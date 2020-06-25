package com.company.akilovasi.data.remote.repositories;

import android.app.admin.SystemUpdatePolicy;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.local.entities.SupportTicket;

import java.util.List;

public interface SupportTicketRepository {

    LiveData<List<SupportTicket>> getSupportTickets();

    /**
     * Temporary until server side completed
     * @param supportTickets
     */
    void temp_saveTickets(List<SupportTicket> supportTickets);

}
