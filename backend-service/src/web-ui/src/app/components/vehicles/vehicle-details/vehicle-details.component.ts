import { Component, OnDestroy, OnInit } from '@angular/core';
import { VehicleApiService } from '../../../services/api/vehicle-api.service';
import { VehicleFullData } from '../../../models/vehicle.model';
import { Subscription } from 'rxjs';
import { DateService } from '../../../services/date.service';
import { Router } from '@angular/router';
import { ReservationApiService } from '../../../services/api/reservation-api.service';
import { Reservation, ReservationStatus } from '../../../models/reservation.model.ts';
import { AuthenticationService } from '../../../services/authentication.service';
import { UserProfile } from '../../../models/user-profile.model';

@Component({
  selector: 'vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.scss']
})
export class VehicleDetailsComponent implements OnInit, OnDestroy {
  vehicleId: number | null = null;
  vehicle: VehicleFullData | null = null;
  subscriptions: Subscription = new Subscription();
  vehicleAvailable = true;
  dateFrom = new Date();
  dateTo = new Date();
  today = new Date();
  user: UserProfile | null = null;
  totalPrice = 0;

  constructor(private router: Router,
              private apiService: VehicleApiService,
              private reservationApiService: ReservationApiService,
              private authenticationService: AuthenticationService,
              private dateService: DateService) {}

  ngOnInit() {
    const url = this.router.routerState.snapshot.url;
    const vehicleId = url.substring(url.lastIndexOf('/') + 1, url.length);
    this.vehicleId = vehicleId as unknown as number;
    this.getVehicleFullData();
    this.getUser();
    this.dateService.zeroDate(this.dateFrom);
    this.dateService.zeroDate(this.dateTo);
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  getVehicleFullData() {
    this.subscriptions.add(this.apiService.getVehicleFullData(this.vehicleId as number).subscribe({
      next: (response) => {
        this.vehicle = response.vehicle;
        this.setTotalPrice();
        this.isVehicleAvailable();
      }
    }));
  }

  getUser() {
    this.subscriptions.add(this.authenticationService.getUser().subscribe({
      next: (user) => {
        this.user = user;
      }
    }));
  }

  getTranslation(value: string, category: string) {
    return 'additional' + '.' + category + '.' + value.toLowerCase();
  }

  onDateChange() {
    this.isVehicleAvailable();
    this.setTotalPrice();
  }

  isVehicleAvailable() {
    const dateFrom = this.dateService.convertDateToLocalDate(this.dateFrom);
    const dateTo = this.dateService.convertDateToLocalDate(this.dateTo);
    this.subscriptions.add(this.apiService.isVehicleAvailable(this.vehicleId as number, dateFrom, dateTo).subscribe({
      next: (available) => this.vehicleAvailable = available
    }));
  }

  reserve() {
    const request = this.prepareReservationRequest();
    this.reservationApiService.createNewReservation(request).subscribe({
      next: (response) => {
        this.router.navigate(['/reservation', response.reservation.id]);
      }
    });
  }

  setTotalPrice() {
    if (this.vehicle) {
      const days = this.dateService.calculateDiffrenceBetweenDatesInDays(this.dateFrom, this.dateTo);
      this.totalPrice = this.vehicle?.basicPrice * days;
    }
  }

  prepareReservationRequest() {
    return {
      vehicleId: this.vehicleId,
      userId: this.user?.id,
      dateFrom: this.dateService.convertDateToLocalDate(this.dateFrom),
      dateTo: this.dateService.convertDateToLocalDate(this.dateTo),
      status: ReservationStatus.DRAFT
    } as Reservation;
  }
}
