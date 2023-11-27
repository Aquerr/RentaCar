import { Component, OnInit } from '@angular/core';
import { ReservationApiService } from '../../services/api/reservation-api.service';
import { ProfileReservation, ReservationStatus } from '../../models/reservation.model.ts';
import { DictionaryEntry, DictionaryService, DictionaryType } from '../../services/dictionary.service';
import { ToastType } from '../../services/toast.service';
import { ConfirmationService } from 'primeng/api';
import { CommonService } from '../../services/common.service';

@Component({
  selector: 'my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.scss']
})
export class MyReservationsComponent implements OnInit {
  reservations: ProfileReservation[] = [];
  dictionary: DictionaryEntry[] = [];

  constructor(private apiService: ReservationApiService,
              private dictionaryService: DictionaryService,
              private confirmationService: ConfirmationService,
              private commonService: CommonService) {}

  ngOnInit() {
    this.getProfileReservations();
    this.getDictionary();
  }

  getProfileReservations() {
    this.apiService.getProfileReservations().subscribe({
      next: (reservationsResponse) => this.reservations = reservationsResponse.reservations,
      error: () => this.reservations = []
    });
  }

  cancelConfirmDialog(reservation: ProfileReservation) {
    this.confirmationService.confirm({
      key: 'cancel',
      accept: () => this.cancel(reservation)
    });
  }

  cancel(reservation: ProfileReservation) {
    this.apiService.updateReservationStatus(reservation.id, ReservationStatus.CANCELLED).subscribe({
      next: () => {
        this.commonService.showToast('components.my-reservations.toasts.reservationCancelled', ToastType.SUCCESS);
        this.getProfileReservations();
      },
      error: () => this.commonService.showToast('components.my-reservations.toasts.reservationCancelledError', ToastType.ERROR)
    });
  }
  getDictionary() {
    this.dictionary = this.dictionaryService.getDictionary(DictionaryType.RESERVATION_STATUS);
  }

}
