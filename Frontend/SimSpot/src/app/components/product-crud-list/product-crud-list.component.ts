import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/common/product';
import { productCRUD } from 'src/app/services/product-crud.service';

@Component({
  selector: 'app-product-crud-list',
  templateUrl: './product-crud-list.component.html',
  styleUrls: ['./product-crud-list.component.css']
})
export class ProductCrudListComponent implements OnInit {


  products?: Product[];
  currentProducts: Product = {};
  currentIndex = -1;
  name = '';

  constructor(private productCRUD: productCRUD, ) { }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  retrieveProducts(): void {
    this.productCRUD.getAll()
      .subscribe({
        next: (data) => {
          this.products = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  refreshList(): void {
    this.retrieveProducts();
    this.currentProducts = {};
    this.currentIndex = -1;
  }


  removeAllProducts(): void {
    this.productCRUD.deleteAll()
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => console.error(e)
      });
  }

  searchName(): void {
    this.currentProducts = {};
    this.currentIndex = -1;

    this.productCRUD.findByName(this.name)
      .subscribe({
        next: (data) => {
          this.products = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  setActiveProduct(product: Product, index: number): void {
    this.currentProducts = product;
    this.currentIndex = index;
  }

}