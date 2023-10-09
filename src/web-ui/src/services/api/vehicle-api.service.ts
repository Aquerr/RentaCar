import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app/app.consts';
import { HttpClient, HttpParams } from '@angular/common/http';
import { VehicleBasicData, VehicleFullData } from '../../models/vehicle.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleApiService {
  private URL = APP_BASE_URL + APP_V1_URL + '/vehicles';

  constructor(private http: HttpClient) {
  }

  public getVehicleFullData(vehicleId: number) {
    return this.http.get<VehicleFullDataResponse>(`${this.URL}/full-data/${vehicleId}`);
  }

  public isVehicleAvailable(vehicleId: number, dateFrom: string, dateTo: string) {
    const httpParams = new HttpParams()
    .set('dateFrom', dateFrom)
    .set('dateTo', dateTo);
    return this.http.get<boolean>(`${this.URL}/${vehicleId}/available`, { params: httpParams });
  }

  public getVehiclesAvailable(dateFrom: string, dateTo: string) {
    const httpParams = new HttpParams()
    .set('dateFrom', dateFrom)
    .set('dateTo', dateTo);
    return this.http.get<VehicleBasicDataResponse>(`${this.URL}/available`, { params: httpParams });
  }

  public saveVehicle(vehicle: VehicleFullData, images: File[]) {
    const formData = new FormData();
    images.forEach(image => formData.append('images', image));
    formData.append('vehicle', new Blob([JSON.stringify(vehicle)], { type: 'application/json' }));
    return this.http.post<VehicleFullDataResponse>(this.URL, formData);
  }

}

export interface VehicleFullDataResponse {
  vehicle: VehicleFullData;
}

export interface VehicleBasicDataResponse {
  vehicles: VehicleBasicData[];
}
