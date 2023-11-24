import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app.consts';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserProfile } from '../../models/user-profile.model';
import { MfaType } from '../../enums/mfa-type.enum';

@Injectable({
  providedIn: 'root'
})
export class UserProfileApiService {
  private URL = APP_BASE_URL + APP_V1_URL + '/profiles';

  constructor(private http: HttpClient) {
  }

  public saveProfile(user: UserProfile, image: File) {
    const formData = new FormData();
    if (image) {
      formData.append('image', image);
    }
    formData.append('profile', new Blob([JSON.stringify(user)], { type: 'application/json' }));
    return this.http.patch<UserProfile>(`${this.URL}/${user.id}`, formData);
  }

  public getProfile(profileId: number) {
    return this.http.get<UserProfile>(`${this.URL}/${profileId}`);
  }

  public generateQrCode(profileId: number, mfaType: MfaType) {
    const httpParams = new HttpParams().set('type', mfaType);
    return this.http.get<MfaTotpQrDataUriResponse>(`${this.URL}/${profileId}/settings/mfa/activation`, { params: httpParams });
  }

  public activateMfa(profileId: number, request: MfaActivationRequest) {
    return this.http.post<MfaActivationResponse>(`${this.URL}/${profileId}/settings/mfa/activation`, request);
  }

  public deleteMfa(profileId: number) {
    return this.http.delete<void>(`${this.URL}/${profileId}/settings/mfa`);
  }

  public getUserMfaSettings(profileId: number) {
    return this.http.get<MfaSettingsResponse>(`${this.URL}/${profileId}/mfa-settings`);
  }

  public getAvailableMfaTypes(profileId: number) {
    return this.http.get<MfaAvailableTypesResponse>(`${this.URL}/${profileId}/settings/mfa/available-types`);
  }

}

export interface MfaTotpQrDataUriResponse {
  qrDataUri: string;
}

export class MfaActivationRequest {
  code: string;

  constructor(code: string) {
    this.code = code;
  }
}

export interface MfaActivationResponse {
  recoveryCodes: string[];
}

export interface MfaSettingsResponse {
  mfaType: MfaType;
  verified: boolean;
  verifiedDate: string;
}

export interface MfaAvailableTypesResponse {
  mfaTypes: string[];
}
