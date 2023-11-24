import { Injectable } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ProfileMfaFormService {

  constructor(private fb: FormBuilder) {
  }

  getForm(): FormGroup {
    return this.fb.group({
      activateMfa: new FormControl(false, []),
      totp: this.fb.group({
        code: new FormControl(null, [Validators.required, Validators.pattern('[0-9]{6}')])
      })
    });
  }

  getActivateMfaControl(form: FormGroup): AbstractControl {
    return form.get('activateMfa') as AbstractControl;
  }

  getTotpCodeControl(form: FormGroup): AbstractControl {
    return form.get('totp.code') as AbstractControl;
  }

}
