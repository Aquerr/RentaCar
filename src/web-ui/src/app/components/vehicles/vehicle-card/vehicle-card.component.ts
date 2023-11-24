import { Component, Input } from '@angular/core';
import { EngineType, VehicleBasicData } from '../../../models/vehicle.model';

@Component({
  selector: 'vehicle-card',
  templateUrl: './vehicle-card.component.html',
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent {
  @Input() vehicle: VehicleBasicData | null = null;
  @Input() lang = 'us';

  constructor() {}

  protected readonly EngineType = EngineType;
}
