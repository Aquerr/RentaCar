import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { VehicleApiService } from '../../../services/api/vehicle-api.service';
import { VehicleBasicData } from '../../../models/vehicle.model';
import { ActivatedRoute, Router } from '@angular/router';
import { LanguageService } from '../../../services/language.service';
import { ReservationApiService } from '../../../services/api/reservation-api.service';
import { ReservationRequest, ReservationStatus } from '../../../models/reservation.model.ts';
import { AuthenticationService } from '../../../services/authentication.service';
import { UserProfile } from '../../../models/user-profile.model';
import { ReservationService } from '../../../services/reservation.service';

@Component({
  selector: 'vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.scss']
})
export class VehicleListComponent implements OnInit, OnDestroy {
  subscriptions = new Subscription();
  vehicles: VehicleBasicData[] = [];
  user: UserProfile | null = null;
  dateFrom = '';
  dateTo = '';
  lang = 'us';

  constructor(private vehicleApiService: VehicleApiService,
              private reservationApiService: ReservationApiService,
              private authenticationService: AuthenticationService,
              private reservationService: ReservationService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private languageService: LanguageService) {
  }

  ngOnInit() {
    this.lang = this.languageService.getLanguage();
    this.activatedRoute.params.subscribe({
      next: (routes) => {
        const routeDates = routes['dates'] as string;
        this.dateFrom = routeDates.substring(6, routeDates.indexOf('&'));
        this.dateTo = routeDates.substring(routeDates.indexOf('&') + 1, routeDates.length - 1);
        this.getVehiclesAvailable();
      }
    });
    this.getUser();
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  getVehiclesAvailable() {
    this.subscriptions.add(this.vehicleApiService.getVehiclesAvailable(this.dateFrom, this.dateTo).subscribe({
      next: (response) => {
        this.vehicles = response.vehicles.sort((a, b) => a.brand.localeCompare(b.brand));
      },
      error: () => this.vehicles = []
    }));
  }

  getUser() {
    this.subscriptions.add(this.authenticationService.getUser().subscribe({
      next: (user) => {
        this.user = user;
      }
    }));
  }

  reserveVehicle(vehicleId: number) {
    const request = this.prepareReservationRequest(vehicleId);
    this.reservationApiService.saveReservationRequest(request).subscribe({
      next: (response) => {
        this.reservationService.updateReservation(response.reservation);
        this.router.navigate(['/reservation', response.reservation.id]);
      }
    });
  }

  prepareReservationRequest(vehicleId: number) {
    return {
      vehicleId: vehicleId,
      userId: this.user?.id,
      dateFrom: this.dateFrom,
      dateTo: this.dateTo,
      status: ReservationStatus.DRAFT
    } as ReservationRequest;
  }

}
