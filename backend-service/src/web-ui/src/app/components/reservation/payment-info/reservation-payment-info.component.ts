import { Component } from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";

@Component({
  selector: 'reservation-payment-info',
  templateUrl: './reservation-payment-info.component.html',
  imports: [
    TranslatePipe
  ],
  styleUrls: ['./reservation-payment-info.component.scss']
})
export class ReservationPaymentInfoComponent {
}
