import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserProfile } from '../../../models/user-profile.model';
import { FormGroup } from '@angular/forms';
import { ProfileMfaFormService } from './profile-mfa.form.service';
import { Subscription } from 'rxjs';
import { MfaActivationRequest, UserProfileApiService } from '../../../services/api/user-profile-api.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { MfaSettings } from '../../../models/mfa-settings.model';
import { ToastService, ToastType } from '../../../services/toast.service';
import { ConfirmationService } from 'primeng/api';
import { CommonService } from '../../../services/common.service';

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
  availableMfaTypes: string[] = [];
  selectedMfaType: string | null = null;

  constructor(private formService: ProfileMfaFormService,
              private authenticationService: AuthenticationService,
              private apiService: UserProfileApiService,
              private toastService: ToastService,
              private confirmationService: ConfirmationService,
              private commonService: CommonService) {
    this.form = this.formService.getForm();
  }

  ngOnInit() {
    this.setUser();
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  setUser() {
    this.subscriptions.add(this.authenticationService.getUser().subscribe((userProfile) => {
      this.userProfile = userProfile;
      this.getUserMfaSettings();
      this.getAvailableUserMfaTypes();
    }));
  }

  getUserMfaSettings() {
    this.apiService.getUserMfaSettings(this.userProfile?.id as number).subscribe({
      next: (userMfaSettings) => this.mfaSettings = userMfaSettings
    });
  }

  getAvailableUserMfaTypes() {
    this.apiService.getAvailableMfaTypes(this.userProfile?.id as number).subscribe({
      next: (availableMfaTypes) => this.availableMfaTypes = availableMfaTypes.mfaTypes
    });
  }

  getTotpForm() {
    return this.form.get('totp') as FormGroup;
  }


  save() {
    const request = new MfaActivationRequest(this.formService.getTotpCodeControl(this.form).value);
    this.apiService.activateMfa(this.userProfile?.id as number, request).subscribe({
      next: (response) => {
        this.confirmationService.confirm(
          {
            key: 'recovery-codes',
            message: this.prepareRecoveryCodeMessage(response.recoveryCodes),
            accept: () => {
              this.selectedMfaType = null;
              this.getUserMfaSettings();
            }
          }
        );
      },
      error: () => this.toastService.createToast('', ToastType.ERROR)
    });
  }

  deleteMfaConfirmDialog() {
    this.confirmationService.confirm({
      accept: () => this.deleteMfa(),
      key: 'delete-mfa'
    });
  }

  openInfoMfaDialog() {
    this.confirmationService.confirm({
      message: 'Wybrane dwustopniowe uwierzytelnienie: ' + this.mfaSettings?.mfaType + '<br>Data aktywacji: ' + this.mfaSettings?.verifiedDate,
      key: 'mfa-info'
    });
  }

  deleteMfa() {
    this.apiService.deleteMfa(this.userProfile?.id as number).subscribe({
      next: () => {
        this.getUserMfaSettings();
        this.toastService.createToast('', ToastType.SUCCESS);
      },
      error: () => this.toastService.createToast('', ToastType.ERROR)
    });
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }

  prepareRecoveryCodeMessage(recoveryCodes: string[]): string {
    const message = recoveryCodes.join('<br>');
    return 'Zapisz poniższe kody bezpiczeństwa. Będą potrzebne w przypadku utraty dostępu do dwustopniowej weryfikacji:<br><br>' + message;
  }

}
