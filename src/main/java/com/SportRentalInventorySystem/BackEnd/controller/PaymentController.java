package com.SportRentalInventorySystem.BackEnd.controller;


import com.SportRentalInventorySystem.BackEnd.Security.services.StripeClient;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*" ) 
@RequestMapping("/api/payment")
public class PaymentController {
   
    @Autowired
    private StripeClient stripeClient;
    
    @Autowired
    PaymentController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }
    
    
    @PostMapping("/charge")
    public Charge chargeCard(@RequestHeader(value="token") String token, @RequestHeader(value="amount") Double amount) throws Exception {
        return this.stripeClient.chargeNewCard(token, amount);
    }


}
