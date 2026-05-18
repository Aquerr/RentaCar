import { Component, OnInit } from '@angular/core';
import { VehicleApiService } from '../../../../services/api/vehicle-api.service';
import { ConfirmationService } from 'primeng/api';
import { CommonService } from '../../../../services/common.service';
import { ToastType } from '../../../../services/toast.service';
import { AdminAddVehicleFormService } from './admin-add-vehicle.form.service';
import { FormGroup } from '@angular/forms';
import { FileUpload } from '../../../../models/file-upload.model';
import { LanguageService } from '../../../../services/language.service';


@Component({
  selector: 'add-vehicle',
  templateUrl: './admin-add-vehicle.component.html',
  styleUrls: ['./admin-add-vehicle.component.scss']
})
export class AdminAddVehicleComponent implements OnInit {
  form: FormGroup;
  imageFiles: FileUpload[] = [];
  today = new Date();
  categories = ['A', 'B', 'C', 'D'];
  userLang = 'en';

  constructor(private vehicleApiService: VehicleApiService,
              private commonService: CommonService,
              private confirmationService: ConfirmationService,
              private formService: AdminAddVehicleFormService,
              private languageService: LanguageService) {
    this.form = this.formService.getForm();
  }

  ngOnInit() {
    this.userLang = this.languageService.getLanguage();
  }

  addVehicleConfirmationDialog() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.confirmationService.confirm({
        accept: () => this.addVehicle()
      });
    }
  }

  addVehicle() {
    const vehicle = this.formService.convertFormToVehicle(this.form);
    const files = this.imageFiles.map(imageFile => imageFile.file);
    this.vehicleApiService.saveVehicle(vehicle, files).subscribe({
      next: (vehicleResponse) => {
        this.commonService.showToast('components.admin-add-vehicle.toasts.success', ToastType.SUCCESS);
        this.commonService.goRoute(`vehicle-details/${vehicleResponse.vehicle.id}`);
      },
      error: () => this.commonService.showToast('components.admin-add-vehicle.toasts.error', ToastType.ERROR)
    });
  }

  openFileBrowser() {
    const fileInput = document.getElementById('file-input') as HTMLElement;
    fileInput.click();
  }

  uploadFile(fileEvent: any) {
    const file = fileEvent.target.files[0] as File;
    const id = new Date().getTime();
    const fileUpload = new FileUpload(id, file);
    this.imageFiles.push(fileUpload);
  }

  getImage(fileUpload: FileUpload) {
    let url = '';
    if (fileUpload.file) {
      const reader = new FileReader();
      reader.readAsDataURL(fileUpload.file);
      reader.onload = (event) => url = event.target?.result as string;
    }
    return url;
  }

  removeFile(fileUpload: FileUpload) {
    this.imageFiles = this.imageFiles.filter(imageFile => imageFile !== fileUpload);
  }

  getTypes() {
    if (this.userLang === 'pl') {
      return [
        { label: 'Benzyna', value: 'GAS' },
        { label: 'Diesel', value: 'DIESEL' },
        { label: 'Hybryda', value: 'HYBRID' },
        { label: 'Elektryk', value: 'ELECTRIC' }
      ];
    } else {
      return [
        { label: 'Gas', value: 'GAS' },
        { label: 'Diesel', value: 'DIESEL' },
        { label: 'Hybrid', value: 'HYBRID' },
        { label: 'Electric', value: 'ELECTRIC' }
      ];
    }
  }

  getTransmissions() {
    if (this.userLang === 'pl') {
      return [
        { label: 'Skrzynia ręczna', value: 'MANUAL' },
        { label: 'Skrzynia automatyczna', value: 'AUTOMATIC' }
      ];
    } else {
      return [
        { label: 'Manual gearbox', value: 'MANUAL' },
        { label: 'Automatic gearbox', value: 'AUTOMATIC' }
      ];
    }
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }
}
