import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app/app.consts';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../../models/user-profile.model';

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

  public generateQrCode(profileId: number) {
    return this.http.get<MfaTotpQrDataUriResponse>(`${this.URL}/${profileId}/settings/mfa/activation`);
  }

  public activateMfa(profileId: number, request: MfaActivationRequest) {
    return this.http.post<MfaTotpQrDataUriResponse>(`${this.URL}/${profileId}/settings/mfa/activation`, request);
  }

  public deleteMfa(profileId: number) {
    return this.http.delete<void>(`${this.URL}/${profileId}/settings/mfa`);
  }

}

export interface MfaTotpQrDataUriResponse {
  qrDataUri: string;
}

export interface MfaActivationRequest {
  code: string;
}

export interface MfaActivationResponse {
  recoveryCodes: string[];
}
