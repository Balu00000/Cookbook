import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { Router } from '@angular/router';
import { AuthGuard } from '../../_guards/guard.guard';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { GetAllFoodService } from '../../services/get-all-food.service';
import { Observable } from 'rxjs';

interface Recipe {
  id: number;
  image: string;
  name: string;
  description: string;
}

@Component({
  selector: 'app-navbar',
  imports: [
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule, // ✅ Required for Angular Material
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  standalone: true,
})
export class NavbarComponent {
  ngOnInit() {
    if (this.authService.isAuthenticated()) {
    }
    this.getAllFood.LALALALALALAL().subscribe({
      next: (response) => (data: Recipe[]) => {
        this.recipes = data;
      },
      error: (error) => console.error('Error Fetching LALALA: ', error),
    });
  }

  search: string = '';

  constructor(
    public authService: LoggedInServiceService,
    private router: Router,
    private guard: AuthGuard,
    private http: HttpClient,
    private getAllFood: GetAllFoodService
  ) {}

  // This method will be called when the profile button is clicked
  onProfileClick(): void {
    if (this.authService.isAuthenticated()) {
      // If the user is authenticated, navigate to the profile page
      this.router.navigate(['/user']);
    } else {
      // If not authenticated, navigate to the login page
      this.router.navigate(['/register']);
    }
  }

  onFavoriteClick(): void {
    if (this.authService.isAuthenticated()) {
      // If the user is authenticated, navigate to the profile page
      this.router.navigate(['/user']);
    } else {
      // If not authenticated, navigate to the login page
      this.router.navigate(['/register']);
    }
  }

  onAdminClick(): void {
    console.log('Admin Status (raw):', this.authService.isUserAdmin());
    console.log(
      'Admin Status (boolean):',
      this.authService.isUserAdmin() === true
    );

    if (this.authService.isUserAdmin()) {
      console.log('Navigating to /admin...');
      this.router.navigate(['/admin']);
    } else {
      console.error('You are NOT an admin.');
      this.router.navigate(['/home']);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }

  searchTerm: string = '';

  recipes: Recipe[] = [];

  // Dinamikus szűrt lista getter-je
  get filteredRecipes(): Recipe[] {
    if (!this.searchTerm) {
      return this.recipes;
    }
    const term = this.searchTerm.toLowerCase();
    return this.recipes.filter(
      (recipe) =>
        recipe.name.toLowerCase().includes(term) ||
        (recipe.image && recipe.image.toLowerCase().includes(term)) ||
        (recipe.name && recipe.name.toLowerCase().includes(term)) ||
        (recipe.description && recipe.description.toLowerCase().includes(term))
    );
  }
}
