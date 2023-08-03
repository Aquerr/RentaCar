export interface Reservation {
  id: number;
  vehicleId: number;
  userId: number;
  dateFrom: string;
  dateTo: string;
  status: ReservationStatus;
}

export interface ReservationRequest {
  vehicleId: number;
  userId: number;
  dateFrom: string;
  dateTo: string;
  status: ReservationStatus;
}

export enum ReservationStatus {
  DRAFT = 'DRAFT',
  PENDING_PAYMENT = 'PENDING_PAYMENT',
  PAYMENT_COMPLETED = 'PAYMENT_COMPLETED',
  CAR_DELIVERED = 'CAR_DELIVERED',
  COMPLETED = 'COMPLETED'
}
