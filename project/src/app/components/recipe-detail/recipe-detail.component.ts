import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DiscoverService } from '../../services/discover.service';
import { NavbarComponent } from "../navbar/navbar.component";

@Component({
  imports: [CommonModule, NavbarComponent],
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css'],
})
export class RecipeDetailComponent implements OnInit {
  recipeDetails: any = {};
  ingredients: any = {}

  constructor(private route: ActivatedRoute, private discoverService: DiscoverService) {}

  ngOnInit(): void {
    this.recipeDetails = this.route.snapshot.data['recipe'];
    if(this.recipeDetails.instructions){
      this.recipeDetails.instructions = this.recipeDetails.instructions.split('§')  
    }else{
      this.recipeDetails.instructions
    }
    this.getIngredientByFoodId()
    this.minuteToHour()
  }

  getIngredientByFoodId(): void {
    this.discoverService.getIngredientById(this.recipeDetails.id).subscribe({
      next: (response) => {
        this.ingredients = response.result
      },
      error: (error) => {
        console.error('Error in getById: ', error)
        this.ingredients = []; // fallback to empty array on error
      } 
    })
  }

  paddedHours: string = ""
  paddedMinutes: string = ""

  minuteToHour(): void{

    const minutes = this.recipeDetails.prepTime || 0;
    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;

    this.paddedHours = hours.toString().padStart(2, '0');
    this. paddedMinutes = remainingMinutes.toString().padStart(2, '0');
  }
}
