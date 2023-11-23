import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserProfile } from '../../../models/user-profile.model';
import { FormGroup } from '@angular/forms';
import { ProfileMfaFormService } from './profile-mfa.form.service';
import { Subscription } from 'rxjs';
import { MfaActivationRequest, UserProfileApiService } from '../../../services/api/user-profile-api.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { MfaType } from '../../../enums/mfa-type.enum';
import { MfaSettings } from '../../../models/mfa-settings.model';
import { ToastService, ToastType } from '../../../services/toast.service';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'profile-mfa',
  templateUrl: './profile-mfa.component.html',
  styleUrls: ['./profile-mfa.component.scss']
})
export class ProfileMfaComponent implements OnInit, OnDestroy {
  userProfile: UserProfile | null = null;
  form: FormGroup;
  mfaSettings: MfaSettings | null = null;
  subscriptions: Subscription = new Subscription();
  mfaTypes = [
    { label: 'Aplikacja uwierzytelniająca', value: MfaType.TOTP }
  ];
  selectedMfaType: MfaType | null = null;

  constructor(private formService: ProfileMfaFormService,
              private authenticationService: AuthenticationService,
              private apiService: UserProfileApiService,
              private toastService: ToastService,
              private confirmationService: ConfirmationService) {
    this.form = this.formService.getForm();
  }

  ngOnInit() {
    this.setUser();
    this.startActivateMfaSubscription();
    this.startSelectedMfaTypeSubscription();
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  setUser() {
    this.subscriptions.add(this.authenticationService.getUser().subscribe((userProfile) => {
      this.userProfile = userProfile;
      this.getUserMfaSettings();
    }));
  }

  getUserMfaSettings() {
    this.apiService.getUserMfaSettings(this.userProfile?.id as number).subscribe({
      next: (userMfaSettings) => this.mfaSettings = userMfaSettings
    });
  }

  startActivateMfaSubscription() {
    this.subscriptions.add(this.formService.getActivateMfaControl(this.form).valueChanges.subscribe(
      {
        next: (value) => {
          const selectedMfaTypeControl = this.formService.getSelectedMfaTypeControl(this.form);
          if (value) {
            selectedMfaTypeControl.enable();
          } else {
            selectedMfaTypeControl.disable();
            selectedMfaTypeControl.setValue(null);
            this.selectedMfaType = null;
          }
        }
      }));
  }

  startSelectedMfaTypeSubscription() {
    this.subscriptions.add(this.formService.getSelectedMfaTypeControl(this.form).valueChanges.subscribe({
      next: (value) => {
        if (value && value === MfaType.TOTP) {
          this.selectedMfaType = MfaType.TOTP;
        } else {
          this.selectedMfaType = null;
        }
      }
    }));
  }

  getTotpForm() {
    return this.form.get('totp') as FormGroup;
  }


  save() {
    const request = new MfaActivationRequest(this.formService.getTotpCodeControl(this.form).value);
    this.apiService.activateMfa(this.userProfile?.id as number, request).subscribe({
      next: (response) => {
        console.log('activate mfa');
        // TODO: Display recovery codes
      },
      error: () => this.toastService.createToast('', ToastType.ERROR)
    });
  }

  deleteMfaConfirmDialog() {
    this.confirmationService.confirm({
      accept: () => this.deleteMfa()
    });
  }

  deleteMfa() {
    this.apiService.deleteMfa(this.userProfile?.id as number).subscribe({
      next: () => this.toastService.createToast('', ToastType.SUCCESS),
      error: () => this.toastService.createToast('', ToastType.ERROR)
    });
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }

  protected readonly MfaType = MfaType;
}
