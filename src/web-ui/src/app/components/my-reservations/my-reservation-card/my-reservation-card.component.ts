import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProfileReservation, ReservationStatus } from '../../../models/reservation.model.ts';
import { DictionaryEntry } from '../../../services/dictionary.service';

@Component({
  selector: 'my-reservation-card',
  templateUrl: './my-reservation-card.component.html',
  styleUrls: ['./my-reservation-card.component.scss']
})
export class MyReservationCardComponent {
  @Input()
  reservation!: ProfileReservation;
  @Input()
  dictionary!: DictionaryEntry[];
  @Output()
  cancelEmitter: EventEmitter<ProfileReservation> = new EventEmitter<ProfileReservation>();

  getReservationStatusTranslation(status: ReservationStatus) {
    const dic = this.dictionary.find((dic) => dic.value === status)! ?? '';
    return dic?.label;
  }

  cancel() {
    this.cancelEmitter.emit(this.reservation);
  }

}
