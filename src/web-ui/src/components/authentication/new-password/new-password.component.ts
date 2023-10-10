import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthenticationApiService } from '../../../services/api/authentication-api.service';
import { AbstractControl, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CommonService } from '../../../services/common.service';
import { ToastType } from '../../../services/toast.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';


@Component({
  selector: 'new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.scss']
})
export class NewPasswordComponent implements OnInit, OnDestroy {
  form = new FormBuilder().group({
    password: new FormControl(null, [Validators.required, Validators.minLength(5)]),
    password2: new FormControl(null, [Validators.required, Validators.minLength(5)])
  });
  subscription: Subscription = new Subscription();
  token = '';

  constructor(private commonService: CommonService,
              private router: Router,
              private apiService: AuthenticationApiService) {}

  ngOnInit() {
    this.isTokenValid();
    this.password2ValueSubscription();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  isTokenValid() {
    const url = this.router.routerState.snapshot.url;
    this.token = url.substring(url.indexOf('=') + 1, url.length);
    this.apiService.isTokenValid(this.token).subscribe({
      next: (tokenValid) => {
        if (!tokenValid) {
          this.handleTokenInvalidError();
        }
      },
      error: () => this.handleTokenInvalidError()
    });
  }

  handleTokenInvalidError() {
    this.commonService.goRoute('');
    this.commonService.showToast('components.new-password.toasts.token-expired', ToastType.ERROR);
  }

  password2ValueSubscription() {
    this.subscription = this.getPassword2().valueChanges.subscribe({
      next: (password2Value) => {
        const passwordValue = this.getPassword().value;
        const password2Control = this.getPassword2();
        if (passwordValue && password2Value && passwordValue !== password2Value) {
          password2Control?.setErrors({ doesntMatch: true });
        } else {
          password2Control?.setErrors(null);
        }
      }
    });
  }

  setNewPassword() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.apiService.setNewPassword(this.token, this.getPassword().value).subscribe({
        next: () => {
          this.commonService.goRoute('');
          this.commonService.showToast('components.new-password.toasts.update-success', ToastType.SUCCESS);
        },
        error: () => this.commonService.showToast('components.new-password.toasts.update-error', ToastType.ERROR)
      });
    }
  }

  getPassword() {
    return this.form.get('password') as AbstractControl;
  }

  getPassword2() {
    return this.form.get('password2') as AbstractControl;
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }

}
