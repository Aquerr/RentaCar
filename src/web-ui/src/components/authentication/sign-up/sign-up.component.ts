import { Component, OnDestroy, OnInit } from '@angular/core';
import { SignUpFormService } from './sign-up.form.service';
import { FormGroup } from '@angular/forms';
import { UserApiService } from '../../../services/api/user-api.service';
import { ToastType } from '../../../services/toast.service';
import { Subscription } from 'rxjs';
import { CommonService } from '../../../services/common.service';

@Component({
  selector: 'sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit, OnDestroy {
  form!: FormGroup;
  password2Subscription: Subscription = new Subscription();

  constructor(
    private userApiService: UserApiService,
    private formService: SignUpFormService,
    private commonService: CommonService
  ) {}

  ngOnInit() {
    this.form = this.formService.getForm();
    this.startPassword2Subscription();
  }

  ngOnDestroy() {
    this.password2Subscription.unsubscribe();
  }

  signUp() {
    this.form.markAllAsTouched();
    if (this.formService.isValid(this.form)) {
      const request = this.formService.createUserSignUpRequest(this.form);
      this.userApiService.register(request).subscribe({
        next: () => {
          this.commonService.showToast('components.sign-up.toasts.sign-up-success', ToastType.SUCCESS);
          this.form.reset();
        },
        error: () => this.commonService.showToast('components.sign-up.sign-up-error', ToastType.ERROR)
      });
    }
  }

  startPassword2Subscription() {
    const password2Control = this.formService.getPassword2(this.form);
    this.password2Subscription = password2Control.valueChanges.subscribe((value) => {
      const passwordControlValue = this.formService.getPassword(this.form).value as string;
      if (passwordControlValue.length > 0 && value.length > 0 && value !== passwordControlValue) {
        password2Control.setErrors({ passwordDoesntMatch: true });
      } else {
        password2Control.setErrors(null);
      }
    });
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }
}

export class UserRegistrationRequest {
  username: string;
  email: string;
  password: string;

  constructor(username: string, email: string, password: string) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
