import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { StorageService } from './storage.service';
import { PrimeNGConfig } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  constructor(
    private config: PrimeNGConfig,
    private translateService: TranslateService,
    private storageService: StorageService
  ) {

    this.setLanguage("en");
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

  public getMessage(key: string, params?: any) {
    let responseMessage = '';
    this.translateService.get(key, params).subscribe({
      next: (response) => {
        responseMessage = response;
      }
    });
    return responseMessage;
  }

  setPrimengLanguage() {
    this.translateService.get('primeng').subscribe((response) => {
      this.config.setTranslation(response);
      const acceptedLangs = this.translateService.getLangs();
      this.storageService.saveItem('ACCEPTED_LANGS', JSON.stringify(acceptedLangs));
    });
  }
}
