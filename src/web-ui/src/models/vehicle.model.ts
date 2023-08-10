export interface VehicleFullData {
  id: number;
  brand: string;
  model: string;
  productionYear: Date;
  seatsAmount: number;
  body: Body;
  engine: Engine;
  equipment: Equipment;
  category: Category;
  basicPrice: number;
  photosUrls: string[];
}

export interface VehicleBasicData {
  id: number;
  brand: string;
  model: string;
  engine: Engine;
  equipment: Equipment;
  basicPrice: number;
  photoUrl: string;
}

export interface Body {
  color: string;
  rimsInch: number;
}

export interface Engine {
  capacity: number;
  type: EngineType;
  power: number;
  torque: number;
  avgFuelConsumption: number;
  transmission: Transmission;
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
