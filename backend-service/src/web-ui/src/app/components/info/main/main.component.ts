import { Component } from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";
import {RouterLink} from "@angular/router";


@Component({
  selector: 'main',
  templateUrl: './main.component.html',
  imports: [
    TranslatePipe,
    RouterLink
  ],
  styleUrls: ['./main.component.scss']
})
export class MainComponent {
}
