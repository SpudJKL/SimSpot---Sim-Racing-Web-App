import { Component, OnInit } from '@angular/core';
import { CartItem } from 'src/app/common/cart-item';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart-details',
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.css']
})
export class CartDetailsComponent implements OnInit {

  cartItems: CartItem[] = [];
  totalPrice: number = 0;
  totalQuantity: number = 0;
  isViewingProducts = false;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.listCartDetails();
    this.isViewingProducts = true;
  }

  listCartDetails() {
    
    // get a handle to the cart items
    this.cartItems = this.cartService.cartItems;

    // subscribe to the cart totalPrice
    this.cartService.totalPrice.subscribe(
      data => this.totalPrice = data
    );
    // subscribe to the cart totalQunatity
    this.cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data
    );
    // compute totals
      this.cartService.computeCartTotals();

  }
   remove(theCartItem: CartItem) {
     this.cartService.remove(theCartItem);
   }

   viewingProducts(Status: boolean) {
      if (Status) {
        
      }
   }

}
