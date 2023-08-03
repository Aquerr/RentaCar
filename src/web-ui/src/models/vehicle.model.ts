export interface Vehicle {
  id: number;
  brand: string;
  model: string;
  productionYear: Date;
  amountOfSeats: number;
  body: Body;
  engine: Engine;
  equipment: Equipment;
  category: Category;
  transmission: Transmission;
  pricePerDay: number;
  photoUrl: string;
}

export interface Body {
  color: string;
  rimsInch: number;
}

export interface Engine {
  capacity: number;
  type: EngineType;
  avgFuelConsumption: number;
}

export interface Equipment {
  ac: boolean;
  frontPDC: boolean;
  rearPDC: boolean;
  bluetooth: boolean;
  ledFrontLights: boolean;
  xenonFrontLights: boolean;
  ledRearLights: boolean;
  leatherSeats: boolean;
  multifunctionalSteeringWheel: boolean;
}

export enum EngineType {
  GAS = 'GAS',
  DIESEL = 'DIESEL',
  HYBRID = 'HYBRID',
  ELECTRIC = 'ELECTRIC'
}

export enum Category {
  A = 'A',
  B = 'B',
  C = 'C',
  D = 'D'
}

export enum Transmission {
  MANUAL = 'MANUAL',
  AUTOMATIC = 'AUTOMATIC'
}
