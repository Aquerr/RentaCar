import { Component, OnInit } from '@angular/core';
import { VehicleApiService } from '../../../../services/api/vehicle-api.service';
import { VehicleBasicData } from '../../../../models/vehicle.model';
import { CommonService } from '../../../../services/common.service';
import { ToastType } from '../../../../services/toast.service';
import { ConfirmationService } from 'primeng/api';


@Component({
  selector: 'remove-vehicle',
  templateUrl: './admin-remove-vehicle.component.html',
  styleUrls: ['./admin-remove-vehicle.component.scss']
})
export class AdminRemoveVehicleComponent implements OnInit {
  vehicles: VehicleBasicData[] = [];

  constructor(private vehicleApiService: VehicleApiService,
              private commonService: CommonService,
              private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.getAllVehicles();
  }

  removeVehicleDialog(vehicleId: number) {
    this.confirmationService.confirm({
      accept: () => this.removeVehicle(vehicleId)
    });
  }
  removeVehicle(vehicleId: number) {
    this.vehicleApiService.removeVehicle(vehicleId).subscribe({
      next: () => {
        this.commonService.showToast('components.admin-remove-vehicle.toasts.removeSuccess', ToastType.SUCCESS);
        this.getAllVehicles();
      },
      error: () => this.commonService.showToast('components.admin-remove-vehicle.toasts.removeError', ToastType.ERROR)
    })
  }

  getAllVehicles() {
    this.vehicleApiService.getAllVehicles().subscribe({
      next: (vehiclesResponse) => {
        console.log(vehiclesResponse)
        this.vehicles = vehiclesResponse.vehicles
      },
      error: () => this.vehicles = []
    })
  }

}
