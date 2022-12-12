import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Country } from '../common/country';
import { map } from 'rxjs/operators';
import { County } from '../common/county';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  // private countriesUrl = "http://simspot-env.eba-sctpzcfa.us-east-1.elasticbeanstalk.com/api/countries";
  // private countiesUrl = "http://simspot-env.eba-sctpzcfa.us-east-1.elasticbeanstalk.com/api/counties";


  
  private countriesUrl = "http://localhost:8080/api/countries";
  private countiesUrl = "http://localhost:8080/api/counties";

  constructor(private httpClient: HttpClient) { }

  getCountries(): Observable<Country[]> {

    return this.httpClient.get<GetResponseCountries>(this.countriesUrl).pipe(
      map(response => response._embedded.countries)
    );
  }

  getCounties(theCountryCode: string): Observable<County[]> {

    // search url 

    const searchCountyUrl = `${this.countiesUrl}/search/findByCountryCode?code=${theCountryCode}`;

    return this.httpClient.get<GetResponseCounties>(searchCountyUrl).pipe(
      map(response => response._embedded.counties)
    );
  }


  getCreditCardMonths(startMonth: number): Observable<number[]> {

    let data: number[] = [];

    // Array for month dropdown list
    //
    for (let theMonth = startMonth; theMonth <=12; theMonth++) {
      data.push(theMonth);
    }
    return of(data);
  }
  getCreditCardYears(): Observable<number[]> {

    let data: number[] = [];

    // Array for Year dropdown list
    //

    const startYear: number = new Date().getFullYear();
    const endYear: number = startYear + 10;

    for (let theYear = startYear; theYear <= endYear; theYear++) {
      data.push(theYear);
    }
    return of(data);
  }
}

interface GetResponseCountries  {
  _embedded: {
    countries: Country[];
  }
}

interface GetResponseCounties {
  _embedded: {
    counties: County[];
  }
}
