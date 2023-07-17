import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { User } from '../models/user.model';
import {StorageService} from "./storage.service";

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  constructor(
    private translateService: TranslateService,
    private storageService: StorageService
  ) {}

  loadLanguage(user: User | undefined) {
    if (user) {
      this.setLanguage(user.lang);
    } else {
      const cookieLang = this.storageService.getItem('COOKIELANG');
      if (cookieLang) {
        this.setLanguage(cookieLang);
      }
    }
  }

  getLanguage() {
    return this.translateService.currentLang;
  }

  setLanguage(lang: string) {
    this.translateService.use(lang);
    this.storageService.saveItem('COOKIELANG', lang);
  }

  public getMessage(key: string) {
    let responseMessage = '';
    this.translateService.get(key).subscribe({
      next: (response) => {
        responseMessage = response;
      },
    });
    return responseMessage;
  }
}
