package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SportRentalInventorySystem.BackEnd.model.ProductProjection;
import com.SportRentalInventorySystem.BackEnd.model.Reservation;
import com.SportRentalInventorySystem.BackEnd.model.ReservedItem;
import com.SportRentalInventorySystem.BackEnd.model.ReservedItemProjection;
import com.SportRentalInventorySystem.BackEnd.model.User;

public interface ReservedItemRepository extends JpaRepository<Reservation, Long> {

    void save(ReservedItem itemReserve);

  
//fetch reserved item by reservation id
            @Query(nativeQuery = true, value = "SELECT i.id, p.product_Name, i.amount ,i.quantity FROM reserved_item i inner join reserved r ON  i.reservation_id = r.id "
                    + "inner join product p ON i.product_id = p.id where r.id=:r_id")
    public List<ReservedItemProjection> findByReservationId(long r_id);
            


}
