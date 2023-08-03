import { Component, OnDestroy, OnInit } from '@angular/core';
import { Reservation } from '../../models/reservation.model.ts';
import { Subscription } from 'rxjs';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit, OnDestroy {
  subscription: Subscription = new Subscription();
  reservation: Reservation | null = null;

  constructor(private reservationService: ReservationService) {}

  ngOnInit() {
    this.getReservation();
  }

  getReservation() {
    this.subscription = this.reservationService.reservationObservable$.subscribe({
      next: (reservation) => {
        this.reservation = reservation;
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
