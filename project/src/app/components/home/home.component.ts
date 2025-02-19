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
} from '@coreui/angular';
import { RouterLink } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core'; // Import ChangeDetectorRef

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    NavbarComponent,
    CarouselIndicatorsComponent,
    CarouselCaptionComponent,
    RouterLink,
    CarouselComponent,
    CarouselInnerComponent,
    CarouselControlComponent,
    CarouselItemComponent,
    ThemeDirective,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  ratedFoodURL: string =
    'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/food/getFoodByRating';

  ngOnInit() {
    this.fetchFoodByRating();
  }

  ngAfterViewInit(){
    this.cdr.detectChanges();
  }

  constructor(private cdr: ChangeDetectorRef) {} // Inject ChangeDetectorRef

  isLoaded: boolean = false
  items: any[] = [];

  async fetchFoodByRating() {
    try {
      const ratedFoodResult = await fetch(this.ratedFoodURL);
      const ratedFoodData = await ratedFoodResult.json();
      this.items = ratedFoodData.result;
       this.isLoaded = true; // Mark as loaded
      this.cdr.detectChanges(); // Force update
      console.log(this.items);
    } catch (exc) {
      console.error('Failed to fetch: ', exc);
    }
  }

  trackByFn(index: number, item: any): number {
    return item.id; // Ensure trackBy uses a unique identifier
  }
}
