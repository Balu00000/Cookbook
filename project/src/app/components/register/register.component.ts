import { Component, EventEmitter, Output, Input } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import {
  EmailValidator,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { LoggedInServiceService } from '../../services/logged-in-service.service';
import { LoginService } from '../../services/login.service';
import { HashService } from '../../services/hash.service';
import { HttpBackend } from '@angular/common/http';
import { PopoverModule } from '@coreui/angular-pro';

@Component({
  selector: 'app-register',
  imports: [NavbarComponent, CommonModule, ReactiveFormsModule, FormsModule, PopoverModule],
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
      username1: ['', [Validators.required]],
      email1: ['', [Validators.required]],
      password1: ['', [Validators.required]],
      image: [null, [Validators.required]],
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
            console.log(this.remember)
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

  changeRemember(): void{
    this.remember = !this.remember
    console.log(this.remember)
  }

  imagePreview: string | ArrayBuffer | null = '';

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.registerForm.patchValue({ image: file });
      this.registerForm.get('image')?.updateValueAndValidity();

      // Image preview (if you want to show the user a preview)
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  // Register User Method
  async onRegister() {
    if (this.registerForm.valid) {
      
      const { username1, email1, password1, image } = this.registerForm.value;
      const imageBase64 = await this.toBase64(image);
      const registerData = {
        username: username1,
        email: email1,
        password: password1,
        image: imageBase64 // send file as 'image'
      };

      // Send registration data to the service
      await this.loginServices.registerUser(registerData)
        .then((response) => {
          console.log('Registration successful:', response);
          this.router.navigate(['/home'])
        })
        .catch((error) => {
          console.error('Registration failed:', error);
        });
    }
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  toBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result!.toString().split(",")[1]); // Extract Base64
      reader.onerror = (error) => reject(error);
    });
  }
}
