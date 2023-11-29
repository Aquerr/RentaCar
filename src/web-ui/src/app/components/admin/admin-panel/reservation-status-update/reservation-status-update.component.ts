import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../../../services/common.service';
import { ToastType } from '../../../../services/toast.service';
import { ReservationApiService } from '../../../../services/api/reservation-api.service';
import { ProfileReservation, ReservationStatus } from '../../../../models/reservation.model.ts';
import { DictionaryEntry, DictionaryService, DictionaryType } from '../../../../services/dictionary.service';


@Component({
  selector: 'reservation-status-update',
  templateUrl: './reservation-status-update.component.html',
  styleUrls: ['./reservation-status-update.component.scss']
})
export class ReservationStatusUpdateComponent implements OnInit {
  reservations: ProfileReservation[] = [];
  dictionary: DictionaryEntry[] = [];

  constructor(private apiService: ReservationApiService,
              private commonService: CommonService,
              private dictionaryService: DictionaryService) {
  }

  ngOnInit() {
    this.getAllReservations();
    this.dictionary = this.dictionaryService.getDictionary(DictionaryType.RESERVATION_STATUS);
  }

  updateVehicleStatus(reservationId: number, status: ReservationStatus) {
    this.apiService.updateReservationStatus(reservationId, status).subscribe({
      next: () => {
        this.commonService.showToast('components.reservation-status-update.toasts.updateSuccess', ToastType.SUCCESS);
        this.getAllReservations();
      },
      error: () => this.commonService.showToast('components.reservation-status-update.toasts.updateRemove', ToastType.ERROR)
    })
  }

  getAllReservations() {
    this.apiService.getAllReservations().subscribe({
      next: (response) => this.reservations = response.reservations,
      error: () => this.reservations = []
    })
  }

}
