import { Injectable } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserRegistrationRequest } from './sign-up.component';

@Injectable({
  providedIn: 'root'
})
export class SignUpFormService {

  getForm() {
    return new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(5)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(5)]),
      password2: new FormControl('', [Validators.required, Validators.minLength(5)])
    });
  }

  createUserSignUpRequest(form: FormGroup): UserRegistrationRequest {
    return new UserRegistrationRequest(
      this.getUsername(form).value,
      this.getEmail(form).value,
      this.getPassword(form).value
    );
  }

  isValid(form: FormGroup) {
    return form.valid && this.getPassword(form).value === this.getPassword2(form).value;
  }

  getUsername(form: FormGroup) {
    return form.get('username') as AbstractControl;
  }

  getEmail(form: FormGroup) {
    return form.get('email') as AbstractControl;
  }

  getPassword(form: FormGroup) {
    return form.get('password') as AbstractControl;
  }

  getPassword2(form: FormGroup) {
    return form.get('password2') as AbstractControl;
  }
}
