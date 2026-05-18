import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../../services/common.service';
import { AuthenticationService } from '../../../services/authentication.service';
import {AuthenticationApiService, InitPasswordResetRequest} from '../../../services/api/authentication-api.service';
import { ToastType } from '../../../services/toast.service';
import {map} from "rxjs";

@Component({
  selector: 'profile-security',
  templateUrl: './profile-security.component.html',
  styleUrls: ['./profile-security.component.scss']
})
export class ProfileSecurityComponent implements OnInit {
  username: string = '';

  constructor(private authenticationService: AuthenticationService,
              private authenticationApiService: AuthenticationApiService,
              private commonService: CommonService) {}

  ngOnInit() {
    this.username = this.authenticationService.getUsername()! ?? '';
  }

  changePassword() {
    if (this.username.length > 0) {
      this.authenticationService.getUser().pipe(
        map(user => user?.email),
        map(userEmail => ({email: userEmail} as InitPasswordResetRequest)),
        map(this.authenticationApiService.resetPassword))
        .subscribe({
          next: () => this.commonService.showToast('components.profile-security.toasts.passwordChangeSuccess', ToastType.SUCCESS),
          error: () => this.commonService.showToast('components.profile-security.toasts.passwordChangeError', ToastType.ERROR)
        });
    } else {
      this.commonService.showToast('components.profile-security.toasts.passwordChangeWarn', ToastType.WARN);
    }
  }
}
