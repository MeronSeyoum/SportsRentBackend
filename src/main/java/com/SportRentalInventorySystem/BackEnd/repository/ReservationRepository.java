package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SportRentalInventorySystem.BackEnd.model.ReservationProjection;
import com.SportRentalInventorySystem.BackEnd.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

//  + "r.address, r.city, country, r.province,r.zip, u.first_name, u.last_name "
 
    @Query(nativeQuery = true, value = "SELECT DISTINCT r.* FROM reserved r inner join users u on r.user = u.id where r.user =:user Group by r.user")
    public List<ReservationProjection> pickupInfo(long user);
    
    @Query(nativeQuery = true, value = "SELECT * FROM reserved r order by date_Stamp_Date desc limit 1 ")
    public Reservation findLastRecord();
}
