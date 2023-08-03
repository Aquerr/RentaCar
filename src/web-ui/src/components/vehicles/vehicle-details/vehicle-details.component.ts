import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Vehicle } from '../../../models/vehicle.model';

@Component({
  selector: 'vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.scss']
})
export class VehicleDetailsComponent implements OnInit {
  vehicle: Vehicle | null = null;

  constructor(private ref: DynamicDialogRef,
              private config: DynamicDialogConfig) {}

  ngOnInit() {
    this.vehicle = this.config.data;
  }

  close() {
    this.ref.close();
  }
}
