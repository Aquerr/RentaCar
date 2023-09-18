import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { VehicleApiService } from '../../../services/api/vehicle-api.service';
import { VehicleBasicData } from '../../../models/vehicle.model';
import { ActivatedRoute, Router } from '@angular/router';
import { LanguageService } from '../../../services/language.service';
import { ReservationApiService } from '../../../services/api/reservation-api.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { UserProfile } from '../../../models/user-profile.model';
import { ReservationService } from '../../../services/reservation.service';
import { DateService } from '../../../services/date.service';

@Component({
  selector: 'vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.scss']
})
export class VehicleListComponent implements OnInit, OnDestroy {
  subscriptions = new Subscription();
  vehicles: VehicleBasicData[] = [];
  user: UserProfile | null = null;
  dateFrom = new Date();
  dateTo = new Date();
  lang = 'us';

  constructor(private vehicleApiService: VehicleApiService,
              private reservationApiService: ReservationApiService,
              private authenticationService: AuthenticationService,
              private reservationService: ReservationService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private languageService: LanguageService,
              private dateService: DateService) {
  }

  ngOnInit() {
    this.lang = this.languageService.getLanguage();
    this.getVehiclesAvailable();
    this.getUser();
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  getVehiclesAvailable() {
    const dateFrom = this.dateService.convertDateToLocalDate(this.dateFrom);
    const dateTo = this.dateService.convertDateToLocalDate(this.dateTo);
    this.subscriptions.add(this.vehicleApiService.getVehiclesAvailable(dateFrom, dateTo).subscribe({
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

}
