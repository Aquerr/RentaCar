import { Component } from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";


@Component({
  selector: 'account-activated',
  templateUrl: './account-activated.component.html',
  imports: [
    TranslatePipe
  ],
  styleUrls: ['./account-activated.component.scss']
})
export class AccountActivatedComponent {
}
