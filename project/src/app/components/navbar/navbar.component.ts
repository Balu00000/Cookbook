import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { Router } from '@angular/router';
import { AuthGuard } from '../../_guards/guard.guard';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { HttpClient } from '@angular/common/http';
import { FoodService } from '../../services/food.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { TruncatePipe } from '../../_pipes/truncate.pipe';

interface Recipe {
  id: number;
  image: string;
  name: string;
  description: string;
  instructions: string;
}

@Component({
  selector: 'app-navbar',
  imports: [FormsModule, CommonModule, MatFormFieldModule, MatInputModule, MatIconModule, TruncatePipe],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  standalone: true,
})
export class NavbarComponent {
  ngOnInit() {
    this.foodService.getAllRecipes().subscribe({
      next: (data: any) => {
        if (data && Array.isArray(data.result)) {
          this.recipes = data.result; // Access the array inside 'result'
        } else {
          console.error('Unexpected data format:', data);
          this.recipes = []; // Set to empty array if incorrect data
        }
      },
      error: (error) => {
        console.error('Error fetching recipes:', error);
        this.recipes = [];
      },
    });
  }

  search: string = '';

  constructor(
    public authService: LoggedInServiceService,
    private router: Router,
    private foodService: FoodService,
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
      this.router.navigate(['/recipeAdd']);
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

  recipes: Recipe[] = []; // Ensure it's an empty array at the start

  // Dinamikus szűrt lista getter-je
  get filteredRecipes(): Recipe[] {
    if (!this.recipes || !Array.isArray(this.recipes)) {
      return []; // Return an empty array to prevent errors
    }

    if (!this.searchTerm.trim()) {
      return this.recipes;
    }

    const term = this.searchTerm.toLowerCase();
    return this.recipes.filter(
      (recipe) =>
        recipe.name.toLowerCase().includes(term) ||
        (recipe.description && recipe.description.toLowerCase().includes(term))
    );
  }

  viewRecipe(recipeName: string): void {
    const slug = recipeName
      .toLowerCase()
      .replace(/\s+/g, '-')
      .replace(/[^\w-]/g, '');
    this.router.navigate(['/recipe', slug]); // No ID, only the name
  }
}
