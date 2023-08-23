import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserProfile } from '../../../models/user-profile.model';
import { FormGroup } from '@angular/forms';
import { ProfileEditFormService } from './profile-edit.form.service';
import { Subscription } from 'rxjs';
import { UserProfileApiService } from '../../../services/api/user-profile-api.service';
import { ToastService, ToastType } from '../../../services/toast.service';
import { LanguageService } from '../../../services/language.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { ImageKind } from '../../../enums/image.kind.enum';

@Component({
  selector: 'profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit, OnDestroy {
  userProfile: UserProfile | null = null;
  form: FormGroup;
  todayDate = new Date();
  subscriptions: Subscription = new Subscription();
  iconUploaded: File | null = null;
  iconUrl = '';

  constructor(private formService: ProfileEditFormService,
              private authenticationService: AuthenticationService,
              private userProfileApiService: UserProfileApiService,
              private toastService: ToastService,
              private languageService: LanguageService) {
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
      if (userProfile) {
        this.loadFormData(userProfile);
        this.iconUrl = userProfile.iconUrl;
      }
    }));
  }

  loadFormData(user: UserProfile) {
    this.formService.setForm(this.form, user);
  }

  update() {
    const request = this.formService.convertFormToUserProfile(this.form);
    this.subscriptions.add(this.userProfileApiService.saveProfile(request, this.iconUploaded as File).subscribe({
      next: (userProfile) => {
        this.authenticationService.updateUser(userProfile);
        this.toastService.createToast(this.languageService.getMessage('components.profile-edit.toasts.update.success'), ToastType.SUCCESS);
        this.loadFormData(userProfile);
        this.iconUploaded = null;
      },
      error: () => this.toastService.createToast(this.languageService.getMessage('components.profile-edit.toasts.update.error'), ToastType.ERROR)
    }));
  }

  openFileBrowser() {
    const fileInput = document.getElementById('file-input') as HTMLElement;
    fileInput.click();
  }

  uploadFile(fileEvent: any) {
    const file = fileEvent.target.files[0] as File;
    this.iconUploaded = file;
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = ((event) => {
      this.iconUrl = event.target?.result as string;
    });
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }
}
