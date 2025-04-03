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

  recipe: any = {}
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
      let randomResult = await fetch(URL)
      if (!randomResult.ok) console.error("Response not good" + randomResult.status + " " + randomResult.statusText);

      let randomData = await randomResult.json()
      
      this.recipe = randomData.result
      if(this.recipe.instructions){
        this.recipe.instructions = this.recipe.instructions.split('§')  
      }else{
        this.recipe.instructions
      }
    } catch (error) {
      console.error(error);
    }
  }
}