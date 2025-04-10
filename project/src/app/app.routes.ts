import { Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { DiscoverComponent } from './components/discover/discover.component';
import { RandomComponent } from './components/random/random.component';
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { AuthGuard } from './_guards/guard.guard';
import { RecipeDetailComponent } from './components/recipe-detail/recipe-detail.component';
import { RecipeResolver } from './_resolver/recipe.resolver';
import { RecipeComponent } from './components/recipe/recipe.component';
import { ErrorComponent } from './components/error/error.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'discover', component: DiscoverComponent },
  // Define the dynamic route
  { 
    path: 'discover/:recipeName', 
    component: RecipeDetailComponent, 
    resolve: { recipe: RecipeResolver } // Fetch before component loads
  },
  { 
    path: 'recipe/:recipeName', 
    component: RecipeDetailComponent, 
    resolve: { recipe: RecipeResolver } // Fetch before component loads
  },
  { path: 'random', component: RandomComponent },
  { path: 'user', component: UserComponent },
  { path: 'user#favorites', component: UserComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
  { path: 'recipeAdd', component: RecipeComponent},

  { path: '**', component: ErrorComponent }, //Make 404 page
];
