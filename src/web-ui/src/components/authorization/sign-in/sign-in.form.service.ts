import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserSignInRequest } from '../../../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class SignInFormService {
  private form = new FormGroup({
    login: new FormControl('', [Validators.required, Validators.minLength(5)]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
    ]),
    rememberMe: new FormControl(false, []),
  });

  getForm() {
    return this.form;
  }

  createUserSignInRequest(): UserSignInRequest {
    return new UserSignInRequest(
      this.getLogin(),
      this.getPassword(),
      this.getRememberMe(),
    );
  }

  public isFormValid(): boolean {
    return this.form.status === 'VALID';
  }

  getLogin() {
    return this.form.controls.login.value as string;
  }

  getPassword() {
    return this.form.controls.password.value as string;
  }

  getRememberMe() {
    return this.form.controls.rememberMe.value as boolean;
  }
}
