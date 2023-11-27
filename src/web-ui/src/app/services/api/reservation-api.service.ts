import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app.consts';
import { HttpClient } from '@angular/common/http';
import { ProfileReservation, Reservation, ReservationStatus } from '../../models/reservation.model.ts';

@Injectable({
  providedIn: 'root'
})
export class ReservationApiService {
  private URL = APP_BASE_URL + APP_V1_URL + '/reservations';

  constructor(private http: HttpClient) {
  }

  public createNewReservation(request: Reservation) {
    return this.http.post<ReservationResponse>(this.URL, request);
  }

  public updateReservation(request: Reservation) {
    return this.http.put<ReservationResponse>(this.URL, request);
  }

  public updateReservationStatus(reservationId: number, status: ReservationStatus) {
    return this.http.patch<void>(`${this.URL}/${reservationId}/status/${status}`, {});
  }

  public getReservation(reservationId: number) {
    return this.http.get<ReservationResponse>(`${this.URL}/${reservationId}`);
  }

  public getProfileReservations() {
    return this.http.get<ReservationsResponse>(`${this.URL}/my-self`);
  }

}

export interface ReservationResponse {
  reservation: Reservation;
}

export interface ReservationsResponse {
  reservations: ProfileReservation[];
}
