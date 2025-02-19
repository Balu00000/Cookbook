import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { ResourceLoader } from '@angular/compiler';

@Component({
    selector: 'app-user',
    imports: [NavbarComponent],
    templateUrl: './user.component.html',
    styleUrl: './user.component.css'
})
export class UserComponent {

	constructor(private whatUser: LoggedInServiceService){}

	ngOnInit(){
		this.fetchingUser()
		this.fetchingUserAdded()
		this.fetchingUserFavorites()
	}

	user: any = {}
	userAdded: any = []
	userFavorites: any = []

	userURL: string = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/getUserProfileInformation?"
	addedURL: string = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/food/getFoodByUser?"
	favoritesURL: string = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/favourite/getFavouriteByUser?"
	
	async fetchingUser(){
		try {
			const userResult = await fetch(this.userURL + "id=" + this.whatUser.whatUser());
			const userData = await userResult.json();
			
			if (userData.result) {
			  this.user = userData.result; // Assign fetched data
			  console.log(this.user);
			  
			}
			
		  } catch (ex) {
			console.error("Fetch failed", ex);
		}
	}

	async fetchingUserAdded(){
		try{
			const addedResult = await fetch(this.addedURL + "id=" + this.whatUser.whatUser())
			const addedData = await addedResult.json()

			if(addedData.statusCode == 417){
				console.log("There are no Recipes added by this user");
			}

			if(addedData.result){
				this.userAdded = addedData.result
				console.log(this.userAdded);
			}
		}catch(ex){
			console.error("Fetch failed: ", ex);
			
		}
	}

	async fetchingUserFavorites(){
		try{
			const favoriteResult = await fetch(this.favoritesURL + "id=" + this.whatUser.whatUser())
			const favortieData = await favoriteResult.json()

			if(favortieData.result){
				this.userFavorites = favortieData.result
				console.log(this.userFavorites);
				
			}
		}catch(ex) 
		{
			console.error("failed to fetch" + ex);
		}
	}
}
