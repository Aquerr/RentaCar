import { Component, OnDestroy, OnInit } from '@angular/core';
import { PickupLocationApiService } from '../../services/api/pickup-location-api.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { DateService } from '../../services/date.service';

@Component({
  selector: 'searcher',
  templateUrl: './searcher.component.html',
  styleUrls: ['./searcher.component.scss']
})
export class SearcherComponent implements OnInit, OnDestroy {
  subscription = new Subscription();
  dateFrom = new Date();
  hourFrom = new Date();
  dateTo = new Date;
  hourTo = new Date();
  pickupLocations: PickupLocationDropdown[] = [];
  selectedPickupLocation: PickupLocationDropdown | null = null;

  constructor(private apiService: PickupLocationApiService,
              private router: Router,
              private dateService: DateService) {
  }

  ngOnInit() {
    this.setStartDates();
    this.startApiServiceSubscription();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  startApiServiceSubscription() {
    this.subscription = this.apiService.getPickupLocations().subscribe({
      next: (response) => {
        response.locations.sort((a, b) => a.city.localeCompare(b.city))
        .forEach((location) => {
          this.pickupLocations.push({
            id: location.id,
            displayName: location.name + ' (' + location.city + ')'
          });
        });
      }
    });
  }

  searchVehicles() {
    const dateParam = this.prepareDateParam();
    this.router.navigate(['vehicle-list', dateParam]);
  }

  setStartDates() {
    this.dateTo.setDate(this.dateTo.getDate() + 7);
    this.dateFrom.setMinutes(0);
    this.hourFrom.setMinutes(0);
    this.dateTo.setMinutes(0);
    this.hourTo.setMinutes(0);
  }

  prepareDateParam() {
    const dateFrom = this.dateService.convertDateToLocalDate(this.dateFrom);
    const dateTo = this.dateService.convertDateToLocalDate(this.dateTo);
    return 'dates?' + dateFrom + '&' + dateTo;
  }
}

export interface PickupLocationDropdown {
  id: number;
  displayName: string;
}
