import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { AdminService } from '../../services/admin.service';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { FoodService } from '../../services/food.service';
import { CommonModule } from '@angular/common';
import { PopoverModule } from '@coreui/angular-pro';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { DiscoverService } from '../../services/discover.service';

@Component({
  selector: 'app-recipe',
  imports: [
    NavbarComponent,
    ReactiveFormsModule,
    CommonModule,
    PopoverModule,],
  templateUrl: './recipe.component.html',
  styleUrl: './recipe.component.css'
})
export class RecipeComponent {

  ngOnInit() {
    this.getAllDifficulties();
    this.getAllMealType();
    this.getAllCuisine();
    this.getAllDietary();
  }

  errorMessage: string = '';
  recipeAdd: FormGroup;

  constructor(
    private adminService: AdminService,
    private foodService: FoodService,
    private fb: FormBuilder,
    private loggedIn: LoggedInServiceService,
    private discover: DiscoverService
  ) {
    this.recipeAdd = this.fb.group({
      name: ['', [Validators.required]],
      image: [null, [Validators.required]],
      description: ['', [Validators.required]],
      preptime: ['', [Validators.required]],
      instructions: ['', [Validators.required]],
      difficultyid: [null, [Validators.required]],
      mealtypeid: [null, [Validators.required]],
      cuisineid: [null, [Validators.required]],
      dietaryid: [null, [Validators.required]],
      ingredients: ['', [Validators.required]],
    });
  }

  onRecipeAdd() {
    this.addRecipe();
  }

  addRecipe(): void {
    let {
      name,
      image,
      userid,
      description,
      preptime,
      instructions,
      difficultyid,
      mealtypeid,
      cuisineid,
      dietaryid,
      ingredients,
    } = this.recipeAdd.value;

    console.log(this.recipeAdd.value);

    userid = this.loggedIn.whatUser();

    // Proceed to convert the image to Base64 if it is valid
    this.toBase64(image)
      .then((imageBase64) => {
        const data = {
          name: name, // The recipe's name
          image: imageBase64, // Image of the food
          description: description, // Recipe description
          preptime: preptime.toString(), // Recipe prep time
          userid: userid, // The user's id
          instructions: instructions?.replace(/\n/g, '§') || '', // Recipe instructions
          difficultyid: difficultyid, // Recipe difficulty ID
          mealtypeid: mealtypeid, // Recipe meal type ID
          cuisineid: cuisineid, // Recipe cuisine ID
          dietaryid: dietaryid,
          ingredients: ingredients, // Recipe ingredients
        };

        this.adminService.addRecipe(data).subscribe({
          next: () => {
            alert('Recipe successfully added!');
          },
          error: (error) => console.error('Recipe Adding Error: ', error),
        });
      })
      .catch((error) => {
        console.error('Base64 conversion error:', error);
      });
  }

  imagePreview: string | ArrayBuffer | null = '';

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.recipeAdd.patchValue({ image: file });
      this.recipeAdd.get('image')?.updateValueAndValidity();

      // Image preview (if you want to show the user a preview)
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  difficulties: { name: string, id: number }[] = [];
  mealTypes: { type: string, id:number }[] = [];
  cuisines: { name: string, id:number }[] = [];
  dietarys: { type: string, id:number }[] = [];

  getAllDifficulties(): void {
    this.discover.difficulty().subscribe({
      next: (response) => {
        for(let i=0; i<response.result.length; i++) {
          console.log(response.result)
          this.difficulties = response.result;
        }
      },
      error: (error) => console.error('Fetch error: ', error),
    });
  }

  getAllMealType(): void {
    this.discover.mealType().subscribe({
      next: (response) => {
        this.mealTypes = response.result
      },
      error: (error) => console.error('Fetch error: ', error)
    })
  }

  getAllCuisine():void {
    this.discover.cuisine().subscribe({
      next: (response) => {
        this.cuisines = response.result
      },
      error: (error) => console.error('Fetch error: ', error)
    })
  }

  getAllDietary(): void{
    this.discover.dietary().subscribe({
      next: (response) => {
        this.dietarys = response.result
      },
      error: (error) => console.error('Fetch error: ', error)
    })
  }

  toBase64(file: File | null): Promise<string> {
    return new Promise((resolve, reject) => {
      if (!file || !(file instanceof Blob)) {
        reject(new Error('Invalid file type'));
        return;
      }

      const reader = new FileReader();
      reader.onload = () => resolve(reader.result!.toString().split(',')[1]); // Extract Base64
      reader.onerror = (error) => reject(error);

      reader.readAsDataURL(file);
    });
  }
}
