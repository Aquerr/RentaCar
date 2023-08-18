import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EngineType, VehicleBasicData } from '../../../models/vehicle.model';

@Component({
  selector: 'vehicle-card',
  templateUrl: './vehicle-card.component.html',
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent {
  @Input() vehicle: VehicleBasicData | null = null;
  @Input() lang = 'us';
  @Input() userId: number | undefined;
  @Output() reserveVehicleEvent = new EventEmitter<number>;

  constructor() {}

  reserveVehicle(vehicle: VehicleBasicData) {
    this.reserveVehicleEvent.emit(vehicle.id);
  }

  protected readonly EngineType = EngineType;
}
