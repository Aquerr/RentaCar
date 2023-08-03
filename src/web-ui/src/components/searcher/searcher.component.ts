import { Component, OnDestroy, OnInit } from '@angular/core';
import { PickupLocationApiService } from '../../services/api/pickup-location-api.service';
import { Subscription } from 'rxjs';

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

  constructor(private apiService: PickupLocationApiService) {
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

  setStartDates() {
    this.dateTo.setDate(this.dateTo.getDate() + 7);
    this.dateFrom.setMinutes(0);
    this.hourFrom.setMinutes(0);
    this.dateTo.setMinutes(0);
    this.hourTo.setMinutes(0);
  }
}

export interface PickupLocationDropdown {
  id: number;
  displayName: string;
}
