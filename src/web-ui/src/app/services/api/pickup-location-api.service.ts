import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app.consts';
import { HttpClient } from '@angular/common/http';
import { VehiclePickupLocation } from '../../models/vehicle-pickup-location.model';
import { LanguageService } from '../language.service';

@Injectable({
  providedIn: 'root'
})
export class PickupLocationApiService {
  private URL = APP_BASE_URL + APP_V1_URL + '/pickup-locations';

  constructor(private http: HttpClient,
              private languageService: LanguageService) {
  }

  public getPickupLocations() {
    const lang = this.languageService.getLanguage();
    return this.http.get<VehiclePickupResponse>(`${this.URL}?lang=${lang}`);
  }

}

export interface VehiclePickupResponse {
  locations: VehiclePickupLocation[];
}
