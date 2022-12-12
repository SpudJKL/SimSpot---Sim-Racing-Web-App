package com.SimSpot.ecommerce.controller;

import com.SimSpot.ecommerce.dto.PaymentInfo;
import com.SimSpot.ecommerce.dto.Purchase;
import com.SimSpot.ecommerce.dto.PurchaseResponse;
import com.SimSpot.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This CheckoutController class sets out the REST endpoints
 * for Post mapping for the CheckoutService
  */

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    // Instance of CheckoutService
    private CheckoutService checkoutService;
    // Constructor
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     * This method placeOrder takes a Purchase object as a param.
     * @PostMapping allows a POST request from the front end with the Purchase object to
     * be saved to the DB.
     * @param purchase
     * @return
     */
    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        return purchaseResponse;
    }

    /**
     * This method createPaymentIntent takes a PaymentInfo object as a param.
     * @PostMapping allows a POST request from the front end with the PaymentInfo object to
     * be sent to stripe via the API.
     * @param paymentInfo
     * @return
     * @throws StripeException
     */
    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);

    }



}
