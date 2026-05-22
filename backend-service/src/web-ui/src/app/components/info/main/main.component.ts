import { Component } from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";
import {RouterLink} from "@angular/router";
import {ButtonComponent} from "../../shared/button/button.component";


@Component({
  selector: 'main',
  templateUrl: './main.component.html',
  imports: [
    TranslatePipe,
    RouterLink,
    ButtonComponent
  ],
  styleUrls: ['./main.component.scss']
})
export class MainComponent {
}
