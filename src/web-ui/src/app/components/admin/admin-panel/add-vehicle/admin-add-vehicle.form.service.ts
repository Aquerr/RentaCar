import { Injectable } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { VehicleFullData } from '../../../../models/vehicle.model';

@Injectable({
  providedIn: 'root'
})
export class AdminAddVehicleFormService {

  constructor(private fb: FormBuilder) {
  }

  getForm(): FormGroup {
    return this.fb.group({
      brand: new FormControl(null, [Validators.required]),
      model: new FormControl(null, [Validators.required]),
      productionYear: new FormControl(null, [Validators.required]),
      seatsAmount: new FormControl(null, [Validators.required]),
      basicPrice: new FormControl(null, [Validators.required]),
      color: new FormControl('#ffffff', [Validators.required]),
      rimsInch: new FormControl(null, [Validators.required]),
      capacity: new FormControl(null, [Validators.required]),
      type: new FormControl(null, [Validators.required]),
      power: new FormControl(null, [Validators.required]),
      torque: new FormControl(null, [Validators.required]),
      avgFuelConsumption: new FormControl(null, [Validators.required]),
      transmission: new FormControl(null, [Validators.required]),
      ac: new FormControl(false, [Validators.required]),
      frontPDC: new FormControl(false, [Validators.required]),
      rearPDC: new FormControl(false, [Validators.required]),
      bluetooth: new FormControl(false, [Validators.required]),
      ledFrontLights: new FormControl(false, [Validators.required]),
      xenonFrontLights: new FormControl(false, [Validators.required]),
      ledRearLights: new FormControl(false, [Validators.required]),
      leatherSeats: new FormControl(false, [Validators.required]),
      multifunctionalSteeringWheel: new FormControl(false, [Validators.required]),
      cruiseControl: new FormControl(false, [Validators.required]),
      category: new FormControl(null, [Validators.required])
    });
  }

  convertFormToVehicle(form: FormGroup) {
    return {
      brand: this.getBrandControl(form).value,
      model: this.getModelControl(form).value,
      productionYear: this.getProductionYearControl(form).value,
      seatsAmount: this.getSeatsAmountControl(form).value,
      body: {
        color: this.getColorControl(form).value,
        rimsInch: this.getRimsInchControl(form).value
      },
      engine: {
        capacity: this.getCapacityControl(form).value,
        type: this.getTypeControl(form).value[0],
        power: this.getPowerControl(form).value,
        torque: this.getTorqueControl(form).value,
        avgFuelConsumption: this.getAvgFuelConsumptionControl(form).value,
        transmission: this.getTransmissionControl(form).value[0]
      },
      equipment: {
        ac: this.getACControl(form).value,
        frontPDC: this.getFrontPDCControl(form).value,
        rearPDC: this.getRearPDCControl(form).value,
        bluetooth: this.getBluetoothControl(form).value,
        ledFrontLights: this.getLedFrontLightsControl(form).value,
        xenonFrontLights: this.getXenonFrontLightsControl(form).value,
        ledRearLights: this.getLedRearLightsControl(form).value,
        leatherSeats: this.getLeatherSeatsControl(form).value,
        multifunctionalSteeringWheel: this.getMultifunctionalSteeringWheelControl(form).value,
        cruiseControl: this.getCruiseControlControl(form).value
      },
      category: this.getCategoryControl(form).value[0],
      basicPrice: this.getBasicPriceControl(form).value,
      photosUrls: []
    } as VehicleFullData;
  }

  getBrandControl(form: FormGroup): AbstractControl {
    return form.get('brand') as AbstractControl;
  }

  getModelControl(form: FormGroup): AbstractControl {
    return form.get('model') as AbstractControl;
  }

  getProductionYearControl(form: FormGroup): AbstractControl {
    return form.get('productionYear') as AbstractControl;
  }

  getSeatsAmountControl(form: FormGroup): AbstractControl {
    return form.get('seatsAmount') as AbstractControl;
  }

  getColorControl(form: FormGroup): AbstractControl {
    return form.get('color') as AbstractControl;
  }

  getRimsInchControl(form: FormGroup): AbstractControl {
    return form.get('rimsInch') as AbstractControl;
  }

  getCapacityControl(form: FormGroup): AbstractControl {
    return form.get('capacity') as AbstractControl;
  }

  getTypeControl(form: FormGroup): AbstractControl {
    return form.get('type') as AbstractControl;
  }

  getPowerControl(form: FormGroup): AbstractControl {
    return form.get('power') as AbstractControl;
  }

  getTorqueControl(form: FormGroup): AbstractControl {
    return form.get('torque') as AbstractControl;
  }

  getAvgFuelConsumptionControl(form: FormGroup): AbstractControl {
    return form.get('avgFuelConsumption') as AbstractControl;
  }

  getTransmissionControl(form: FormGroup): AbstractControl {
    return form.get('transmission') as AbstractControl;
  }

  getACControl(form: FormGroup): AbstractControl {
    return form.get('ac') as AbstractControl;
  }

  getFrontPDCControl(form: FormGroup): AbstractControl {
    return form.get('frontPDC') as AbstractControl;
  }

  getRearPDCControl(form: FormGroup): AbstractControl {
    return form.get('rearPDC') as AbstractControl;
  }

  getBluetoothControl(form: FormGroup): AbstractControl {
    return form.get('bluetooth') as AbstractControl;
  }

  getLedFrontLightsControl(form: FormGroup): AbstractControl {
    return form.get('ledFrontLights') as AbstractControl;
  }

  getXenonFrontLightsControl(form: FormGroup): AbstractControl {
    return form.get('xenonFrontLights') as AbstractControl;
  }

  getLedRearLightsControl(form: FormGroup): AbstractControl {
    return form.get('ledRearLights') as AbstractControl;
  }

  getLeatherSeatsControl(form: FormGroup): AbstractControl {
    return form.get('leatherSeats') as AbstractControl;
  }

  getMultifunctionalSteeringWheelControl(form: FormGroup): AbstractControl {
    return form.get('multifunctionalSteeringWheel') as AbstractControl;
  }

  getCruiseControlControl(form: FormGroup): AbstractControl {
    return form.get('cruiseControl') as AbstractControl;
  }

  getCategoryControl(form: FormGroup): AbstractControl {
    return form.get('category') as AbstractControl;
  }

  getBasicPriceControl(form: FormGroup): AbstractControl {
    return form.get('basicPrice') as AbstractControl;
  }
}
