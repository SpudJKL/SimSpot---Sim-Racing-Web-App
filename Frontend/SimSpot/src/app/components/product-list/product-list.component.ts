import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/services/cart.service';
import { productCRUD } from 'src/app/services/product-crud.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  productsCrud?: Product[];
  currentProduct: Product = {};
  products?: Product[] = [];
  currentCategoryID: number = 3;
  previousCategoryID: number = 3;
  searchMode: boolean = false;

  // pagniation properties

  thePageNumber: number = 1;
  thePageSize: number = 10;
  theTotalElements: number = 0;

  previousKeyword: string ="";


  constructor(private productService: ProductService,
              private cartService: CartService,
              private route: ActivatedRoute, 
              private productCRUD: productCRUD) { }

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
    this.listProducts();
    });
  }

  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if (this.searchMode){
      this.handleSearchProducts();
    }
    else {
      this.handleListProducts();
    }
    
  }

  handleSearchProducts() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    // if we have a different keyword than previous 
    // set thePageNumber to 1 
    if(this.previousKeyword != theKeyword){
      this.thePageNumber = 1;
    }

    this.previousKeyword = theKeyword;
    console.log(`keyword=${theKeyword}, thePageNumber=${this.thePageNumber}`)
    // now search using keyword
    this.productService.searchProductsPaginate(this.thePageNumber -1,
                                                this.thePageSize,
                                                theKeyword).subscribe(this.processResult());
  }

  handleListProducts() {


    const hasCategoryID:boolean = this.route.snapshot.paramMap.has('id');

    // Get categoryID, convert string to a number

    if(hasCategoryID){ 
      this.currentCategoryID = +this.route.snapshot.paramMap.get('id')!;
    }

    else {
        // if no ID give Default categoryID to 1
        this.currentCategoryID = 1;
    }

    // get id now 

    // Check if we have a different category id 
    // if so reset thePageNumber back to 1 
    if(this.previousCategoryID != this.currentCategoryID) {
      this.thePageNumber = 1;
    }

    this.previousCategoryID = this.currentCategoryID;

    console.log(`currentCategoryID=${this.currentCategoryID}, thePageNumber=${this.thePageNumber}`);


    this.productService.getProductListPaginate(this.thePageNumber -1, 
                                               this.thePageSize,
                                               this.currentCategoryID)
                                               .subscribe(this.processResult());
  }

  processResult() {
    return(data: any) => {
      this.products = data._embedded.products;
      this.thePageNumber = data.page.number + 1;
      this.thePageSize = data.page.size;
      this,this.theTotalElements = data.page.totalElements;
    }
  }


  addToCart(theProduct: Product){
    console.log(`Adding to cart: ${theProduct.name}, ${theProduct.price}`);

    const theCartItem = new CartItem(theProduct);

    this.cartService.addToCart(theCartItem);
  }

  
  retrieveTutorials(): void {
    this.productCRUD.getAll()
      .subscribe({
        next: (data) => {
          this.productsCrud = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  removeProduct(id): void {
    this.productCRUD.delete(id)
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (e) => console.error(e)
      });
  }


}
