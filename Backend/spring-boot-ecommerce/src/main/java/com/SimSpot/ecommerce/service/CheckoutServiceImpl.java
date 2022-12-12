package com.SimSpot.ecommerce.service;

import com.SimSpot.ecommerce.dto.PaymentInfo;
import com.SimSpot.ecommerce.dao.CustomerRepository;
import com.SimSpot.ecommerce.dto.Purchase;
import com.SimSpot.ecommerce.dto.PurchaseResponse;
import com.SimSpot.ecommerce.entity.Customer;
import com.SimSpot.ecommerce.entity.Order;
import com.SimSpot.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * This is a Checkout Implementation for the Checkout service
 * We implement the checkout service to expose and override the underlying methods
 */
@Service
public class CheckoutServiceImpl implements CheckoutService{


    // New customerRepository object
    private CustomerRepository customerRepository;

    // Constructor, includes the applications stripe api 'secret key'
    public CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;

        // Initialise Stripe API with key
        Stripe.apiKey = secretKey;

    }

    /**
     *  This method placeOrder, takes a purchase object as a parameter,
     *  creates and saves the purchase to the DB, and returns a String containing the
     *  orderTrackingNumber
     * @param purchase
     * @return
     */
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //Retrieve order info
        Order order = purchase.getOrder();
        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));
        // populate order with billing and shipping address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());
        // populate customer with order
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer
        String theEmail = customer.getEmail();
        Customer customerfromDB = customerRepository.findByEmail(theEmail);

        if(customerfromDB != null){
            // assign
            customer = customerfromDB;
        }

        customer.add(order);
        // save to db
        customerRepository.save(customer);
        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    /**
     *  This method creates a new PaymentIntent object, and takes paymentInfo as a parameter
     *  PaymentInfo is an object containing fields amount,currency, and email. This method is implemented to feed Payment details
     *  to the Stripe API.
     * @param paymentInfo
     * @return
     * @throws StripeException
     */

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Sim Spot Purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());
        return PaymentIntent.create(params);
    }


    /**
     * This is a method to return a randomly generate a OrderTrackingNumber
     * @return
     */
    private String generateOrderTrackingNumber() {
        // generate random UUID number

        return UUID.randomUUID().toString();
    }
}
