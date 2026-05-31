import {Component, input, InputSignal} from '@angular/core';
import { EngineType, VehicleBasicData } from '../../../models/vehicle.model';
import {TranslatePipe} from "@ngx-translate/core";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {UpperCasePipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {ButtonComponent} from "../../shared/button/button.component";

@Component({
  selector: 'vehicle-card',
  templateUrl: './vehicle-card.component.html',
  imports: [
    TranslatePipe,
    FaIconComponent,
    UpperCasePipe,
    RouterLink,
    ButtonComponent
  ],
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent {
  vehicle: InputSignal<VehicleBasicData> = input.required<VehicleBasicData>();
  lang: InputSignal<string> = input<string>('us');
  protected readonly EngineType = EngineType;
}
