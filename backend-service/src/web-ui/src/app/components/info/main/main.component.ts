import { Component } from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";


@Component({
  selector: 'main',
  templateUrl: './main.component.html',
  imports: [
    TranslatePipe
  ],
  styleUrls: ['./main.component.scss']
})
export class MainComponent {
}
