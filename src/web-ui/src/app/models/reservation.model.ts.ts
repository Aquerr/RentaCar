export class Reservation {
  id?: number;
  vehicleId: number;
  userId: number;
  dateFrom: string;
  dateTo: string;
  status: ReservationStatus;

  constructor(vehicleId: number, userId: number, dateFrom: string, dateTo: string, status: ReservationStatus, id?: number) {
    this.id = id;
    this.vehicleId = vehicleId;
    this.userId = userId;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.status = status;
  }
}

export enum ReservationStatus {
  DRAFT = 'DRAFT',
  PENDING_PAYMENT = 'PENDING_PAYMENT',
  PAYMENT_COMPLETED = 'PAYMENT_COMPLETED',
  VEHICLE_DELIVERED = 'VEHICLE_DELIVERED',
  COMPLETED = 'COMPLETED',
  VEHICLE_NOT_AVAILABLE = 'VEHICLE_NOT_AVAILABLE',
  CANCELLED = 'CANCELLED'
}

export interface ProfileReservation {
  id: number;
  vehicleIconUrl: string;
  vehicleName: string;
  dateFrom: string;
  dateTo: string;
  status: ReservationStatus;
}
