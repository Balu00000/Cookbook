import { Component, EventEmitter, Output, Input } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginServiceService } from '../../services/login-service.service';
import { RegisterServiceService } from '../../services/register-service.service';
import { Router } from '@angular/router';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { ɵASYNC_ANIMATION_LOADING_SCHEDULER_FN } from '@angular/platform-browser/animations/async';

@Component({
    selector: 'app-register',
    imports: [NavbarComponent, CommonModule, FormsModule],
    templateUrl: './register.component.html',
    styleUrl: './register.component.css'
})
export class RegisterComponent {
  showLogin = false; // To toggle between login and register views

  toggleAuth(type: string) {
    this.showLogin = type === 'login';
  }

  registerEmail: string = "";
  registerUsername: string = "";
  registerPassword: string = "";

  remember: boolean = false;  
  acceptEULA: boolean = false;
  admin: boolean = false;

  loginEmailName: string = "";
  loginPassword: string = "";

  data: string = ""

  constructor(private loginService: LoginServiceService,
              private registerService: RegisterServiceService,
              private router: Router,
              private loggedIn: LoggedInServiceService) { }

  async onLogin() {
    try {
      const loginData = {
        email: this.loginEmailName,
        password: this.loginPassword
      }
      await this.loginService
        .loginUser(loginData)
        .then((response) => {
          console.log('Response:', response);
          if (response.result.isAdmin === true) {
            console.log("hes adming");
            this.admin = true
            this.loggedIn.login(response.result.jwt, response.result.id, this.remember);
            this.router.navigate(['/home']);
          } else {
            console.log("hes not adming");
            this.admin = false
            this.loggedIn.login(response.result.jwt, response.result.id, this.remember);
            this.router.navigate(['/home'])
          }

        })
        .catch((error) => {
          console.error('Error:', error);
        });
    } catch (error) { }
  }

  async onRegister() {
    try {
      const registerData = {
        username: this.registerUsername,
        email: this.registerEmail,
        password: this.registerPassword,
        image: ""
      }
      await this.registerService
        .registerUser(registerData)
        .then((response) => {
          console.log("Response:", response);
        })
        .catch((error) => {
          console.error("Error:", error);
        })
    } catch (error) {
      console.error("evry big error");

    }
  }
}