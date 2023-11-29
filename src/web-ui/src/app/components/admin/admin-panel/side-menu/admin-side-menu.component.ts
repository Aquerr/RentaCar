import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../../services/authentication.service';


@Component({
  selector: 'admin-side-menu',
  templateUrl: './admin-side-menu.component.html',
  styleUrls: ['./admin-side-menu.component.scss']
})
export class AdminSideMenuComponent implements OnInit {
  authorities: string[] = [];

  constructor(private authService: AuthenticationService) {}

  ngOnInit() {
    this.authorities = this.authService.getAuthorities();
  }

  hasUserAuthority(authorityName: string): boolean {
    return this.authorities.includes(authorityName);
  }
}
