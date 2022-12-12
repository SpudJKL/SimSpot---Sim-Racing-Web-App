import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../common/product';



// const baseUrl = 'http://simspot-env.eba-sctpzcfa.us-east-1.elasticbeanstalk.com/api/products';

const baseUrl = 'http://localhost:8080/api/products';

@Injectable({
  providedIn: 'root'
})
export class productCRUD {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(baseUrl);
  }

  get(id: any): Observable<Product> {
    return this.http.get<Product>(`${baseUrl}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/${id}`);
  }
  deleteAll(): Observable<any> {
    return this.http.delete(baseUrl);
  }

  findByName(name: any): Observable<Product[]> {
    return this.http.get<Product[]>(`${baseUrl}/search/findByNameContaining?name=${name}`);
  }
}