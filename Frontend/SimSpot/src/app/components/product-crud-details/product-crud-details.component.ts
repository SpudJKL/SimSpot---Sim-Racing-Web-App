import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/common/product';
import { productCRUD } from 'src/app/services/product-crud.service';


@Component({
  selector: 'app-product-crud-details',
  templateUrl: './product-crud-details.component.html',
  styleUrls: ['./product-crud-details.component.css']
})
export class ProductCrudDetailsComponent implements OnInit {


  @Input() viewMode = false;

  @Input() currentProduct: Product = {
    description: '',
    id: 0,
    name: '',
    location: '',
    price: 0,
    imageUrl: '',
    status: false,
    deliveryMethod: false,
    ogBoxes: false,
    dateCreated: undefined,
    dateUpdated: undefined,
    category: undefined
    
  };
  
  message = '';

  constructor(
    private productCRUD: productCRUD,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getProduct(this.route.snapshot.params["id"]);
    }
  }

  getProduct(id: string): void {
    this.productCRUD.get(id)
      .subscribe({
        next: (data) => {
          this.currentProduct = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  updateProduct(): void {
    this.message = '';

    this.productCRUD.update(this.currentProduct.id, this.currentProduct)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This product listing was updated successfully!';
        },
        error: (e) => console.error(e)
      });
  }

  deleteProduct(): void {
    this.productCRUD.delete(this.currentProduct.id)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.router.navigate(['/products']);
        },
        error: (e) => console.error(e)
      });
  }

}