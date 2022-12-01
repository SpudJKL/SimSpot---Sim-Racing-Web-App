import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/common/category';
import { Product } from 'src/app/common/product';
import { productCRUD } from 'src/app/services/product-crud.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  product: Product = {
    id: 0,
    name: "",
    description: "",
    location: "",
    price: 0,
    imageUrl: "",
    status: false,
    deliveryMethod: true,
    ogBoxes: false,
    dateCreated: undefined,
    dateUpdated: undefined,
    
  };
  
  submitted = false;

  productCategories: Category[] = [];

  constructor(private productCRUD: productCRUD,
    productService: ProductService) { }

  ngOnInit(): void {
    console.log(this.test);
  }

  saveProduct(): void {
    const data = {
      id: this.product.id,
      name: this.product.name,
      description: this.product.description,
      location: this.product.location,
      price: this.product.price,
      status: this.product.status,
      deliveryMethod: this.product.deliveryMethod,
      ogBoxes: this.product.ogBoxes,
      dateUpdated: new Date(),
    
    };

    this.productCRUD.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.submitted = true;
        },
        error: (e) => console.error(e)
      });
  }

  newProduct(): void {
    this.submitted = false;
    this.product = {
      id: 0,
      name: "",
      description: "",
      location: "",
      price: 0,
      status: false,
      deliveryMethod: false,
      ogBoxes: false,
      dateCreated: new Date(),
      dateUpdated: undefined,
      
    };
  }

  test = "";
  




}