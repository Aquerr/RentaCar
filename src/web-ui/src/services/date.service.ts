import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DateService {

  constructor(private datePipe: DatePipe) {
  }

  public convertDateToLocalDate(date: Date) {
    return this.datePipe.transform(date, 'yyyy-MM-dd') as string;
  }

  public calculateDiffrenceBetweenDatesInDays(dateFrom: Date, dateTo: Date) {
    const dateFromZero = this.zeroDate(dateFrom);
    const dateToZero = this.zeroDate(dateTo);
    const dateToPlusOne = new Date(dateToZero.setDate(dateToZero.getDate() + 1));
    const diffrenceInTime = dateToPlusOne.getTime() - dateFromZero.getTime();
    return Math.floor(diffrenceInTime / (1000 * 3600 * 24));
  }

  public zeroDate(date: Date) {
    const newDate = new Date(date);
    newDate.setHours(0);
    newDate.setMinutes(0);
    newDate.setSeconds(0);
    newDate.setMilliseconds(0);
    return newDate;
  }
}
