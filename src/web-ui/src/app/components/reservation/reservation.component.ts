import { Component, OnDestroy, OnInit } from '@angular/core';
import { Reservation, ReservationStatus } from '../../models/reservation.model.ts';
import { Subscription } from 'rxjs';
import { ReservationApiService } from '../../services/api/reservation-api.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { CommonService } from '../../services/common.service';
import { ToastType } from '../../services/toast.service';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit, OnDestroy {
  subscription: Subscription = new Subscription();
  reservation!: Reservation;
  activeIndex: number = 0;
  steps = this.prepareSteps();
  readOnly = false;

  constructor(private activatedRoute: ActivatedRoute,
              private reservationApiService: ReservationApiService,
              private confirmationService: ConfirmationService,
              private commonService: CommonService,
              private languageService: LanguageService) {}

  ngOnInit() {
    this.getReservation();
  }

  getReservation() {
    const reservationId = this.activatedRoute.snapshot.params['id'];
    this.subscription = this.reservationApiService.getReservation(reservationId).subscribe({
      next: (reservation) => {
        this.reservation = reservation.reservation;
        if (this.reservation.status !== ReservationStatus.DRAFT) {
          this.readOnly = true;
          this.commonService.showToast('components.reservation.toasts.reservationReadOnly', ToastType.WARN);
        }
      },
      error: () => {
        this.commonService.showToast('components.reservation.toasts.reservationNotFound', ToastType.ERROR);
        this.commonService.goRoute('');
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  reserveConfirmDialog() {
    this.confirmationService.confirm({
      accept: () => this.reserve()
    });
  }

  reserve() {
    this.reservation.status = ReservationStatus.PENDING_PAYMENT;
    this.reservationApiService.updateReservation(this.reservation).subscribe({
      next: (reservationResponse) => {
        if (reservationResponse.reservation.status === ReservationStatus.VEHICLE_NOT_AVAILABLE) {
          this.commonService.showToast('components.reservation.toasts.reservationVehicleNotAvailable', ToastType.SUCCESS);
          this.commonService.goRoute('');
        } else {
          this.commonService.showToast('components.reservation.toasts.reservationSuccess', ToastType.SUCCESS);
          this.commonService.goRoute('reservation-payment-info');
        }
      },
      error: () => this.commonService.showToast('components.reservation.toasts.reservationError', ToastType.ERROR)
    });
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

  private prepareSteps() {
    if (this.languageService.getLanguage() === 'pl') {
      return [
        { label: 'Dane pojazdu' },
        { label: 'Dane kontaktowe' },
        { label: 'Podsumowanie' }
      ];
    }
    return [
      { label: 'Vehicle data' },
      { label: 'Contact data' },
      { label: 'Summary' }
    ];
  }
}
