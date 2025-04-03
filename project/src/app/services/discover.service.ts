import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DiscoverService {

  constructor(private http: HttpClient) {}

  baseURL: string = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/"
  
  difficulty(): Observable<any>{
    return this.http.get(`${this.baseURL}difficulty/getAllDifficulty`)
  }

  mealType(): Observable<any>{
    return this.http.get(`${this.baseURL}mealType/getAllMealType`)
  }

  cuisine(): Observable<any> {
    return this.http.get(`${this.baseURL}cuisine/getAllCuisine`)
  }

  dietary(): Observable<any> {
    return this.http.get(`${this.baseURL}dietary/getAllDietary`)
  }
}
