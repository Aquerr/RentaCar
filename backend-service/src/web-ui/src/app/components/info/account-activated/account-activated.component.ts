import { Component } from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";
import {RouterLink} from "@angular/router";


@Component({
  selector: 'account-activated',
  templateUrl: './account-activated.component.html',
  imports: [
    TranslatePipe,
    RouterLink
  ],
  styleUrls: ['./account-activated.component.scss']
})
export class AccountActivatedComponent {
}
