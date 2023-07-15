import {CookieService} from "ngx-cookie-service";
import {Injectable} from "@angular/core";

@Injectable()
export class AnonymousGuard {
  constructor(private cookieService: CookieService) {
  }

  canActivate() {
    const jwt = this.cookieService.get('jwt') as string;
    return !jwt;
  }
}
