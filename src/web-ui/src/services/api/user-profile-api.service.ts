import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app/app.consts';
import { HttpClient } from '@angular/common/http';
import { JwtTokenResponse } from './authentication-api.service';
import { UserProfile } from '../../models/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class UserProfileApiService {
  private URL = APP_BASE_URL + APP_V1_URL + '/profiles';

  constructor(private http: HttpClient) {
  }

  public saveProfile(request: UserProfile) {
    return this.http.patch<JwtTokenResponse>(`${this.URL}/${request.id}`, request);
  }

  public getProfile(profileId: number) {
    return this.http.get<UserProfile>(`${this.URL}/${profileId}`);
  }

}
