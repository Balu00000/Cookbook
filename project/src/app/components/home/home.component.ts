import { Component, input } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import {
  CarouselCaptionComponent,
  CarouselComponent,
  CarouselControlComponent,
  CarouselIndicatorsComponent,
  CarouselInnerComponent,
  CarouselItemComponent,
  ThemeDirective,
  ButtonDirective,
} from '@coreui/angular-pro';
import { Router, RouterLink } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core'; // Import ChangeDetectorRef
import { TruncatePipe } from '../../_pipes/truncate.pipe';
import { HttpClient } from '@angular/common/http';
import { FoodService } from '../../services/food.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    NavbarComponent,
    CarouselIndicatorsComponent,
    RouterLink,
    CarouselComponent,
    CarouselInnerComponent,
    CarouselControlComponent,
    CarouselItemComponent,
    ThemeDirective,
    TruncatePipe,
    ButtonDirective,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  ngOnInit() {
    this.fetchAddedAt();
    this.fetchRatedFood();
    this.getRecipeOfTheDay();
  }

  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  constructor(
    private cdr: ChangeDetectorRef,
    private router: Router,
    private http: HttpClient,
    private foodService: FoodService
  ) {} // Inject ChangeDetectorRef

  isLoaded: boolean = false;
  foodItems: any[] = [];
  foodAddedAt: any[] = [];

  fetchRatedFood(): void {
    this.foodService.getRatedFood().subscribe({
      next: (response) => {
        this.foodItems = response.result;
        this.checkIfDataLoaded();
      },
      error: (error) => console.error('Fetch error: ', error),
    });
  }

  fetchAddedAt(): void {
    this.foodService.getFoodByAdded().subscribe({
      next: (response) => {
        this.foodAddedAt = response.result;
        this.checkIfDataLoaded();
      },
      error: (error) => console.error('Fetch error: ', error),
    });
  }

  recipeOfTheDay: any = null;

  getRecipeOfTheDay(): void {
    const stored = localStorage.getItem('recipeOfTheDay');
    const today = new Date().toDateString(); // eg. "Sat Apr 05 2025"

    console.log('Fasz1');
    if (stored) {
      const parsed = JSON.parse(stored);
      console.log('Fasz2');
      if (parsed.date === today) {
        console.log('Fasz3');
        this.cdr.detectChanges();
        return this.recipeOfTheDay = parsed.recipe;
      }
    }
    
    // If not stored or outdated, fetch new one
    this.foodService.randomRecipe().subscribe({
      next: (response) => {
        this.recipeOfTheDay = response.result;
        localStorage.setItem(
          'recipeOfTheDay',
          JSON.stringify({
            date: today,
            recipe: response.result,
          })
        );
        this.cdr.detectChanges();
      },
      error: (error) =>
        console.error('Error fetching recipe of the day: ', error),
    });
  }

  checkIfDataLoaded(): void {
    if (this.foodItems.length > 0 && this.foodAddedAt.length > 0) {
      this.isLoaded = true;
      this.cdr.detectChanges();
    }
  }

  trackByFn(index: number, item: any): number {
    return item.id; // Ensure trackBy uses a unique identifier
  }

  viewRecipe(recipeName: string): void {
    const slug = recipeName
      .toLowerCase()
      .replace(/\s+/g, '-')
      .replace(/[^\w-]/g, '');
    this.router.navigate(['/recipe', slug]); // No ID, only the name
  }
}
