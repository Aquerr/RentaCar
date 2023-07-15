import {Injectable} from '@angular/core';
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class LoginGuard {
  constructor(private cookieService: CookieService) {
  }

  canActivate() {
    const jwt = this.cookieService.get('jwt') as string;
    return !!jwt;
  }
}
