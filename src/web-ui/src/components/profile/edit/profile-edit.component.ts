import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserProfile } from '../../../models/user-profile.model';
import { AuthenticationService } from '../../../services/authentication.service';
import { FormGroup } from '@angular/forms';
import { ProfileEditFormService } from './profile-edit.form.service';
import { Subscription } from 'rxjs';
import { UserProfileApiService } from '../../../services/api/user-profile-api.service';
import { ToastService } from '../../../services/toast.service';
import { LanguageService } from '../../../services/language.service';

@Component({
  selector: 'profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss'],
})
export class ProfileEditComponent implements OnInit, OnDestroy {
  userProfile: UserProfile | null = null;
  form: FormGroup;
  todayDate = new Date();
  subscription: Subscription = new Subscription();

  constructor(private formService: ProfileEditFormService, private authenticationService: AuthenticationService,
              private userProfileApiService: UserProfileApiService, private toastService: ToastService,
              private languageService: LanguageService) {
    this.form = this.formService.getForm();
  }

  ngOnInit() {
    this.setUser();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setUser() {
    this.subscription = this.authenticationService.getMyself().subscribe((userProfile) => {
      this.userProfile = userProfile;
      if (userProfile) {
        this.loadFormData(userProfile);
      }
    });
  }

  loadFormData(user: UserProfile) {
    this.formService.setForm(this.form, user);
  }

  update() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const request = this.formService.convertFormTouserProfile(this.form);
      this.userProfileApiService.saveProfile(request).subscribe({
        next: () => {
          this.toastService.createSuccessToast(this.languageService.getMessage('components.profile-edit.update.success'));
        },
        error: () => {
          this.toastService.createErrorToast(this.languageService.getMessage('components.profile-edit.update.error'));
        },
      });
    }
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }
}
