import { Injectable } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ProfileMfaFormService {

  constructor(private fb: FormBuilder) {
  }

  getForm(): FormGroup {
    return this.fb.group({
      activateMfa: new FormControl(false, [])
    });
  }

  getActivateMfaControl(form: FormGroup): AbstractControl {
    return form.get('activateMfa') as AbstractControl;
  }

}
