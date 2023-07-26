import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import {TokenService} from "../../services/token.service";
import {StorageService} from "../../services/storage.service";
@Injectable()
export class AppInterceptor implements HttpInterceptor {
  constructor(private storageService: StorageService, private tokenService: TokenService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler,
  ): Observable<HttpEvent<any>> {
    const jwt = this.tokenService.getToken();
    const lang = this.storageService.getItem('COOKIELANG');

    if (jwt) {
      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + jwt,
          Lang: lang ? lang : 'en'
        },
      });
    }
    return next.handle(request);
  }
}
