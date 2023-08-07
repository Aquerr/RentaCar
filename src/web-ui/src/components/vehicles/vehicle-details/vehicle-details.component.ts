import { Component, OnDestroy, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { VehicleApiService } from '../../../services/api/vehicle-api.service';
import { VehicleFullData } from '../../../models/vehicle.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.scss']
})
export class VehicleDetailsComponent implements OnInit, OnDestroy {
  vehicleId: number | null = null;
  vehicle: VehicleFullData | null = null;
  subscription: Subscription = new Subscription();

  constructor(private apiService: VehicleApiService,
              private ref: DynamicDialogRef,
              private config: DynamicDialogConfig) {}

  ngOnInit() {
    this.vehicleId = this.config.data;
    this.getVehicleFullData();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getVehicleFullData() {
    this.subscription = this.apiService.getVehicleFullData(this.vehicleId as number).subscribe({
      next: (response) => this.vehicle = response.vehicle
    });
  }

  getTranslation(value: string, category: string) {
    return 'additional' + '.' + category + '.' + value.toLowerCase();
  }

  close() {
    this.ref.close();
  }
}
