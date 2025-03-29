import { CanActivateFn } from '@angular/router';
import { Router, CanActivate } from '@angular/router';
import { LoginService } from '../services/login.service';
import { Injectable } from '@angular/core';
import { LoggedInServiceService } from '../services/logged-in-service.service';


@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private loginService: LoggedInServiceService, private router: Router) {}
  canActivate(): boolean {
    const isAdmin = this.loginService.isUserAdmin();
    console.log("AuthGuard Check - isAdmin:", isAdmin);
    
    if (!isAdmin) {
      console.warn('AuthGuard: Not an admin! Redirecting...');
      this.router.navigate(['/login']);
      return false;
    }
  
    console.log("AuthGuard: Allowed through!");
    return true;
  }
  
}
