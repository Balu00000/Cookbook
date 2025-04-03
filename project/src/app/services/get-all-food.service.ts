import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GetAllFoodService {

  constructor(private http: HttpClient) { }

  apiUrl = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/food/getAllFood"
  LALALALALALAL(){
    return this.http.get<any>(`${this.apiUrl}`);
  }
}
