import { Component, OnDestroy, OnInit } from '@angular/core';
import { Reservation } from '../../models/reservation.model.ts';
import { Subscription } from 'rxjs';
import { ReservationService } from '../../services/reservation.service';
import { ReservationApiService } from '../../services/api/reservation-api.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit, OnDestroy {
  subscription: Subscription = new Subscription();
  reservation: Reservation | null = null;
  activeIndex: number = 0;
  steps = [
    { label: 'Dane pojazdu' },
    { label: 'Dane kontaktowe' },
    { label: 'Podsumowanie' }
  ];

  constructor(private activatedRoute: ActivatedRoute,
              private reservationApiService: ReservationApiService,
              private reservationService: ReservationService,
              private confirmationService: ConfirmationService) {}

  ngOnInit() {
    this.getReservation();
  }

  getReservation() {
    const reservationId = this.activatedRoute.snapshot.params['id'];
    this.subscription = this.reservationApiService.getReservation(reservationId).subscribe({
      next: (reservation) => {
        this.reservation = reservation.reservation;
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  reserveDialog() {
    this.confirmationService.confirm({
      message: 'Do you want to confirm reservation?',
      header: 'Confirmation',
      accept: () => this.reserve()
    });
  }

  reserve() {
    //TODO zebranie danych z child componentów, spakowanie rezerwacji, nadanie odpowiedniego statusu rezerwacji i procesowanie dalsze - generowanie linku do zaplaty i wysylka do klienta
    console.log('rezerwuje');
  }

  changeStep(event: number) {
    this.activeIndex = event;
  }

  goBack() {
    this.activeIndex = this.activeIndex - 1;
  }

  goNext() {
    this.activeIndex = this.activeIndex + 1;
  }
}
