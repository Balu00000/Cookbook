import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-navbar',
    imports: [FormsModule],
    templateUrl: './navbar.component.html',
    styleUrl: './navbar.component.css'
})
export class NavbarComponent {
	search:string = ""

	constructor(private authService: LoggedInServiceService, private router: Router) {}

	// This method will be called when the profile button is clicked
	onProfileClick(): void {
		if (this.authService.isAuthenticated()) {
			// If the user is authenticated, navigate to the profile page
			this.router.navigate(['/user']);
		} else {
			// If not authenticated, navigate to the login page
			this.router.navigate(['/register']);
		}
	}
	
	onFavoriteClick(): void {
		if (this.authService.isAuthenticated()) {
			// If the user is authenticated, navigate to the profile page
			this.router.navigate(['/user']);
		} else {
			// If not authenticated, navigate to the login page
			this.router.navigate(['/register']);
		}
	}

	onAdminClick(): void{
		this.router.navigate(['/admin'])
	}

	// Optional: To handle auto-login or checking status on page load
	ngOnInit() {
		if (this.authService.isAuthenticated()) {
			// The user is logged in, perform any necessary actions
			// You could set a flag or make a call to fetch user details
		}
	}

	logout(){
		this.authService.logout()
		this.router.navigate(['/home'])
	}
}
