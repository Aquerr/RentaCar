import { Component } from '@angular/core';
import {AdminSideMenuComponent} from "./side-menu/admin-side-menu.component";
import {RouterOutlet} from "@angular/router";


@Component({
  selector: 'admin-panel',
  templateUrl: './admin-panel.component.html',
  imports: [
    AdminSideMenuComponent,
    RouterOutlet
  ],
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent {
}
