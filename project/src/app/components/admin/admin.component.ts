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
    NavbarComponent
  ]
})
export class AdminComponent {

  ngOnInit(){
    this.fetch()
  }

  yuh: IItem[] = []

  token = sessionStorage.getItem('auth_token')

  async fetch(){
    try {
      // Always retrieve the token when calling the fetch method
      const token = sessionStorage.getItem('auth_token');
      console.error("Token being used:", token);
      
      const response = await fetch("http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/getAllUser", {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': token ? `Bearer ${token}` : ''
        }
      });
      
      // Check the response's content type before parsing
      const contentType = response.headers.get('Content-Type');
      let personData;
      if (contentType && contentType.indexOf("application/json") !== -1) {
        personData = await response.json();
      } else {
        personData = await response.text();
      }
      
      console.log("Response from server:", personData);
      
      // Optionally, check if the server returned a "TokenExpired" message
      if (personData === "TokenExpired") {
        // Handle token expiration: redirect to login or refresh token logic
        console.error("The token is expired. Please log in again.");
      }
      
      return this.yuh = personData;
    } catch (exc) {
      console.error("Error during fetch:", exc);
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