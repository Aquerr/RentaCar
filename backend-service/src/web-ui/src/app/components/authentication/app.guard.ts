import { Injectable } from '@angular/core';
import { TokenService } from '../../services/token.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { isEmpty } from 'lodash';

@Injectable()
export class AppGuard {
  constructor(private router: Router,
              private tokenService: TokenService,
              private authenticationService: AuthenticationService) {}

  isAuthenticated() {
    return this.tokenService.getToken() !== null;
  }

  hasUserAuthority(authorityName: string) {
    const authorities: string[] = this.authenticationService.getAuthorities() as string[];
    if (isEmpty(authorities)) {
      this.routeToMainPage();
      return false;
    }
    if (authorities.includes(authorityName)) {
      return true;
    }
    this.routeToMainPage();
    return false;
  }

  private routeToMainPage() {
    this.router.navigate(['']);
  }

}
