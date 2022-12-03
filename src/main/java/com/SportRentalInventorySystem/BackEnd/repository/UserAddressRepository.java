package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SportRentalInventorySystem.BackEnd.model.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long>  {

          //get UserAddress data using ForeignId
            @Query("select a from UserAddress a where a.user=:user_id")
           public List<UserAddress> findByForeignId(Long user_id);
}
