import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoggedInServiceService {
  private readonly TOKEN_KEY = 'auth_token';  // Key used to store the token in localStorage

  constructor() {}

  // Method to log in the user and store the token in localStorage
  login(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  // Method to log out the user and clear the token from localStorage
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  // Method to check if the user is authenticated
  isAuthenticated(): boolean {
    // If the token exists in localStorage, the user is considered authenticated
    return localStorage.getItem(this.TOKEN_KEY) !== null;
  }

  // Method to get the token (optional, if you need to access the token elsewhere)
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
  
}
