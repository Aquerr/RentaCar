import { Injectable } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserProfile } from '../../../../models/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class ContactStepReservationFormService {

  constructor(private fb: FormBuilder) {
  }

  getForm(): FormGroup {
    return this.fb.group({
      id: new FormControl(null, [Validators.required]),
      firstName: new FormControl(null, [Validators.required]),
      lastName: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required, Validators.email]),
      birthDate: new FormControl(null, [Validators.required]),
      city: new FormControl(null, [Validators.required]),
      zipCode: new FormControl(null, [Validators.required, Validators.pattern('[0-9]{2}[0-9]{3}')]),
      street: new FormControl(null, [Validators.required]),
      phoneNumber: new FormControl(null, [Validators.required, Validators.pattern('[0-9]{9}')]),
      iconUrl: new FormControl(null, [])
    });
  }

  setForm(form: FormGroup, userProfile: UserProfile) {
    this.getIdControl(form).setValue(userProfile.id);
    this.getFirstNameControl(form).setValue(userProfile.firstName);
    this.getLastNameControl(form).setValue(userProfile.lastName);
    this.getEmailControl(form).setValue(userProfile.email);
    this.getBirthDateControl(form).setValue(new Date(userProfile.birthDate));
    this.getCityControl(form).setValue(userProfile.city);
    this.getZipCodeControl(form).setValue(userProfile.zipCode);
    this.getStreetControl(form).setValue(userProfile.street);
    this.getPhoneNumberControl(form).setValue(userProfile.phoneNumber);
    this.getIconUrlControl(form).setValue(userProfile.iconUrl);
  }

  convertFormToUserProfile(form: FormGroup) {
    return {
      id: this.getIdControl(form).value,
      firstName: this.getFirstNameControl(form).value,
      lastName: this.getLastNameControl(form).value,
      email: this.getEmailControl(form).value,
      birthDate: this.getBirthDateControl(form).value,
      city: this.getCityControl(form).value,
      zipCode: this.getZipCodeControl(form).value,
      street: this.getStreetControl(form).value,
      phoneNumber: this.getPhoneNumberControl(form).value,
      iconUrl: this.getIconUrlControl(form).value
    } as UserProfile;
  }

  getIdControl(form: FormGroup): AbstractControl {
    return form.get('id') as AbstractControl;
  }

  getFirstNameControl(form: FormGroup): AbstractControl {
    return form.get('firstName') as AbstractControl;
  }

  getLastNameControl(form: FormGroup): AbstractControl {
    return form.get('lastName') as AbstractControl;
  }

  getPhoneNumberControl(form: FormGroup): AbstractControl {
    return form.get('phoneNumber') as AbstractControl;
  }

  getEmailControl(form: FormGroup): AbstractControl {
    return form.get('email') as AbstractControl;
  }

  getBirthDateControl(form: FormGroup): AbstractControl {
    return form.get('birthDate') as AbstractControl;
  }

  getCityControl(form: FormGroup): AbstractControl {
    return form.get('city') as AbstractControl;
  }

  getZipCodeControl(form: FormGroup): AbstractControl {
    return form.get('zipCode') as AbstractControl;
  }

  getStreetControl(form: FormGroup): AbstractControl {
    return form.get('street') as AbstractControl;
  }

  getIconUrlControl(form: FormGroup): AbstractControl {
    return form.get('iconUrl') as AbstractControl;
  }
}
