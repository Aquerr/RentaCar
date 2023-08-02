import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { TokenService } from '../../services/token.service';
import { StorageService } from '../../services/storage.service';

@Injectable()
export class AppInterceptor implements HttpInterceptor {
  constructor(private storageService: StorageService, private tokenService: TokenService) {}

  userLang = '';
  headerLanguages = '';

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const jwt = this.tokenService.getToken();
    this.userLang = this.getUserLang();
    this.headerLanguages = this.prepareHeaderLanguages();

    if (jwt) {
      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + jwt,
          'Accept-Language': this.headerLanguages
        }
      });
    } else {
      request = request.clone({
        setHeaders: {
          'Accept-Language': this.headerLanguages
        }
      });
    }
    return next.handle(request);
  }

  private getUserLang(): string {
    return this.storageService.getItem('COOKIELANG') as string;
  }

  private prepareHeaderLanguages(): string {
    let acceptedLanguages = JSON.parse(this.storageService.getItem('ACCEPTED_LANGS') || '[]');
    acceptedLanguages = acceptedLanguages.filter((lang: string) => this.userLang !== lang);
    acceptedLanguages.unshift(this.userLang);
    return acceptedLanguages.join(',');
  }
}
