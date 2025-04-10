import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import {
  CarouselCaptionComponent,
  CarouselComponent,
  CarouselControlComponent,
  CarouselIndicatorsComponent,
  CarouselInnerComponent,
  CarouselItemComponent,
  ThemeDirective,
  ButtonDirective,
} from '@coreui/angular-pro';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TruncatePipe } from '../../_pipes/truncate.pipe';

@Component({
  selector: 'app-user',
  imports: [CommonModule,
    NavbarComponent,
    CarouselIndicatorsComponent,
    RouterLink,
    CarouselComponent,
    CarouselInnerComponent,
    CarouselControlComponent,
    CarouselItemComponent,
    ThemeDirective,
    TruncatePipe,
    ButtonDirective,],
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

  getFormattedDate(): string {
    // Remove timezone ("CEST") for safe parsing
    const cleanDateStr = this.user.createdAt.replace(/CEST|GMT|UTC|[A-Z]{3,4}/g, '').trim();
    const date = new Date(cleanDateStr);

    if (isNaN(date.getTime())) {
      return 'Invalid date';
    }

    const year = date.getFullYear();
    const month = date.toLocaleString('en-US', { month: 'long' }); // "Sep"
    const day = String(date.getDate()).padStart(2, '0');

    return `${year} ${month} ${day}`;
  }  

  viewRecipe(recipeName: string): void {
    const slug = recipeName
      .toLowerCase()
      .replace(/\s+/g, '-')
      .replace(/[^\w-]/g, '');
    this.router.navigate(['/recipe', slug]); // No ID, only the name
  }
}
