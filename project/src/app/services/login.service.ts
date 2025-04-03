import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { HashService } from './hash.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private router: Router, private sha1: HashService) {}

  private VALID_EMAIL: string[] = [];
  private VALID_PASSWORD: string[] = [];

  async fetch(
    email: string,
    password: string
  ): Promise<{ emails: string[]; passwords: string[] }> {
    const data = await fetch(
      'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/getAllUser'
    );
    const response = await data.json();

    if (Array.isArray(response.result)) {
      const user = response.result.find((user: any) => user.email === email);

      if (user && user.password === this.sha1.hashString(password)) {
        // Found the user with matching email and password
        this.VALID_EMAIL = [user.email];
        this.VALID_PASSWORD = [user.password];

        return { emails: this.VALID_EMAIL, passwords: this.VALID_PASSWORD };
      }
    } else {
      console.error('Unexpected response format:', response);
    }

    return { emails: [], passwords: [] };
  }

  loginUser(data: { email: string; password: string }): Promise<any> {
    const url =
      'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/login';
    console.log('Sending POST request to:', url, 'with data:', data);

    return fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        console.log('Response status:', response.status);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .catch((error) => {
        console.error('Error in POST request:', error);
        throw error;
      });
  }

  registerUser(data: {
    username: string;
    email: string;
    password: string;
    image: string;
  }): Promise<any> {
    const url =
      'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/registerUser';


    return fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        console.log('Response status:', response.status);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .catch((error) => {
        console.error('Error in POST request:', error);
        throw error;
      });
  }

  authenticate(username: string, password: string): Observable<boolean> {
    if (
      this.VALID_EMAIL.includes(username) &&
      this.VALID_PASSWORD.includes(this.sha1.hashString(password))
    ) {
      this.router.navigate(['/home']);
      return of(true);
    }
    return throwError(() => new Error('Balfasz felhasználó/password'));
  }

  isAdmin(): boolean {
    if (localStorage.getItem('isAdmin') || sessionStorage.getItem('isAdmin') === 'true') {
      return true;
    }
    return false;
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('isLoggedIn') === 'true';
  }

  logout(): void {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user_id');
    localStorage.removeItem('isAdmin');
    sessionStorage.removeItem('auth_token');
    sessionStorage.removeItem('user_id');
    sessionStorage.removeItem('isAdmin');
    localStorage.removeItem('isLoggedIn');
    this.router.navigate(['/login']);
  }
}
