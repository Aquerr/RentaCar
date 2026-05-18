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
      totp: this.fb.group({
        code: new FormControl(null, [Validators.required, Validators.pattern('[0-9]{6}')])
      })
    });
  }

  getTotpCodeControl(form: FormGroup): AbstractControl {
    return form.get('totp.code') as AbstractControl;
  }

}
