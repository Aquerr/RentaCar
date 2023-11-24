import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {MfaAuthenticationRequest} from '../../../services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class SignInMfaFormService {
  private form = new FormGroup({
    code: new FormControl('', [Validators.required, Validators.minLength(6)]),
    challenge: new FormControl('', [Validators.required])
  });

  getForm() {
    return this.form;
  }

  createMfaSignInRequest(): MfaAuthenticationRequest {
    return new MfaAuthenticationRequest(
      this.getCode(),
      this.getChallenge()
    );
  }

  public isFormValid(): boolean {
    return this.form.status === 'VALID';
  }

  getCode() {
    return this.form.controls.code.value as string;
  }

  getChallenge() {
    return this.form.controls.challenge.value as string;
  }
}
