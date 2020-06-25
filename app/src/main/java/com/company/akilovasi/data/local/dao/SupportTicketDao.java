package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.SupportTicket;

import java.util.List;

@Dao
public interface SupportTicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveSupportTickets(List<SupportTicket> supportTickets);

    @Query("SELECT * FROM supportTicket ORDER BY status ASC")
    LiveData<List<SupportTicket>> loadSupportTickets();
}
