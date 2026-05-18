import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Reservation } from '../../../../models/reservation.model.ts';
import { AuthenticationService } from '../../../../services/authentication.service';
import { VehicleApiService, VehicleFullDataResponse } from '../../../../services/api/vehicle-api.service';
import { UserProfile } from '../../../../models/user-profile.model';
import { DateService } from '../../../../services/date.service';

@Component({
  selector: 'summary-step-reservation',
  templateUrl: './summary-step-reservation.component.html',
  styleUrls: ['./summary-step-reservation.component.scss']
})
export class SummaryStepReservationComponent implements OnInit, OnDestroy {
  userProfile: UserProfile | null = null;
  vehicle: VehicleFullDataResponse | null = null;
  subscriptions: Subscription = new Subscription();
  @Input() reservation: Reservation | null = null;

  constructor(private authenticationService: AuthenticationService,
              private vehicleService: VehicleApiService,
              private dateService: DateService) {}

  ngOnInit() {
    this.getUser();
    this.getVehicle();
  }

  getUser() {
    this.subscriptions.add(this.authenticationService.getUser().subscribe({
      next: (userProfile) => this.userProfile = userProfile
    }));
  }

  getVehicle() {
    const vehicleId = this.reservation?.vehicleId as number;
    this.subscriptions.add(this.vehicleService.getVehicleFullData(vehicleId).subscribe({
      next: (vehicle) => this.vehicle = vehicle
    }));
  }

  calculatePrice() {
    const dateFrom = new Date(this.reservation?.dateFrom as string);
    const dateTo = new Date(this.reservation?.dateTo as string);
    const days = this.dateService.calculateDiffrenceBetweenDatesInDays(dateFrom, dateTo);
    const price = this.vehicle?.vehicle.basicPrice as number;
    return days * price;
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}
