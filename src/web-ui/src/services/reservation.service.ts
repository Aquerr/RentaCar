import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Reservation } from '../models/reservation.model.ts';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private reservation: Subject<Reservation | null> = new Subject<Reservation | null>();
  reservationObservable$ = this.reservation.asObservable();

  updateReservation(reservation: Reservation | null) {
    this.reservation.next(reservation);
  }

}
