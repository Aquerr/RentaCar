import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EngineType, Vehicle } from '../../../models/vehicle.model';
import { DialogService } from 'primeng/dynamicdialog';
import { VehicleDetailsComponent } from '../vehicle-details/vehicle-details.component';

@Component({
  selector: 'vehicle-card',
  templateUrl: './vehicle-card.component.html',
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent {
  @Input() vehicle: Vehicle | null = null;
  @Input() lang = 'us';
  @Output() reserveVehicleEvent = new EventEmitter<number>;

  constructor(private dialogService: DialogService) {}

  showDetails() {
    this.dialogService.open(VehicleDetailsComponent, {
      data: Object.assign({}, this.vehicle)
    });
  }

  reserveVehicle(vehicle: Vehicle) {
    this.reserveVehicleEvent.emit(vehicle.id);
  }

  calculateCurrency(price: number) {
    // TODO pobieranie wartości walutowych na dany dzień, a może job na backend i raz dziennie odpalać quartz i rekalkulować cene w zależności od waluty?
    if (this.lang === 'en') {
      return (price / 4).toFixed(2);
    }
    return price;
  }

  protected readonly EngineType = EngineType;
}
