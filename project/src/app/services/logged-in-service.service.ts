import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoggedInServiceService {
  private readonly TOKEN_KEY = 'auth_token';  // Key used to store the token in localStorage
  private readonly ID = "user_id"

  constructor() {}

  // Method to log in the user and store the token in localStorage
  login(token: string, id: string, remember: boolean): void {
    if(remember){
      localStorage.setItem(this.TOKEN_KEY, token);
      localStorage.setItem(this.ID, id)
    }else{
      sessionStorage.setItem(this.TOKEN_KEY, token)
      sessionStorage.setItem(this.ID, id)
    }
  }

  // Method to log out the user and clear the token from localStorage
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.ID)
    sessionStorage.removeItem(this.TOKEN_KEY)
    sessionStorage.removeItem(this.ID)
  }

  // Method to check if the user is authenticated
  isAuthenticated(): boolean {
    // If the token exists in localStorage, the user is considered authenticated
    if(!localStorage.getItem(this.TOKEN_KEY)){
      return sessionStorage.getItem(this.TOKEN_KEY) !== null;
    }else{
      return localStorage.getItem(this.TOKEN_KEY) !== null;
    }
  }

  // Method to get the token (optional, if you need to access the token elsewhere)
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  whatUser():string |null{
    if(!localStorage.getItem(this.ID)){
      return sessionStorage.getItem(this.ID)
    }else{
      return localStorage.getItem(this.ID)
    }
  }
}