import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  constructor(
    private translateService: TranslateService,
    private cookieService: CookieService,
  ) {}

  loadLanguage(user: User | undefined) {
    if (user) {
      this.setLanguage(user.lang);
    } else {
      const cookieLang = this.cookieService.get('COOKIELANG') as string;
      if (cookieLang) {
        this.setLanguage(cookieLang);
      }
    }
  }

  setLanguage(lang: string) {
    this.translateService.use(lang);
  }
}
