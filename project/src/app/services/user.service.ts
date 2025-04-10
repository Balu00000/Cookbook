import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoggedInServiceService } from './logged-in-service.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private whatUser: LoggedInServiceService) { }

  apiUrl: string = 'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/'

  getProfileInformation(): Observable<any> {
    return this.http.get(`${this.apiUrl}user/getUserProfileInformation?id=${this.whatUser.whatUser()}`)
  }

  getFoodByUser(): Observable<any> {
    return this.http.get(`${this.apiUrl}food/getFoodByUser?id=${this.whatUser.whatUser()}`)
  }
  
  getFavoriteByUser(): Observable<any> {
    return this.http.get(`${this.apiUrl}favourite/getFavouriteByUser?id=${this.whatUser.whatUser()}`)
  }
}
