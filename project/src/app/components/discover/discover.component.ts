import { Component, ElementRef, ViewChild, Renderer2, ChangeDetectorRef } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { GetAllFoodService } from '../../services/get-all-food.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-discover',
  standalone: true,
  imports: [NavbarComponent, CommonModule, FormsModule],
  templateUrl: './discover.component.html',
  styleUrl: './discover.component.css'
})
export class DiscoverComponent {

  isDropdownOpen: boolean = false;
  isDropdownOpen2: boolean = false;
  isDropdownOpen3: boolean = false;
  isDropdownOpen4: boolean = false;

  difficulties: { name: string }[] = []
  mealTypes: { type: string }[] = []
  cuisines: { type: string }[] = []
  dietarys: { type: string }[] = []
  
  allFood:any[] = []
  filteredFood: any[] = [];

  selectedDifficulties: { [key: string]: boolean } = {};
  selectedMealTypes: { [key: string]: boolean } = {};
  selectedCuisines: { [key: string]: boolean } = {};
  selectedDietarys: { [key: string]: boolean } = {};


  baseURL: string = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/"

  constructor(private foodFetch:GetAllFoodService){}

  async ngOnInit(){
    this.allFood = await this.foodFetch.LALALALALALAL();
    this.filteredFood = this.allFood;
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
    }
    this.applyFilters();
  }

  applyFilters() {
    this.filteredFood = this.allFood.filter(item =>
      (Object.keys(this.selectedDifficulties).length === 0 || this.selectedDifficulties[item.difficultyName] === true) &&
      (Object.keys(this.selectedMealTypes).length === 0 || this.selectedMealTypes[item.mealTypeName] === true) &&
      (Object.keys(this.selectedCuisines).length === 0 || this.selectedCuisines[item.cuisineName] === true) &&
      (Object.keys(this.selectedDietarys).length === 0 || this.selectedDietarys[item.dietaryType] === true)
    );
  }

  clearFilters() {
    // Reset the selected filters
    this.selectedDifficulties = {};
    this.selectedMealTypes = {};
    this.selectedCuisines = {};
    this.selectedDietarys = {};
    this.applyFilters()
  }

  get isFilterActive(): boolean {
    return Object.values(this.selectedDifficulties).some(value => value) || 
           Object.values(this.selectedMealTypes).some(value => value) || 
           Object.values(this.selectedCuisines).some(value => value) || 
           Object.values(this.selectedDietarys).some(value => value);
  }
  
  
  async difficulty() {
    try {
      const difficultyResponse = await fetch(this.baseURL + "difficulty/getAllDifficulty")
      const difficultyData = await difficultyResponse.json()
      if (!difficultyResponse.ok) {
        console.error('Failed to fetch difficulties.');
      }

      console.log(difficultyData);
      this.difficulties = difficultyData.result
      console.log(this.difficulties);

      this.isDropdownOpen = !this.isDropdownOpen; // Toggle visibility
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }

  async mealType() {
    try {
      const mealTypeResponse = await fetch(this.baseURL + "mealType/getAllMealType")
      const mealTypeData = await mealTypeResponse.json()
      if (!mealTypeResponse.ok) {
        console.error('Failed to fetch mealType.');
      }

      console.log(mealTypeData);
      this.mealTypes = mealTypeData.result
      console.log(this.mealTypes);

      this.isDropdownOpen2 = !this.isDropdownOpen2;
      
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }

  async cuisine() {
    try {
      const cuisineResponse = await fetch(this.baseURL + "cuisine/getAllCuisine")
      const cuisineData = await cuisineResponse.json()
      if (!cuisineResponse.ok) {
        console.error('Failed to fetch cuisine.');
      }

      console.log(cuisineData);
      this.cuisines = cuisineData.result
      console.log(this.cuisines);

      this.isDropdownOpen3 = !this.isDropdownOpen3;
      
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }

  async dietary() {
    try {
      const dietaryResponse = await fetch(this.baseURL + "dietary/getAllDietary")
      const dietaryData = await dietaryResponse.json()
      if (!dietaryResponse.ok) {
        console.error('Failed to fetch dietary.');
      }

      console.log(dietaryData);
      this.dietarys = dietaryData.result
      console.log(this.dietarys);

      this.isDropdownOpen4 = !this.isDropdownOpen4;
      
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }  
}