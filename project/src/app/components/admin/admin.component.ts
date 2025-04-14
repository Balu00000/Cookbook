import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import {
  AlignDirective,
  BadgeComponent,
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardFooterComponent,
  ColDirective,
  CollapseDirective,
  IColumn,
  IItem,
  SmartTableComponent,
  TableActiveDirective,
  TableColorDirective,
  TemplateIdDirective,
} from '@coreui/angular-pro';
import { NavbarComponent } from '../navbar/navbar.component';
import { AdminService } from '../../services/admin.service';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { FoodService } from '../../services/food.service';
import { HashService } from '../../services/hash.service';
import { CommonModule } from '@angular/common';
import { PopoverModule } from '@coreui/angular-pro';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { DiscoverService } from '../../services/discover.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css',

  standalone: true,
  imports: [
    AlignDirective,
    BadgeComponent,
    ButtonDirective,
    CardBodyComponent,
    CardComponent,
    CardFooterComponent,
    ColDirective,
    CollapseDirective,
    NgClass,
    SmartTableComponent,
    TableActiveDirective,
    TableColorDirective,
    TemplateIdDirective,
    NavbarComponent,
    ReactiveFormsModule,
    CommonModule,
    PopoverModule,
  ],
  providers: [AdminService],
})
export class AdminComponent {
  ngOnInit() {
    this.fetchUsers();
    this.fetchRecipes();
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

  button1: boolean = false;
  button3: boolean = true;

  buttonActive(button: 'user' | 'recipeMod') {
    const buttonState = {
      user: [true, false],
      recipeMod: [false, true],
    };

    [this.button1, this.button3] = buttonState[
      button
    ] || [false, true];
    console.log(button);
  }

  onRecipeAdd() {
    this.addRecipe();
  }

  fetchUsers(): void {
    this.adminService.getAllUsers().subscribe({
      next: (response) => {
        this.usersData = response.result;
      },
      error: (error) => console.error('Fetch error:', error),
    });
  }

  fetchRecipes(): void {
    this.foodService.getAllRecipes().subscribe({
      next: (response) => {
        console.log('Recipes Data:', response.result); // Debugging
        this.recipeData = response.result;
      },
      error: (error) => console.error('Fetch error:', error),
    });
  }

  // Delete user and refresh list
  deleteUser(id: number, isDeleted: boolean): void {
    if (isDeleted == undefined) {
      if (confirm('Are you sure you want to delete this user?')) {
        this.adminService.deleteUser(id).subscribe({
          next: () => {
            alert('User deleted successfully!');
            this.fetchUsers(); // Refresh the user list
          },
          error: (error) => console.error('Error deleting user:', error),
        });
      }
    }
  }

  deleteRecipe(id: number, isDeleted: boolean): void {
    if (isDeleted == undefined) {
      if (confirm('Are you sure you want to delete this Recipe?')) {
        this.adminService.deleteRecipe(id).subscribe({
          next: () => {
            alert('Recipe deleted successfully!');
            this.fetchRecipes(); // Refresh the user list
          },
          error: (error) => console.error('Error recipe:', error),
        });
      }
    }
  }

  // username: string;
  //   image: string;
  //   description: string;
  //   preptime: number;
  //   userId: number;
  //   instructions: string;
  //   difficultyId: number;
  //   mealTypeId: number;
  //   cuisineId: number;
  //   ingredients: string;

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

  adminify() {}

  usersData: IItem[] = [];
  recipeData: IItem[] = [];

  columns: (IColumn | string)[] = [
    {
      key: 'id',
      label: 'id',
      _style: { width: '2%' },
    },
    {
      key: 'username',
      label: 'name',
      _style: { width: '40%' },
      _props: { color: 'danger', class: 'fw-bold' },
    },
    {
      key: 'email',
      label: 'Email',
      _style: { width: '30%' },
      _props: { color: 'pimary', class: 'fw-bold' },
    },
    {
      key: 'isAdmin',
      label: 'Admin',
      _style: { width: '15%' },
    },
    {
      key: 'isDeleted',
      label: 'Deleted',
      _style: { width: '7%' },
    },
    {
      key: 'show',
      label: '',
      _style: { width: '5%' },
      filter: false,
      sorter: false,
    },
  ];

  columns1: (IColumn | string)[] = [
    {
      key: 'id',
      label: 'id',
      _style: { width: '2%' },
    },
    {
      key: 'name',
      label: 'Recipe Name',
      _style: { width: '5%' },
      _props: { color: 'danger', class: 'fw-bold' },
    },
    {
      key: 'description',
      label: 'Description',
      _style: { width: '30%' },
      _props: { color: 'pimary', class: 'fw-bold' },
    },
    {
      key: 'cuisineName',
      label: 'Cuisine',
      _style: { width: '5%' },
    },
    {
      key: 'rating',
      label: 'Rating',
      _style: { width: '2%' },
    },
    {
      key: 'difficultyName',
      label: 'Difficulty',
      _style: { width: '5%' },
    },
    {
      key: 'mealTypeName',
      label: 'Meal Type',
      _style: { width: '7%' },
    },
    {
      key: 'prepTime',
      label: 'Time (Minutes)',
      _style: { width: '3%' },
    },
    {
      key: 'username',
      label: 'username',
      _style: { width: '10%' },
    },
    {
      key: 'show',
      label: '',
      _style: { width: '5%' },
      filter: false,
      sorter: false,
    },
  ];

  getBadge(status: string) {
    switch (status) {
      case 'Active':
        return 'success';
      case 'Inactive':
        return 'secondary';
      case 'Pending':
        return 'warning';
      case 'Banned':
        return 'danger';
      default:
        return 'primary';
    }
  }

  getItem(item: any) {
    return Object.keys(item);
  }

  details_visible = Object.create({});

  toggleDetails(item: any) {
    this.details_visible[item] = !this.details_visible[item];
  }
}
