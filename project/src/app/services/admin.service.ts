import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CardBodyComponent } from '@coreui/angular-pro';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private apiUrl =
    'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/';

  constructor(private http: HttpClient) {}

  // Fetch all users
  getAllUsers(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}user/getAllUser`);
  }

  // Delete user by ID
  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}user/deleteUserById?id=${id}`);
  }

  // Delte recipe by ID
  deleteRecipe(id: number): Observable<any> {
    return this.http.delete(
      `${this.apiUrl}food/deleteFoodById?id=${id}`
    );
  }

  makeUserAdmin(data: {
    username: string;
    image: string;
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/registerAdmin`, {
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
  }

  addRecipe(data: {
    name: string;
    image: string;
    description: string;
    preptime: string;
    userid: number;
    instructions: string;
    difficultyid: number;
    mealtypeid: number;
    cuisineid: number;
    ingredients: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}food/addFood`, data,{
      headers: {
        'Content-Type': 'application/json'
      }
    })
  }
}
