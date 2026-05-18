import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ProfileReservation, ReservationStatus } from '../../../../../models/reservation.model.ts';
import { DictionaryEntry } from '../../../../../services/dictionary.service';


@Component({
  selector: 'reservation-status-update-card',
  templateUrl: './reservation-status-update-card.component.html',
  styleUrls: ['./reservation-status-update-card.component.scss']
})
export class ReservationStatusUpdateCardComponent implements OnInit {
  selectedStatus: ReservationStatus | null = null;

  @Input()
  reservation!: ProfileReservation;
  @Input()
  dictionary: DictionaryEntry[] = [];
  @Output()
  changeStatus: EventEmitter<ReservationStatus> = new EventEmitter<ReservationStatus>();

  ngOnInit() {
    this.selectedStatus = this.reservation.status;
  }

  changeStatusEmit() {
    this.changeStatus.emit(this.selectedStatus!);
  }

  getProperStatuses() {
    let dic: any[] = [];
    if (this.reservation.status === ReservationStatus.PENDING_PAYMENT) {
      dic = this.dictionary.filter((dic) => dic.value === ReservationStatus.PAYMENT_COMPLETED || dic.value === ReservationStatus.CANCELLED);
    } else if (this.reservation.status === ReservationStatus.PAYMENT_COMPLETED) {
      dic =  this.dictionary.filter((dic) => dic.value === ReservationStatus.CANCELLED || dic.value === ReservationStatus.VEHICLE_DELIVERED);
    } else if (this.reservation.status === ReservationStatus.VEHICLE_DELIVERED) {
      dic =  this.dictionary.filter((dic) => dic.value === ReservationStatus.CANCELLED || dic.value === ReservationStatus.COMPLETED);
    } else if (this.reservation.status === ReservationStatus.DRAFT) {
      dic =  this.dictionary.filter((dic) => dic.value === ReservationStatus.CANCELLED || dic.value === ReservationStatus.PENDING_PAYMENT);
    }
    const selectedDicOption = this.dictionary.find((dic) => dic.value === this.reservation.status)!;
    dic.push(selectedDicOption);
    return dic;
  }

  isNotAllowedChangeStatuses(): boolean {
    return [ReservationStatus.CANCELLED, ReservationStatus.COMPLETED].includes(this.reservation.status);
  }
}
