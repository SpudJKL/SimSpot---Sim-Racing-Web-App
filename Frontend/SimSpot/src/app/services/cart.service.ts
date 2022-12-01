import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { CartItem } from '../common/cart-item';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItems: CartItem[] = [];

  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  storage: Storage = sessionStorage;
  
  constructor() {
    // read data from storage
    let data = JSON.parse(this.storage.getItem('cartItems'));

    if (data != null) {
      this.cartItems = data;
    }
    // compute totals from stored data
    this.computeCartTotals();
  }

  addToCart(theCartItem: CartItem) {
    // check if we already have an item in our cart
    let alreadyExistsInCart: boolean = false;
    let existingCartItem: CartItem = undefined!;

    if (this.cartItems.length > 0) {
      // find the item in our cart based on id
      for (let tempCartItem of this.cartItems) {
        if (tempCartItem.id == theCartItem.id) {
          existingCartItem = tempCartItem;
          break;
        }
      }
      // check if we found it 
      alreadyExistsInCart = (existingCartItem != undefined);
    }

    if (alreadyExistsInCart) {
      // increment 
      existingCartItem.quantity++;

    }
    else {

      this.cartItems.push(theCartItem);
    }
    // compute cart total 

    this.computeCartTotals();

  }

  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let currentCartItem of this.cartItems) {
      totalPriceValue += currentCartItem.price * currentCartItem.quantity;
      totalQuantityValue += currentCartItem.quantity;
    }
    // publish values 
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
    // logging cart data 
    this.logCartData(totalPriceValue, totalPriceValue);

    // persist cart data 
    this.persistCartItems();
  }
  
  persistCartItems(){
    this.storage.setItem('cartItems', JSON.stringify(this.cartItems));

  }

  logCartData(totalPriceValue: number, totalQuantityValue: number) {
    console.log(`Contents of the cart`);
    for (let tempCartItem of this.cartItems) {
      const subTotalPrice = tempCartItem.price * tempCartItem.quantity;
      console.log(`name: ${tempCartItem.name}, price= ${tempCartItem.price},  quantity= ${tempCartItem.quantity}`)
    }
    console.log(`totalPrice ${totalPriceValue.toFixed(2)}, totalQuantity: ${totalQuantityValue}`);
  }


  remove(theCartItem: CartItem) {
    // get index of item in []
    const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.id == theCartItem.id)

    // if found the remove item from []
    if (itemIndex > -1) {
      this.cartItems.splice(itemIndex, 1);

      this.computeCartTotals();
    }
  }

}
