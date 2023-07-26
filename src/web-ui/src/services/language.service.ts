import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StorageService } from './storage.service';
import { PrimeNGConfig } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  constructor(
    private config: PrimeNGConfig,
    private translateService: TranslateService,
    private storageService: StorageService,
  ) {
  }

  loadLanguage() {
    const cookieLang = this.storageService.getItem('COOKIELANG');
    if (cookieLang) {
      this.setLanguage(cookieLang);
    }
  }

  getLanguage() {
    return this.translateService.currentLang;
  }

  setLanguage(lang: string) {
    this.translateService.use(lang);
    this.storageService.saveItem('COOKIELANG', lang);
    this.setPrimengLanguage();
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

  setPrimengLanguage() {
    this.translateService.get('primeng').subscribe((response) => this.config.setTranslation(response));
  }
}
