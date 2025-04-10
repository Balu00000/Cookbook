import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FoodService } from '../../services/food.service';

@Component({
    selector: 'app-random',
    imports: [NavbarComponent, FormsModule, CommonModule],
    templateUrl: './random.component.html',
    styleUrl: './random.component.css'
})
export class RandomComponent {

  constructor(private foodService: FoodService) {}

  recipe: any = {}
  
  ngOnInit(){
    this.foodService.randomRecipe().subscribe({
      next: (response) => {
        this.recipe = response.result
        this.recipe.instructions ? this.recipe.instructions = this.recipe.instructions.split('§') : this.recipe.instructions
      },
      error: (error) => console.error("Error Fetching: ", error)
    })
  }
}