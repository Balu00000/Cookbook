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
  TemplateIdDirective,
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
  ],
})
export class AdminComponent {
  ngOnInit() {
    this.fetch();
  }

  yuh: IItem[] = [];

  token = sessionStorage.getItem('auth_token');

  decodedToken: any;

  async fetch() {
    try {
      const response = await fetch(
        'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/getAllUser'
      );
      const data = await response.json();
      console.log(data.result);
      return (this.usersData = data.result);
    } catch (exc) {
      console.error('fetch erro: ' + exc);
    }
  }

  usersData: IItem[] = [];

  columns: (IColumn | string)[] = [
    {
      key: 'id',
      label: 'id',
      _style: { width: '2%' },
    },
    {
      key: 'username',
      label: 'name',
      _style: { width: '40%' },
      _props: { color: 'danger', class: 'fw-bold' },
    },
    {
      key: 'email',
      label: 'Email',
      _style: { width: '30%' },
      _props: { color: 'pimary', class: 'fw-bold' },
    },
    {
      key: 'isAdmin',
      label: 'Admin',
      _style: { width: '15%' },
    },
    { 
      key: 'isDeleted',
      label: 'Deleted', 
      _style: { width: '7%' } 
    },
    {
      key: 'show',
      label: '',
      _style: { width: '5%' },
      filter: false,
      sorter: false,
    },
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
