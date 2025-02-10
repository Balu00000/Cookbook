import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
	selector: 'app-user',
	standalone: true,
	imports: [NavbarComponent],
	templateUrl: './user.component.html',
	styleUrl: './user.component.css'
})
export class UserComponent {

	baseURL: string = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/getUserProfileInformation?"
	
	async fetching(){
		const userResult = await fetch(this.baseURL + "id=")
	}
}
