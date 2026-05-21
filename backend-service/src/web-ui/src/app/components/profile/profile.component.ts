import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {TranslatePipe} from "@ngx-translate/core";

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  imports: [
    RouterLinkActive,
    RouterLink,
    TranslatePipe,
    RouterOutlet
  ],
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

}
