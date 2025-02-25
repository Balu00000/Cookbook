import { Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { DiscoverComponent } from './components/discover/discover.component';
import { RandomComponent } from './components/random/random.component';
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component'

export const routes: Routes = [
    {path: "", redirectTo:"/home", pathMatch: 'full'},
    {path: "home", component: HomeComponent},
    {path: "register", component: RegisterComponent},
    {path: "discover", component: DiscoverComponent},
    {path: "random", component:RandomComponent},
    {path: "user", component:UserComponent},
    {path: "user#favorites", component:UserComponent},
    {path: "admin", component:AdminComponent}
];
