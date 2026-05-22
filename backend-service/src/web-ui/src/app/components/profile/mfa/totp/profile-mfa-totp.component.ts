import { Component, Input, OnInit } from '@angular/core';
import { UserProfileApiService } from '../../../../services/api/user-profile-api.service';
import { MfaType } from '../../../../enums/mfa-type.enum';
import {FormGroup, ReactiveFormsModule} from '@angular/forms';
import {TranslatePipe} from "@ngx-translate/core";
import {InputComponent} from "../../../shared/input/input.component";

@Component({
  selector: 'profile-mfa-totp',
  templateUrl: './profile-mfa-totp.component.html',
  imports: [
    ReactiveFormsModule,
    TranslatePipe,
    InputComponent
  ],
  styleUrls: ['./profile-mfa-totp.component.scss']
})
export class ProfileMfaTotpComponent implements OnInit {

  @Input()
  userProfileId!: number;
  @Input()
  form!: FormGroup;
  qrCode: string | null = null;

  constructor(private apiService: UserProfileApiService) {}

  ngOnInit() {
    this.apiService.generateQrCode(this.userProfileId, MfaType.TOTP).subscribe({
      next: (qrCodeResponse) => this.qrCode = qrCodeResponse.qrDataUri,
      error: () => this.qrCode = null
    });
  }
}
