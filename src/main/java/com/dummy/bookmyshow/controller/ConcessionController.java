package com.dummy.bookmyshow.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.bookmyshow.entity.Concession;
import com.dummy.bookmyshow.entity.ConcessionOrder;
import com.dummy.bookmyshow.repository.BookingRepository;
import com.dummy.bookmyshow.repository.ConcessionOrderRepository;
import com.dummy.bookmyshow.repository.ConcessionRepository;
import com.dummy.bookmyshow.util.ResponseParser;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class ConcessionController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResponseParser responseParser;

    @Autowired
    private ConcessionRepository concessionRepository;

    @Autowired
    private ConcessionOrderRepository concessionOrderRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Add a concession
     * TODO : Only ADMIN user is allowed to use this API
     * 
     * @param concession
     * @return
     */
    @RequestMapping(value = "/addConcessions", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addConcession(@RequestBody List<Concession> concessions) {
        try {
            this.LOGGER.info("\n\n\n");
            this.LOGGER.info("addConcession() called with " + concessions.size() + " concessions");
            for (Concession concession : concessions) {
                this.LOGGER.info("\n\n");
                this.LOGGER.info("The concession object : \n" + concession.toString());
                validateInput(concession);
                this.LOGGER.info("addConcession() saving concession as " + concession.getConcessionId());
                if(!this.concessionRepository.existsById(concession.getConcessionId())) {
                    this.concessionRepository.save(concession);
                }
                else {
                    throw new IllegalArgumentException("Concession with the given ID " + concession.getConcessionId() + " already exist");
                }
                this.LOGGER.info("addConcession() saved concession as " + concession.getConcessionId() + " in DB");
            }
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
                    "Successfully saved concession", "Successfully saved concession"), HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e) {
            this.LOGGER.error("Error adding concession  " + e.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } 
        catch (Exception ex) {
            this.LOGGER.error("Error adding concession object " + ex.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param concessionIds
     */
    @RequestMapping(value = "/deleteConcessions", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void deleteConcession(@RequestBody List<Long> concessionIds) {
        try {
            this.LOGGER.info("deleteConcession() called with " + concessionIds.size() + " concessions");
            for (Long concessionId : concessionIds) {
                this.LOGGER.info("deleteConcession() deleting concession as " + concessionId);
                if (this.concessionRepository.existsById(concessionId)) {
                    this.concessionRepository.deleteById(concessionId);
                } else {
                    throw new IllegalArgumentException("No concession with the given ID: " + concessionId);
                }
            }
            this.LOGGER.info("deleteConcession() successfully deleted all concession: " + concessionIds);
        } catch (IllegalArgumentException e) {
            this.LOGGER.error("Error deleting concession  " + e.getMessage());
        } catch (Exception ex) {
            this.LOGGER.error("Error deleting concession object " + ex.getMessage());
        }
    }

    /**
     * TODO: Implement the edit feature
     * 
     * @param concession
     */
    @RequestMapping(value = "/editConcessions", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> editConcession(@RequestBody List<Concession> concessions) {
        try {
            this.LOGGER.info("\n\n\n");
            this.LOGGER.info("editConcession() called with " + concessions.size() + " concessions");
            for (Concession concession : concessions) {
                this.LOGGER.info("\n\n");
                this.LOGGER.info("The concession object : \n" + concession.toString());

                // Validate if null
                validateInputUpdate(concession);

                if (this.concessionRepository.existsById(concession.getConcessionId())) {
                    this.LOGGER.info("editConcession() updating concession as " + concession.getConcessionId());
                    this.concessionRepository.save(concession);
                } else {
                    throw new IllegalArgumentException(
                            "The Id given does not exists in DB : " + concession.getConcessionId());
                }

            }
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
                    "Successfully edited concession", "Successfully edited concession"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            this.LOGGER.error("Error edited concession  " + e.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            this.LOGGER.error("Error edited concession object " + ex.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param 
     */
    @RequestMapping(value = "/getAllConcessions", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Concession>> getAllConcessions() {
        try {
            this.LOGGER.info("\n\n\n");
            this.LOGGER.info("getAllConcessions() called");
            return new ResponseEntity<>(this.concessionRepository.findAll(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            this.LOGGER.error("Error edited concession  " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            this.LOGGER.error("Error edited concession object " + ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Order concessions
     * TODO : Order Concessions for User
     *
     * @param order_concession
     * @return
     */
    @RequestMapping(value = "/orderConcession", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> orderConcession(@RequestBody List<ConcessionOrder> concessionOrders) {
        try {
            this.LOGGER.info("\n\n\n");
            this.LOGGER.info("orderConcession() called with " + concessionOrders.size() +
                    " concession orders");
            for (ConcessionOrder concessionOrder : concessionOrders) {
                this.LOGGER.info("\n\n");
                this.LOGGER.info("The Concession Order : \n" + concessionOrder.toString() + "\n");
                validateOrderInput(concessionOrder);
                this.LOGGER.info("Checking if Booking ID " +
                        concessionOrder.getConcessionBookingId() + ", exists in DB");
                if (this.bookingRepository.existsById(concessionOrder.getConcessionBookingId())) {
                    this.LOGGER.info("Booking ID " +
                            concessionOrder.getConcessionBookingId() + ", exists in DB");

                    // Convert into array of strings
                    String[] concessionsOrdered = splitString(concessionOrder.getConcessions(),
                            ",");

                    this.LOGGER.info("Concessions Ordered : " + Arrays.toString(concessionsOrdered));

                    // Calculate Total
                    BigDecimal total_Price = new BigDecimal(0);
                    for (String concession : concessionsOrdered) {
                        if (!this.concessionRepository.existsById(Long.parseLong(concession))) {
                            throw new IllegalArgumentException(
                                    "No concession with the given ID: " + concession + ", exists");
                        } else {
                            Concession theConcession = this.concessionRepository.getOne(Long.parseLong(concession));
                            this.LOGGER.info("Concession Ordered : \n" + theConcession.toString() + "\n");
                            this.LOGGER.info("Concession Ordered Price : " + theConcession.getPrice());
                            this.LOGGER.info("Adding Operation : " + total_Price + " + " + theConcession.getPrice());
                            total_Price = total_Price.add(theConcession.getPrice());
                            continue;
                        }
                    }

                    concessionOrder.setTotalPrice(total_Price);
                    this.LOGGER.info("The Concession Order After Calculation : \n" + concessionOrder.toString() + "\n");
                    this.concessionOrderRepository.save(concessionOrder);

                } else {
                    throw new IllegalArgumentException(
                            "The Booking Id given is invalid : " +
                                    concessionOrder.getConcessionBookingId());
                }
            }
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
                    "Successfully saved concession order", "Successfully saved concession order"),
                    HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            this.LOGGER.error("Error adding concession order " + e.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            this.LOGGER.error("Error adding concession order object " + ex.getMessage());
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method will validate the input for concession object
     * 
     * @param concession
     */
    private void validateInput(Concession concession) {
        try {
            Assert.notNull(concession, "Concession object must not be null");
            Assert.hasLength(concession.getName(), "Concession name must not be null or empty");
            Assert.hasLength(concession.getDescription(), "Concession description must not be null or empty");
            Assert.isTrue(concession.getPrice().compareTo(new BigDecimal(0.0)) > 0, "Price must not be 0.0 or less");
        } catch (IllegalArgumentException e) {
            this.LOGGER.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * This method will validate the input for concession object
     * 
     * @param concession
     */
    private void validateInputUpdate(Concession concession) {
        try {
            Assert.notNull(concession, "Concession object must not be null");
            Assert.notNull(concession.getConcessionId(), "Concession ID must not be null");
        } catch (IllegalArgumentException e) {
            this.LOGGER.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * This method will validate the input for concession object
     * 
     * @param concession
     */
    private void validateOrderInput(ConcessionOrder concessionOrder) {
        try {
            Assert.notNull(concessionOrder, "Concession object must not be null");
            Assert.notNull(concessionOrder.getConcessionBookingId(), "Booking ID must not be null or empty");
            Assert.hasLength(concessionOrder.getConcessions(), "Concession description must not be null or empty");
        } catch (IllegalArgumentException e) {
            this.LOGGER.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static String[] splitString(String input, String delimiter) {
        return input.split(delimiter);
    }

    public static String[] removeDuplicates(String[] input) {
        HashSet<String> set = new HashSet<String>(Arrays.asList(input));
        return set.toArray(new String[set.size()]);
    }

}