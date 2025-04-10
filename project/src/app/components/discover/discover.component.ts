import {
  Component,
  ElementRef,
  ViewChild,
  Renderer2,
  ChangeDetectorRef,
  output,
  Output,
  EventEmitter,
  model,
} from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TruncatePipe } from '../../_pipes/truncate.pipe';
import { FoodService } from '../../services/food.service';
import { Router, RouterLink } from '@angular/router';
import { DiscoverService } from '../../services/discover.service';

@Component({
  selector: 'app-discover',
  imports: [
    NavbarComponent,
    CommonModule,
    FormsModule,
    TruncatePipe,
    RouterLink,
  ],
  templateUrl: './discover.component.html',
  styleUrl: './discover.component.css',
})
export class DiscoverComponent {
  isDropdownOpen: boolean = false;
  isDropdownOpen2: boolean = false;
  isDropdownOpen3: boolean = false;
  isDropdownOpen4: boolean = false;

  difficulties: { name: string }[] = [];
  mealTypes: { type: string }[] = [];
  cuisines: { name: string }[] = [];
  dietarys: { type: string }[] = [];

  allFood: any[] = [];
  filteredFood: any[] = [];

  selectedDifficulties: { [key: string]: boolean } = {};
  selectedMealTypes: { [key: string]: boolean } = {};
  selectedCuisines: { [key: string]: boolean } = {};
  selectedDietarys: { [key: string]: boolean } = {};

  constructor(
    private foodService: FoodService,
    private router: Router,
    private discover: DiscoverService
  ) {}

  ngOnInit() {
    this.foodService.getAllRecipes().subscribe(
      (data) => {
        console.log('Food API Response: ', data); // Debugging
        this.allFood = data.result || data; // Ensure you extract the array properly
        this.filteredFood = [...this.allFood]; // Copy the array properly
      },
      (error) => {
        console.error('Error fetching food data:', error);
      }
    );
  }

  toggleSelection(category: string, value: string) {
    switch (category) {
      case 'difficulty':
        this.selectedDifficulties[value] = !this.selectedDifficulties[value];
        break;
      case 'mealType':
        this.selectedMealTypes[value] = !this.selectedMealTypes[value];
        break;
      case 'cuisine':
        this.selectedCuisines[value] = !this.selectedCuisines[value];
        break;
      case 'dietary':
        this.selectedDietarys[value] = !this.selectedDietarys[value];
        break;
      default:
        this.applyFilters();
        break;
    }
    this.applyFilters();
  }

  applyFilters() {
    this.filteredFood = this.allFood.filter((item) => {
      const difficultyMatch = Object.values(this.selectedDifficulties).some(
        (v) => v
      )
        ? !!this.selectedDifficulties[item.difficultyName]
        : true;

      const mealTypeMatch = Object.values(this.selectedMealTypes).some((v) => v)
        ? !!this.selectedMealTypes[item.mealTypeName]
        : true;

      const cuisineMatch = Object.values(this.selectedCuisines).some((v) => v)
        ? !!this.selectedCuisines[item.cuisineName]
        : true;

      const dietaryMatch = Object.values(this.selectedDietarys).some((v) => v)
        ? !!this.selectedDietarys[item.dietaryType]
        : true;

      return difficultyMatch && mealTypeMatch && cuisineMatch && dietaryMatch;
    });
  }

  clearFilters() {
    // Reset the selected filters
    this.selectedDifficulties = {};
    this.selectedMealTypes = {};
    this.selectedCuisines = {};
    this.selectedDietarys = {};
    this.applyFilters();
  }

  get isFilterActive(): boolean {
    return (
      Object.values(this.selectedDifficulties).some((value) => value) ||
      Object.values(this.selectedMealTypes).some((value) => value) ||
      Object.values(this.selectedCuisines).some((value) => value) ||
      Object.values(this.selectedDietarys).some((value) => value)
    );
  }

  difficulty(): void {
    this.discover.difficulty().subscribe({
      next: (response) => {
        this.difficulties = response.result;
        this.isDropdownOpen = !this.isDropdownOpen; // Toggle visibility
      },
      error: (error) => console.error("Error Fetching: ", error)
    });
  }

  mealType(): void {
    this.discover.mealType().subscribe({
      next: (response) => {
        this.mealTypes = response.result
        this.isDropdownOpen2 = !this.isDropdownOpen2;
      },
      error: (error) => console.error("Error Fetching: ", error)
    })
  }

  cuisine(): void {
    this.discover.cuisine().subscribe({
      next: (response) => {
        this.cuisines = response.result
        this.isDropdownOpen3 = !this.isDropdownOpen3;
      },
      error: (error) => console.error("Error Fetching: ", error)
    })
  }

  dietary(): void {
    this.discover.dietary().subscribe({
      next: (response) => {
        this.dietarys = response.result
        this.isDropdownOpen4 = !this.isDropdownOpen4;
      },
      error: (error) => console.error("Error Fetching: ", error)
    })
  }

  viewRecipe(recipeName: string): void {
    const slug = recipeName
      .toLowerCase()
      .replace(/\s+/g, '-')
      .replace(/[^\w-]/g, '');
    this.router.navigate(['/recipe', slug]); // No ID, only the name
  }
}
