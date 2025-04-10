import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { FoodService } from '../services/food.service';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RecipeResolver implements Resolve<any> {
  constructor(private foodService: FoodService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    const recipeName = route.paramMap.get('recipeName');
  
    return this.foodService.getAllRecipes().pipe(
      map((response) => {
        console.log("API Response:", response); // Log the API response to verify its structure
  
        // Check if the response contains a 'recipes' property
        if (response && Array.isArray(response.result   )) {
          const recipe = response.result.find((r: any) => this.slugify(r.name) === recipeName);
          if (recipe) {
            return recipe; // Return the found recipe
          } else {
            console.error("Recipe not found");
            this.router.navigate(['/discover']); // Redirect if recipe not found
            return null;
          }
        } else {
          console.error("Expected an object with 'recipes' property, but got:", response);
          return null; // Return null if the response is not in the expected format
        }
      }),
      catchError((error) => {
        console.error("Error during fetching", error);
        this.router.navigate(['/discover']); // Redirect if there's an error
        return of(null);
      })
    );
  }

  private slugify(name: string): string {
    return name.toLowerCase().replace(/\s+/g, '-').replace(/[^\w-]/g, '');
  }
}
