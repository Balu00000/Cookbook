import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-random',
    imports: [NavbarComponent, FormsModule, CommonModule],
    templateUrl: './random.component.html',
    styleUrl: './random.component.css'
})
export class RandomComponent {

  recipeName:string = ""
  recipeImage: string= ""
  recipeDescription: string= ""
  recipeDifficulty: string = ""
  recipeFavoritedByPeople: string = ""
  recipeInstructions: string = ""
  recipeInstructionsSegments: string[] = []

  ngOnInit(){
    this.randomRecipe()
  }

  async randomRecipe() {
    const URL = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/food/getFoodByRandom"

    try {
      let randomResponse = await fetch(URL)
      if (!randomResponse.ok) console.error("Response not good" + randomResponse.status + " " + randomResponse.statusText);

      let randomData = await randomResponse.json()

      this.recipeName =                 randomData.result.name
      this.recipeDescription =          randomData.result.description
      this.recipeImage =                randomData.result.image
      this.recipeDifficulty =           randomData.result.difficulty
      this.recipeFavoritedByPeople =    randomData.result.rating
      this.recipeInstructions =         randomData.result.instructions
      this.recipeInstructionsSegments = this.recipeInstructions.split('§')

    } catch (error) {
      console.error(error);
    }
  }
}