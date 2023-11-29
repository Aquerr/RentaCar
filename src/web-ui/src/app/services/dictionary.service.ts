import { Injectable } from '@angular/core';
import { LanguageService } from './language.service';

@Injectable({
  providedIn: 'root'
})
export class DictionaryService {

  constructor(private languageService: LanguageService) {}

  getDictionary(type: DictionaryType): DictionaryEntry[] {
    const lang = this.languageService.getLanguage();
    switch (lang) {
      case 'pl':
        return this.getPlDictionary(type);
      case 'en':
        return this.getEnDictionary(type);
      default:
        return this.getEnDictionary(type);
    }
  }

  private getPlDictionary(type: DictionaryType): DictionaryEntry[] {
    switch (type) {
      case DictionaryType.RESERVATION_STATUS:
        return [
          { label: 'Szkic', value: 'DRAFT' },
          { label: 'Oczekiwanie na płatność', value: 'PENDING_PAYMENT' },
          { label: 'Płatność ukończona', value: 'PAYMENT_COMPLETED' },
          { label: 'Pojazd dostarczony', value: 'VEHICLE_DELIVERED' },
          { label: 'Ukończona', value: 'COMPLETED' },
          { label: 'Pojazd nie dostępny', value: 'VEHICLE_NOT_AVAILABLE' },
          { label: 'Rezygnacja', value: 'CANCELLED' }
        ];
      default:
        return [];
    }
  }

  private getEnDictionary(type: DictionaryType): DictionaryEntry[] {
    switch (type) {
      case DictionaryType.RESERVATION_STATUS:
        return [
          { label: 'Draft', value: 'DRAFT' },
          { label: 'Pending payment', value: 'PENDING_PAYMENT' },
          { label: 'Payment completed', value: 'PAYMENT_COMPLETED' },
          { label: 'Vehicle delivered', value: 'VEHICLE_DELIVERED' },
          { label: 'Completed', value: 'COMPLETED' },
          { label: 'Vehicle not available', value: 'VEHICLE_NOT_AVAILABLE' },
          { label: 'Cancelled', value: 'CANCELLED' }
        ];
      default:
        return [];
    }
  }
}

export interface DictionaryEntry {
  label: string;
  value: string;
}

export enum DictionaryType {
  RESERVATION_STATUS
}
