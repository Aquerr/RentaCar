import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProfileReservation, ReservationStatus } from '../../../models/reservation.model.ts';
import { DictionaryEntry } from '../../../services/dictionary.service';
import {TranslatePipe} from "@ngx-translate/core";
import {RouterLink} from "@angular/router";
import {ButtonComponent} from "../../shared/button/button.component";

@Component({
  selector: 'my-reservation-card',
  templateUrl: './my-reservation-card.component.html',
  imports: [
    TranslatePipe,
    RouterLink,
    ButtonComponent
  ],
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
