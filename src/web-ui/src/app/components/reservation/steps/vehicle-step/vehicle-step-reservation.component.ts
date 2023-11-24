import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { VehicleApiService } from '../../../../services/api/vehicle-api.service';
import { VehicleFullData } from '../../../../models/vehicle.model';

@Component({
  selector: 'vehicle-step-reservation',
  templateUrl: './vehicle-step-reservation.component.html',
  styleUrls: ['./vehicle-step-reservation.component.scss']
})
export class VehicleStepReservationComponent implements OnInit, OnDestroy {
  vehicle: VehicleFullData | null = null;
  subscription: Subscription = new Subscription();
  @Input() vehicleId: number = 1;

  constructor(private vehicleApiService: VehicleApiService) {}

  ngOnInit() {
    this.getVehicle();
  }

  getVehicle() {
    this.subscription = this.vehicleApiService.getVehicleFullData(this.vehicleId).subscribe({
      next: (vehicleResponse) => this.vehicle = vehicleResponse.vehicle
    });
  }

  getImage() {
    return this.vehicle?.photosUrls[0];
  }

  getTranslation(any: any, any1: any) {
    return '';
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
