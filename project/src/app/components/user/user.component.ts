import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { ResourceLoader } from '@angular/compiler';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user',
  imports: [NavbarComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css',
})
export class UserComponent {
  constructor(
    private authService: LoggedInServiceService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.userService.getProfileInformation().subscribe({
      next: (response) => {
        this.user = response.result
      }, 
      error: (error) => console.error("Fetch Error: ", error)
    })

    this.userService.getFoodByUser().subscribe({
      next: (response) => {
        this.userAdded = response.result
      }, 
      error: (error) => console.error("Error Fetching: ", error)
    })

    this.userService.getFavoriteByUser().subscribe({
      next: (response) => {
        this.userFavorites = response.result
      }, 
      error: (error) => console.error("Error Fetching: ", error)
    })
  }

  user: any = {};
  userAdded: any = [];
  userFavorites: any = [];

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }
}
