import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { VehicleApiService } from '../../../services/api/vehicle-api.service';
import { Category, EngineType, Transmission, Vehicle } from '../../../models/vehicle.model';
import { ActivatedRoute } from '@angular/router';
import { LanguageService } from '../../../services/language.service';

@Component({
  selector: 'vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.scss']
})
export class VehicleListComponent implements OnInit, OnDestroy {
  subscription = new Subscription();
  vehicles: Vehicle[] = [];
  lang = 'us';

  constructor(private apiService: VehicleApiService,
              private activatedRoute: ActivatedRoute,
              private languageService: LanguageService) {
  }

  ngOnInit() {
    this.lang = this.languageService.getLanguage();
    this.activatedRoute.params.subscribe({
      next: (routes) => {
        const routeDates = routes['dates'] as string;
        const dateFrom = routeDates.substring(6, routeDates.indexOf('&'));
        const dateTo = routeDates.substring(routeDates.indexOf('&') + 1, routeDates.length - 1);
        this.getVehiclesAvailable(dateFrom, dateTo);
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getVehiclesAvailable(dateFrom: string, dateTo: string) {
    // TODO do usunięcia gdy powstanie logika backendowa z dummy-data
    this.vehicles = [
      {
        id: 1,
        brand: 'brand',
        model: 'model',
        photoUrl: 'vehicles/1/photo.webp',
        productionYear: new Date(),
        amountOfSeats: 5,
        pricePerDay: 250.99,
        body: {
          color: 'red',
          rimsInch: 21
        },
        engine: {
          capacity: 1899,
          type: EngineType.GAS,
          avgFuelConsumption: 5.7
        },
        equipment: {
          ac: true,
          frontPDC: false,
          rearPDC: true,
          steeringAssist: true,
          bluetooth: true,
          ledFrontLights: true,
          xenonFrontLights: false,
          ledRearLights: true,
          leatherSeats: true,
          multifunctionalSteeringWheel: true
        },
        category: Category.A,
        transmission: Transmission.MANUAL

      } as Vehicle,
      {
        id: 2,
        brand: 'brand1',
        model: 'model1',
        photoUrl: 'vehicles/2/photo.webp',
        productionYear: new Date(),
        amountOfSeats: 5,
        pricePerDay: 650.99,
        body: {
          color: 'blue',
          rimsInch: 21
        },
        engine: {
          capacity: 1899,
          type: EngineType.HYBRID,
          avgFuelConsumption: 5.7
        },
        equipment: {
          ac: false,
          frontPDC: true,
          rearPDC: true,
          steeringAssist: true,
          bluetooth: true,
          ledFrontLights: true,
          xenonFrontLights: true,
          ledRearLights: true,
          leatherSeats: true,
          multifunctionalSteeringWheel: true
        },
        category: Category.B,
        transmission: Transmission.AUTOMATIC
      } as Vehicle
    ];
    // this.subscription = this.apiService.getVehiclesAvailable(dateFrom, dateTo).subscribe({
    //   next: (response) => {
    //     this.vehicles = response.vehicles.sort((a, b) => a.brand.localeCompare(b.brand));
    //   }
    // });
  }

  reserveVehicle(vehicle: Vehicle) {
    // TODO rezerwacja pojazdow
  }

}
