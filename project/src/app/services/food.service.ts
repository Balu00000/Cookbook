import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private http: HttpClient) { }

  apiUrl: string = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/"

  randomRecipe(): Observable<any> {
    return this.http.get(`${this.apiUrl}food/getFoodByRandom`)
  }

  getAllRecipes(){
    return this.http.get<any>(`${this.apiUrl}food/getAllFood`);
  }

  getRatedFood(): Observable<any> {
    return this.http.get(`${this.apiUrl}food/getFoodByRating`)
  }

  getFoodByAdded(): Observable<any> {
    return this.http.get(`${this.apiUrl}food/getFoodByAddedAt`)
  }
}
