import { Component, EventEmitter, Output, Input } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import {
  EmailValidator,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { ɵASYNC_ANIMATION_LOADING_SCHEDULER_FN } from '@angular/platform-browser/animations/async';
import { LoginService } from '../../services/login.service';
import { HashService } from '../../services/hash.service';

@Component({
  selector: 'app-register',
  imports: [NavbarComponent, CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  ngOnInit() {
    this.loginServices.fetch;
  }
  showLogin = false; // To toggle between login and register views

  toggleAuth(authType: string) {
    this.showLogin = authType === 'login';

    setTimeout(() => {
      const slider = document.querySelector('.slider') as HTMLElement;
      const loginButton = document.getElementById('login');
      const registerButton = document.getElementById('register');

      if (slider && loginButton && registerButton) {
        const targetButton =
          authType === 'login' ? loginButton : registerButton;

        // Move the slider to the exact position of the selected button
        slider.style.transform = `translateX(${
          targetButton.offsetLeft - 10
        }px)`;
        slider.style.width = `${targetButton.offsetWidth}px`; // Adjust width dynamically
      }
    }, 50); // Small delay to ensure DOM updates
  }

  registerEmail: string = '';
  registerUsername: string = '';
  registerPassword: string = '';

  remember: boolean = false;
  acceptEULA: boolean = false;
  admin: boolean = false;

  loginEmailName: string = '';
  loginPassword: string = '';

  data: string = '';

  loginForm: FormGroup;
  registerForm: FormGroup;
  errorMessage: string = '';
  isLoading: boolean = false;
  showPassword: boolean = false;

  selectedFile: File | undefined;

  constructor(
    private router: Router,
    private loggedIn: LoggedInServiceService,
    private loginServices: LoginService,
    private hash: HashService,
    private fb: FormBuilder
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
    this.registerForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
      image: [''],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.errorMessage = '';

      const { username, password } = this.loginForm.value;
      console.log('Submitted Username & Password:', username, password);

      // Call fetch() before authentication
      this.loginServices
        .fetch(username, password)
        .then(() => {
          this.loginServices.authenticate(username, password).subscribe({
            next: () => {
              this.onLogin();
            },
            error: (error) => {
              console.error('Error in authenticate():', error);
              this.errorMessage = error.message;
              this.isLoading = false;
            },
            complete: () => {
              this.isLoading = false;
            },
          });
        })
        .catch((error) => {
          console.error('Error during fetch():', error);
        });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  async onLogin() {
    try {
      const { username, password } = this.loginForm.value;

      const loginData = {
        email: username,
        password: password,
      };

      await this.loginServices
        .loginUser(loginData)
        .then((response) => {
          console.log('Response:', response);
          if (response.result.isAdmin === true) {
            this.admin = true;
            this.loggedIn.login(
              response.result.jwt,
              response.result.id,
              response.result.isAdmin,
              this.remember
            );
            this.router.navigate(['/home']);
          } else {
            this.admin = false;
            this.loggedIn.login(
              response.result.jwt,
              response.result.id,
              response.result.isAdmin,
              this.remember
            );
            this.router.navigate(['/home']);
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    } catch (error) {
      console.error('Error in onLogin:', error);
    }
  }

  async onRegister() {
    try {
      const { username, email, password, image } = this.registerForm.value;
      const registerData = {
        username: username,
        email: email,
        password: password,
        image: image,
      };
      console.log(image);
      await this.loginServices
        .registerUser(registerData)
        .then((response) => {
          console.log('Response:', response);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    } catch (error) {
      console.error('evry big error');
    }
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
}