import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../../services/common.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { AuthenticationApiService } from '../../../services/api/authentication-api.service';
import { ToastType } from '../../../services/toast.service';

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
      this.authenticationApiService.resetPassword(this.username).subscribe(
        {
          next: () => this.commonService.showToast('', ToastType.SUCCESS),
          error: () => this.commonService.showToast('', ToastType.ERROR)
        }
      );
    } else {
      this.commonService.showToast('', ToastType.WARN);
    }
  }
}
