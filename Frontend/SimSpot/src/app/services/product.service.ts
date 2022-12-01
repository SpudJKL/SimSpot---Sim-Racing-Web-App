import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../common/product';
import { Observable } from 'rxjs';
import { map }  from 'rxjs/operators';
import { Category } from '../common/category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  


  private baseUrl = 'http://localhost:8080/api/products';
  private categoryUrl ="http://localhost:8080/api/category"

  constructor(private httpClient: HttpClient) { }

  getProductList(theCategoryID: number): Observable<Product[]> {

    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryID}`;

    return this.getProducts(searchUrl);
    
    }

    getProductListPaginate(thePage: number, thePageSize: 
                            number,theCategoryID: number ): Observable<GetResponseProduct> {

      const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryID}` 
                            +`&page=${thePage}&size=${thePageSize}`;
  
      return this.httpClient.get<GetResponseProduct>(searchUrl);
      
      }

    getProduct(theProductId: number): Observable<Product> {
      // build url based on productID
      const productUrl = `${this.baseUrl}/${theProductId}`;
      return this.httpClient.get<Product>(productUrl);
    }
  
    searchProducts(theKeyword: string): Observable<Product[]> {
      // build url based on keyword entered by user 
      const searchUrl = `${this.baseUrl}/search/findByNameContaining?name=${theKeyword}`;

      return this.getProducts(searchUrl);
    }

    searchProductsPaginate(thePage: number, thePageSize: 
                            number,theKeyword: string ): Observable<GetResponseProduct> {

      const searchUrl = `${this.baseUrl}/search/findByNameContaining?name=${theKeyword}` 
                       +`&page=${thePage}&size=${thePageSize}`;

      return this.httpClient.get<GetResponseProduct>(searchUrl);

}
  
  private getProducts(searchUrl: string): Observable<Product[]> {
    return this.httpClient.get<GetResponseProduct>(searchUrl).pipe(
      map(response => response._embedded.products)
    );
  }

    getProductCategories(): Observable<Category[]> {
    
    return this.httpClient.get<GetResponseCategory>(this.categoryUrl).pipe( 
      map(response => response._embedded.productCategory)
    );
    }
  }



interface GetResponseProduct {
  _embedded: {
    products: Product[];
  },
  page : {
    size : number,
    totalElements : number,
    totalPages : number,
    number : number
  }
}

  interface GetResponseCategory {
    _embedded: {
      productCategory: Category[];
    }
}
