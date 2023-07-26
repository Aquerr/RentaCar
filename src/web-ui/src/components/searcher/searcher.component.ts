import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'searcher',
  templateUrl: './searcher.component.html',
  styleUrls: ['./searcher.component.scss'],
})
export class SearcherComponent implements OnInit {
  dateFrom = new Date();
  hourFrom = new Date();
  dateTo = new Date;
  hourTo = new Date();
  places= [{name: 'Warszawa', code: 'WW'},
    {name: 'Gdańsk', code: 'GD'}];
  selectedPlace: any;

  constructor() {
  }

  ngOnInit() {
    this.setStartDates();
  }

  setStartDates() {
    this.dateTo.setDate(this.dateTo.getDate() + 7);
    this.dateFrom.setMinutes(0);
    this.hourFrom.setMinutes(0);
    this.dateTo.setMinutes(0);
    this.hourTo.setMinutes(0);
  }
}
