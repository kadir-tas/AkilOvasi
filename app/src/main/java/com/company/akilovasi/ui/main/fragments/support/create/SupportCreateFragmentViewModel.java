package com.company.akilovasi.ui.main.fragments.support.create;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.SupportTicket;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.repositories.PlantRepository;
import com.company.akilovasi.data.remote.repositories.SupportTicketRepository;
import com.company.akilovasi.di.SecretPrefs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SupportCreateFragmentViewModel extends ViewModel {

    private PlantRepository plantRepository;

    private SupportTicketRepository supportTicketRepository;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    public SupportCreateFragmentViewModel( PlantRepository plantRepository,  SupportTicketRepository supportTicketRepository ) {
        this.plantRepository = plantRepository;
        this.supportTicketRepository = supportTicketRepository;
    }

    public LiveData<Resource<List<Plant>>> getAllUserPlants(){
        return plantRepository.getUserAllPlants( secretPreferences.getLong(ApiConstants.USER_ID, -1) );
    }

    //TODO: THIS IS TEMPORARY DO NOT USE
    public void temp_saveTickets(SupportTicket ticket){
        ticket.setUserId(secretPreferences.getLong(ApiConstants.USER_ID, -1));
        final ArrayList<SupportTicket> supportTickets = new ArrayList<>();
        supportTickets.add(ticket);
        supportTicketRepository.temp_saveTickets(supportTickets);
    }
}
