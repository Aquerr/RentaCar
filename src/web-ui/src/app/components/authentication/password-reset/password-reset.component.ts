import { Component } from '@angular/core';
import { AuthenticationApiService } from '../../../services/api/authentication-api.service';
import { AbstractControl, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CommonService } from '../../../services/common.service';
import { ToastType } from '../../../services/toast.service';


@Component({
  selector: 'password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent {
  form = new FormBuilder().group({
    email: new FormControl(null, [Validators.required])
  });

  constructor(private commonService: CommonService,
              private apiService: AuthenticationApiService) {}

  resetPassword() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.apiService.resetPassword({email: this.getLogin().value}).subscribe({
        next: () => {
          this.commonService.goRoute('');
          this.commonService.showToast('components.password-reset.toasts.success', ToastType.SUCCESS);
        },
        error: () => this.commonService.showToast('components.password-reset.toasts.error', ToastType.ERROR)
      });
    }
  }

  getLogin() {
    return this.form.get('email') as AbstractControl;
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }

}
