import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserProfile } from '../../../models/user-profile.model';
import { FormGroup } from '@angular/forms';
import { ProfileMfaFormService } from './profile-mfa.form.service';
import { Subscription } from 'rxjs';
import { UserProfileApiService } from '../../../services/api/user-profile-api.service';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'profile-mfa',
  templateUrl: './profile-mfa.component.html',
  styleUrls: ['./profile-mfa.component.scss']
})
export class ProfileMfaComponent implements OnInit, OnDestroy {
  userProfile: UserProfile | null = null;
  form: FormGroup;
  subscriptions: Subscription = new Subscription();
  qrCode: string | null = null;

  constructor(private formService: ProfileMfaFormService,
              private authenticationService: AuthenticationService,
              private apiService: UserProfileApiService) {
    this.form = this.formService.getForm();
  }

  ngOnInit() {
    this.setUser();
    this.startActivateMfaSubscription();
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  setUser() {
    this.subscriptions.add(this.authenticationService.getUser().subscribe((userProfile) => this.userProfile = userProfile));
  }

  startActivateMfaSubscription() {
    this.subscriptions.add(this.formService.getActivateMfaControl(this.form).valueChanges.subscribe(
      {
        next: (value) => {
          if (value) {
            this.apiService.generateQrCode(this.userProfile?.id as number).subscribe({
              next: (qrCodeResponse) => this.qrCode = qrCodeResponse.qrDataUri,
              error: () => this.qrCode = null
            });
          } else {
            this.qrCode = null;
          }
        }
      }));
  }


  save() {
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }
}
