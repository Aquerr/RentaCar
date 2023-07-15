import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import {CookieService} from "ngx-cookie-service";
@Injectable()
export class AppInterceptor implements HttpInterceptor {

  constructor(private cookieService: CookieService) { }

  intercept(request: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {
    const jwt = this.cookieService.get("jwt");

    if (!!jwt) {
      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + jwt
        }
      });
    }
    return next.handle(request);
  }
}
