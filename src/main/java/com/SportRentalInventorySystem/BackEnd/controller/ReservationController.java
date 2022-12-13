package com.SportRentalInventorySystem.BackEnd.controller;

/**
*
* @author  Meron seyoum
* @version 1.0
 * 
 * This code is a REST API that allows users to make reservations for products and add products to a shopping cart. 
 * It also allows for the retrieval of summer and winter categories, top-selling products, and products by category and season. 
 * It also has functionality for sending emails and searching for products. The code uses the Spring framework and uses 
 * the @RestController and @RequestMapping annotations to map incoming HTTP requests to specific handler methods. 
 * It also uses the @Autowired annotation to inject dependencies and the @CrossOrigin annotation to enable cross-origin resource sharing (CORS). 
 * The code also uses the @PreAuthorize annotation to enable access control.
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.model.Product;
import com.SportRentalInventorySystem.BackEnd.model.ProductList;
import com.SportRentalInventorySystem.BackEnd.model.Reservation;
import com.SportRentalInventorySystem.BackEnd.model.ReservedItem;
import com.SportRentalInventorySystem.BackEnd.model.User;
import com.SportRentalInventorySystem.BackEnd.repository.ProductRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ReservationRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ReservedItemRepository;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/Reservation")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservedItemRepository reserveItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/makeReservation/{id}")
    public ResponseEntity<Reservation> makeReservation(@PathVariable long id,
            @RequestBody Reservation reservationDetails) {
        String Reservation_Code = RandomString.make(6);
        Reservation reserve = userRepository.findById(id).map(user -> {
            reservationDetails.setUser(user);

            reservationDetails.setReservation_Code(Reservation_Code);

            return reservationRepository.save(reservationDetails);
        }).orElseThrow(() -> new RuntimeException("create new Reservation fail "));

        return new ResponseEntity<>(reserve, HttpStatus.CREATED);
    }

    @PostMapping("/addCart/{id}/{email}")
    public ResponseEntity<?> addCart(@PathVariable long id, @PathVariable String email,
            @RequestBody List<ReservedItem> reserveItem) throws IOException, MessagingException {

//        get last entered reservation id
        Product lastInsertedProduct = null;

        Reservation reserve = reservationRepository.findLastRecord();

        for (int i = 0; i < reserveItem.size(); i++) {

            lastInsertedProduct = getProductId(reserveItem.get(i).getProduct().getId());

            ReservedItem itemReserve = new ReservedItem(reserveItem.get(i).getAmount(),
                    reserveItem.get(i).getQuantity(), lastInsertedProduct, reserve);

            reserveItemRepository.save(itemReserve);
        }
//        after successful cart save send email confirmation
        sendEmail(email, reserve, reserve.getReservation_Code());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    public Product getProductId(long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id:" + id));
        return product;
    }

    /**
     * Retrieve data
     * 
     * @param id
     * @return
     */
    @GetMapping("/getPickupInfo/{id}")
    public ResponseEntity<?> getPickupInfo(@PathVariable long id) {

        return new ResponseEntity<>(reservationRepository.pickupInfo(id), HttpStatus.OK);
    }

    /**
     * Fetch reservation info by id
     * 
     * @param id
     * @return
     */
    @GetMapping("/getReservationById/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable long id) {

        return new ResponseEntity<>(reservationRepository.findById(id), HttpStatus.OK);
    }

    /**
     * fetch reserved item by id reservation id
     */
    @GetMapping("/getReservedItemById/{id}")
    public ResponseEntity<?> getReservedItemById(@PathVariable long id) {

        return new ResponseEntity<>(reserveItemRepository.findByReservationId(id), HttpStatus.OK);
    }

    /**
     * get all reservation
     */

    @GetMapping("/getReservation")
    public ResponseEntity<?> getReservation() {

        return new ResponseEntity<>(reservationRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getReservationLastRecord")
    public ResponseEntity<?> getReservationLastRecord() {

        return new ResponseEntity<>(reservationRepository.findLastRecord(), HttpStatus.OK);
    }

    /**
     * send email with a link to a user to change password
     * 
     * @param recipientEmail
     * @param link
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private void sendEmail(String recipientEmail, Reservation reserved, String code)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("sportsrentReset@gmail.com", "Sportrent Support");

        helper.setTo(recipientEmail);

        String subject = "Reservation confirmation email";

        String content = " <p>RESERVATION CONFIRMATION </p>"
                + "<html><body>"
                + " <table style='border:2px solid black width:100%'>"
                + "<tr style =bgcolor:#33CC99>"
                + "     <td colspan='3'>   Reservation Code:   " + code
                + " </td>            <td colspan='2'><h3>Sports Rent</h3><td></tr>"
                + "<tr> <td colspan='3'>   Reservation Date:    " + reserved.getStartDate()
                + "         <td colspan='2'>" + reserved.getAddress() + " </td></tr>"
                + "<tr> <td colspan='3'>                                           <td colspan='2'>12345 Your City </td></tr>"
                + "<tr> <td colspan='3'>   Reservation Detail                      <td colspan='2'> COUNTRY </td></tr>"
                + "<tr> <td colspan='3'>  Pickup:  </td> <td colspan='2'>   " + reserved.getStartDate() + " </td></tr>"
                + " <tr> <td colspan='3'>  Return:  </td> <td colspan='2'>  " + reserved.getEndDate()
                + "  </td></tr><br><br>"
                + "<tr> <td colspan='5'>    Reserved :<td></tr>"
                + " <tr> <td colspan='5'>    Name:   " + reserved.getPickupFullName() + " </td></tr>"
                + " <tr> <td colspan='5'>    Contact: email@email.com </td></tr>"
                + " <tr> <td>   Product Name </td>        <td> COST </td>   <td> QUANTITY</td> <td> AMOUNT      </td></tr>"
                + " <tr> <td>   14’ Aluminium Boat </td>        <td> $130    </td>   <td>   1     </td> <td>  $130          </td></tr> "
                + " <tr> <td colspan='2'> </td>                         <td> Subtotal   </td>   <td> $130          </td></tr>"
                + " <tr> <td colspan='2'>  </td>                        <td> VAT rate (%)</td>  <td>         5%  </td></tr>"
                + " <tr> <td colspan='2'>   </td>                       <td> VAT          </td> <td> $6.5          </td></tr>  "
                + " <tr> <td colspan='2'>   </td>                        <td> Total        </td> <td> $ 136.5         </td></tr>"
                + " </table></body></html>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

}
