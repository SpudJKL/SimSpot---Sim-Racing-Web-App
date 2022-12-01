import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Country } from 'src/app/common/country';
import { County } from 'src/app/common/county';
import { Order } from 'src/app/common/order';
import { OrderItem } from 'src/app/common/order-item';
import { PaymentInfo } from 'src/app/common/payment-info';
import { Purchase } from 'src/app/common/purchase';
import { CartService } from 'src/app/services/cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { FormService } from 'src/app/services/form.service';
import { SimSpotValidators } from 'src/app/validators/sim-spot-validators';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  checkoutFormGroup: FormGroup;

  totalPrice: number = 0;
  totalQuantity: number = 0;

  creditCardYears: number[] = [];
  creditCardMonths: number[] = [];

  countries: Country[] = [];
  counties: County[] = [];

  shippingAddressCounties: County[] = [];
  billingAddressCounties: County[] = [];

  isDisabled: boolean = false;

  // Stripe api
  stripe = Stripe(environment.stripePublishableKey);

  paymentInfo: PaymentInfo = new PaymentInfo();
  cardElement: any;
  displayError: any = "";


  constructor(private formBuilder: FormBuilder,
    private FormService: FormService,
    private cartService: CartService,
    private checkoutService: CheckoutService,
    private router: Router) { }

  ngOnInit(): void {

    this.setupStripePaymentForm();

    this.reviewCartDetails();

    this.checkoutFormGroup = this.formBuilder.group({

      customer: this.formBuilder.group({
        firstName: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace
        ]),
        lastName: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace
        ]),
        email: new FormControl('', [Validators.required,
        Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])
      }),

      shippingAddress: this.formBuilder.group({
        street: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace]),

        city: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace]),

        country: new FormControl('', [Validators.required]),
        county: new FormControl('', [Validators.required]),
        postCode: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace]),
      }),

      billingAddress: this.formBuilder.group({
        street: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace]),

        city: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace]),

        country: new FormControl('', [Validators.required]),
        county: new FormControl('', [Validators.required]),
        postCode: new FormControl('', [Validators.required,
        Validators.minLength(2),
        SimSpotValidators.notOnlyWhitespace])
      }),
      creditCard: this.formBuilder.group({
        // cardType: new FormControl('', [Validators.required]),
        // nameOnCard: new FormControl('', [Validators.required,
        // Validators.minLength(2),
        // SimSpotValidators.notOnlyWhitespace]),
        // cardNumber: new FormControl('', [Validators.required,
        // Validators.minLength(16),
        // Validators.pattern('[0-9]{16}'),
        // SimSpotValidators.notOnlyWhitespace]),
        // securityCode: new FormControl('', [Validators.required,
        // Validators.minLength(3),
        // Validators.pattern('[0-9]{3}'),
        // SimSpotValidators.notOnlyWhitespace]),
        // expMonth: new FormControl('', [Validators.required]),
        // expYear: new FormControl('', [Validators.required]),
      })
    });

    // populate countries 

    this.FormService.getCountries().subscribe(
      data => {
        console.log("Retrieved Countries: " + JSON.stringify(data));
        this.countries = data;
      }
    )

  }

  setupStripePaymentForm() {
    var elements = this.stripe.elements();

    this.cardElement = elements.create('card', { hidePostalCode: true });

    this.cardElement.mount('#card-element');

    this.cardElement.on('change', (event) => {

      this.displayError = document.getElementById('card-errors');

      if (event.complete) {
        this.displayError.textContent = "";
      } else if (event.error) {
        this.displayError.textContent = event.error.message;
      }

    });

  }

  reviewCartDetails() {

    // subscribe to cartService.totalQuantity
    this.cartService.totalQuantity.subscribe(
      totalQuantity => this.totalQuantity = totalQuantity
    );
    // subscribe to cartService.totalPrice
    this.cartService.totalPrice.subscribe(
      totalPrice => this.totalPrice = totalPrice
    );
  }

  copyShippingAddressToBillingAddress(event) {

    if (event.target.checked) {
      this.checkoutFormGroup.controls['billingAddress']
        .setValue(this.checkoutFormGroup.controls['shippingAddress'].value);

      this.billingAddressCounties = this.shippingAddressCounties;
    }
    else {
      this.checkoutFormGroup.controls['billingAddress'].reset();
      this.billingAddressCounties = [];
    }


  }

  onSubmit() {
    console.log("Handling the submit button");

    // if (this.checkoutFormGroup.invalid) {
    //   this.checkoutFormGroup.markAllAsTouched();
    //   console.log("Error here");
    //   return;
    // }

    console.log("Setting up order");
    // set up order

    let order = new Order();
    order.totalPrice = this.totalPrice;
    order.totalQuantity = this.totalQuantity;
    console.log(`this.totalPrice: ${this.totalPrice}`);

    // get cart items

    const cartItems = this.cartService.cartItems

    // create orderItems from cartItems

    let orderItems: OrderItem[] = [];
    for (let i = 0; i < cartItems.length; i++) {
      orderItems[i] = new OrderItem(cartItems[i]);
    }

    // set up purchase 
    let purchase = new Purchase();
    // populate purchase - customer

    purchase.customer = this.checkoutFormGroup.controls['customer'].value;

    // populate purchase - shipping Address

    purchase.shippingAddress = this.checkoutFormGroup.controls['shippingAddress'].value;
    const shippingCountry: Country = JSON.parse(JSON.stringify(purchase.shippingAddress.country));
    const shippingCounty: County = JSON.parse(JSON.stringify(purchase.shippingAddress.county));
    purchase.shippingAddress.county = shippingCounty.name;
    purchase.shippingAddress.country = shippingCountry.name;

    // populate purchase - billing Address

    purchase.billingAddress = this.checkoutFormGroup.controls['billingAddress'].value;
    const billingCountry: Country = JSON.parse(JSON.stringify(purchase.billingAddress.country));
    const billingCounty: County = JSON.parse(JSON.stringify(purchase.billingAddress.county));
    purchase.billingAddress.county = billingCounty.name;
    purchase.billingAddress.country = billingCountry.name;


    // populate purchase - order and orderItems

    purchase.order = order;
    purchase.orderItems = orderItems;

    //  compute paymentInfo
    this.paymentInfo.amount = Math.round (this.totalPrice * 100);
    this.paymentInfo.currency = "EUR";
    this.paymentInfo.receiptEmail = purchase.customer.email;

    // call rest APIa via checkoutService
    this.isDisabled = true;
    if (this.displayError.textContent === "") {

      this.checkoutService.createPaymentIntent(this.paymentInfo).subscribe(
        (paymentIntentResponse) => {
          this.stripe.confirmCardPayment(paymentIntentResponse.client_secret,
            {
              payment_method: {
                card: this.cardElement,
                billing_details: {
                  email: purchase.customer.email,
                  name: `${purchase.customer.firstName} ${purchase.customer.lastName}`,
                  address: {
                    line1: purchase.billingAddress.street,
                    city: purchase.billingAddress.city,
                    state: purchase.billingAddress.county,
                    postal_code: purchase.billingAddress.postCode,
                    country: this.billingAddressCountry.value.code
                  }
                }
              }
            }, { handleActions: false })
          .then(function(result) {
            if (result.error) {
              // inform the customer there was an error
              alert(`There was an error: ${result.error.message}`);
              this.isDisabled = false;
            } else {
              // call REST API via the CheckoutService
              this.checkoutService.placeOrder(purchase).subscribe({
                next: response => {
                  alert(`Your order has been received.\nOrder tracking number: ${response.orderTrackingNumber}`);

                  // reset cart
                  this.resetCart();
                  this.isDisabled = false;
                },
                error: err => {
                  alert(`There was an error: ${err.message}`);
                  this.isDisabled = false;
                }
              })
            }            
          }.bind(this));
        }
      );
    } else {
      this.checkoutFormGroup.markAllAsTouched();
      return;
    }

  }

  resetCart() {
    // rest cart data 
    this.cartService.cartItems = [];
    this.cartService.totalPrice.next(0);
    this.cartService.totalQuantity.next(0);
    this.cartService.persistCartItems();
    // rest form data
    this.checkoutFormGroup.reset();
    // go back to products page
    this.router.navigateByUrl("/products");

  }


  handleMonthsAndYears() {
    const creditCardFormGroup = this.checkoutFormGroup.get('creditCard');

    const currentYear: number = new Date().getFullYear();
    const selectedYear: number = Number(creditCardFormGroup.value.expYear);

    // if the current year = selected year 
    let startMonth: number;

    if (currentYear == selectedYear) {
      startMonth = new Date().getMonth() + 1;
    }
    else {
      startMonth = 1;
    }

    this.FormService.getCreditCardMonths(startMonth).subscribe(
      data => {
        console.log("Retrieved credit card months: " + JSON.stringify(data));
        this.creditCardMonths = data;
      }
    );
  }


  get firstName() {
    return this.checkoutFormGroup.get('customer.firstName');
  }
  get lastName() {
    return this.checkoutFormGroup.get('customer.lastName');
  }
  get email() {
    return this.checkoutFormGroup.get('customer.email');
  }



  get shippingAddressStreet() {
    return this.checkoutFormGroup.get('shippingAddress.street');
  }
  get shippingAddressCity() {
    return this.checkoutFormGroup.get('shippingAddress.city');
  }
  get shippingAddressCountry() {
    return this.checkoutFormGroup.get('shippingAddress.country');
  }
  get shippingAddressCounty() {
    return this.checkoutFormGroup.get('shippingAddress.county');
  }
  get shippingAddressPostCode() {
    return this.checkoutFormGroup.get('shippingAddress.postCode');
  }


  get billingAddressStreet() {
    return this.checkoutFormGroup.get('billingAddress.street');
  }
  get billingAddressCity() {
    return this.checkoutFormGroup.get('billingAddress.city');
  }
  get billingAddressCountry() {
    return this.checkoutFormGroup.get('billingAddress.country');
  }
  get billingAddressCounty() {
    return this.checkoutFormGroup.get('billingAddress.county');
  }
  get billingAddressPostCode() {
    return this.checkoutFormGroup.get('billingAddress.postCode');
  }

  get cardType() {
    return this.checkoutFormGroup.get('creditCard.cardType');
  }
  get nameOnCard() {
    return this.checkoutFormGroup.get('creditCard.nameOnCard');
  }
  get cardNumber() {
    return this.checkoutFormGroup.get('creditCard.cardNumber');
  }
  get securityCode() {
    return this.checkoutFormGroup.get('creditCard.securityCode');
  }
  get expMonth() {
    return this.checkoutFormGroup.get('creditCard.expMonth');
  }
  get expYear() {
    return this.checkoutFormGroup.get('creditCard.expYear');
  }

  getCounties(formGroupName: string) {
    const formGroup = this.checkoutFormGroup.get(formGroupName);

    const countryCode = formGroup.value.country.code;
    const countryName = formGroup.value.country.name;

    this.FormService.getCounties(countryCode).subscribe(
      data => {
        if (formGroupName == 'shippingAddress') {
          this.shippingAddressCounties = data;
        } else {
          this.billingAddressCounties = data;
        }
        // select first item by default 
        formGroup.get('county').setValue(data[0]);
      }
    );
  }
}



