package com.SportRentalInventorySystem.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SportRentalInventorySystem.BackEnd.model.Reservation;

public interface ReservedItemRepository extends JpaRepository<Reservation, Long> {

}
