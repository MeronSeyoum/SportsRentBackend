package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SportRentalInventorySystem.BackEnd.model.Reservation;
import com.SportRentalInventorySystem.BackEnd.model.ReservedItem;
import com.SportRentalInventorySystem.BackEnd.model.User;

public interface ReservedItemRepository extends JpaRepository<Reservation, Long> {

    void save(ReservedItem itemReserve);

  
}
