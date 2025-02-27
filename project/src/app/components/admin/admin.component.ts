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
  TemplateIdDirective
} from '@coreui/angular-pro';
import { NavbarComponent } from '../navbar/navbar.component';
import { InterpolationConfig } from '@angular/compiler';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
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
  ]
})
export class AdminComponent {

  ngOnInit(){
    this.fetch();
  }

  yuh: IItem[] = [];

  token = sessionStorage.getItem('auth_token');

  decodedToken: any;

  /* async fetch() {
    try {
      const token = sessionStorage.getItem('auth_token');
      console.log("Token being used:", token);

      const response = await fetch("http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/getAllUser", {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        credentials: 'include', // This is important if you're sending cookies or sessions
      });

      // Check if the response status is 401 or 400, and the response body is "TokenExpired"
      if (response.status === 401 || response.status === 400) {
        const responseText = await response.text();
        if (responseText.includes("TokenExpired")) {
          console.error("The token has expired. Please log in again.");
          return; // Exit early since the token is expired
        } else {
          console.error("Bad request:", responseText);
          return;
        }
      }

      // Now handle the JSON response if the status is not 400 or 401
      const contentType = response.headers.get('Content-Type');
      let personData;

      if (contentType && contentType.indexOf("application/json") !== -1) {
        personData = await response.json(); // Parse the JSON response
      } else {
        personData = await response.text(); // Handle any non-JSON response
      }

      console.log("Response from server:", personData);

      // Return the parsed data
      return this.yuh = personData;
    } catch (exc) {
      console.error("Error during fetch:", exc);
    }
  } */
    async fetch() {
      try {
        // const token = sessionStorage.getItem('auth_token');
        // console.log("Token being used:", token);
  
        const response = await fetch("http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/getAllUser")
        const data = await response.json()
        return this.yuh = data
        }catch(exc){
          console.error("fetch erro: " + exc);
        }
      }

    

  usersData: IItem[] = this.yuh;

  columns: (IColumn | string)[] = [
    {
      key: 'name',
      _style: { width: '40%' },
      _props: { color: 'danger', class: 'fw-bold' }
    },
    'registered',
    { key: 'role', filter: false, sorter: false, _style: { width: '15%' }, _classes: 'text-muted small' },
    { key: 'status', _style: { width: '15%' } },
    {
      key: 'show',
      label: '',
      _style: { width: '5%' },
      filter: false,
      sorter: false
    }
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
