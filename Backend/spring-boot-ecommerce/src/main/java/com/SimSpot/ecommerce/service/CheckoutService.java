package com.SimSpot.ecommerce.service;

import com.SimSpot.ecommerce.dto.PaymentInfo;
import com.SimSpot.ecommerce.dto.Purchase;
import com.SimSpot.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

/**
 * This is a Checkout interface, which allows the methods below to be implemented
 */

public interface CheckoutService {

    /**
     *  This is an abstract method to be implemented into the CheckoutServiceImpl
     * @param purchase
     * @return
     */

    PurchaseResponse placeOrder(Purchase purchase);


    /**
     * This is an abstract method to be implemented into the CheckoutServiceImpl
     * @param paymentInfo
     * @return
     * @throws StripeException
     */

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
