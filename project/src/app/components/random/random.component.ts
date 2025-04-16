import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FoodService } from '../../services/food.service';
import { DiscoverService } from '../../services/discover.service';

@Component({
  selector: 'app-random',
  imports: [NavbarComponent, FormsModule, CommonModule],
  templateUrl: './random.component.html',
  styleUrl: './random.component.css',
})
export class RandomComponent implements OnInit {
  constructor(
    private foodService: FoodService,
    private discoverService: DiscoverService
  ) {}

  recipe: any = {};
  ingredients: any = {};

  ngOnInit() {
    this.foodService.randomRecipe().subscribe({
      next: (response) => {
        this.recipe = response.result;
        this.recipe.instructions
          ? (this.recipe.instructions = this.recipe.instructions.split('§'))
          : this.recipe.instructions;
        this.minuteToHour();
        this.getIngredientByFoodId();
      },
      error: (error) => console.error('Random Fetch Error: ', error),
    });
  }

  paddedHours: string = '';
  paddedMinutes: string = '';

  minuteToHour(): void {
    console.log(this.recipe);
    const minutes = Number(this.recipe.prepTime) || 0;
    console.log(minutes);
    const hours = Math.floor(minutes / 60);
    console.log(hours);
    const remainingMinutes = minutes % 60;
    console.log(remainingMinutes);

    this.paddedHours = hours.toString().padStart(2, '0');
    this.paddedMinutes = remainingMinutes.toString().padStart(2, '0');
  }

  getIngredientByFoodId(): void {
    console.log(this.recipe.id);
    this.discoverService.getIngredientById(this.recipe.id).subscribe({
      next: (response) => {
        this.ingredients = response.result;
      },
      error: (error) => {
        console.error('Error in getById: ', error);
        this.ingredients = []; // fallback to empty array on error
      },
    });
  }
}
