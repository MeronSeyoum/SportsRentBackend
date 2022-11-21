/**
* *
*
* @author  Meron seyoum
* @version 1.0
* @since   2022-09-24
*/
package com.SportRentalInventorySystem.BackEnd.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SportRentalInventorySystem.BackEnd.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email); 
     
    public User findByResetPasswordToken(String token);
    
    
    
////  Search category by keyword
//  @Query("select c from User c where c.LastName LIKE %?1%")
//  public List<User> searchByUserName(String keyWords);
}
