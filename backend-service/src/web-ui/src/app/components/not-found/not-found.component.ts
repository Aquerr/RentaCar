import { Component } from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";

@Component({
  selector: 'not-found',
  templateUrl: './not-found.component.html',
  imports: [
    TranslatePipe
  ],
  styleUrls: ['./not-found.component.scss']
})
export class NotFoundComponent {}
