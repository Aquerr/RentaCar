import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../../models/user-profile.model';
import { APP_BASE_URL, APP_V1_URL } from '../../app/app.consts';
import { AuthenticationRequest } from '../authentication.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationApiService {
  private AUTH_URL = APP_BASE_URL + APP_V1_URL + '/auth';

  constructor(private http: HttpClient) {}

  public signinUser(request: AuthenticationRequest) {
    return this.http.post<JwtTokenResponse>(this.AUTH_URL, request);
  }

  public getMyself() {
    const url = this.AUTH_URL + '/myself';
    return this.http.get<UserProfile>(url);
  }

  public logout() {
    const url = this.AUTH_URL + '/invalidate';
    return this.http.post<void>(url, {});
  }
}

export interface JwtTokenResponse {
  jwt: string;
  username: string;
  authorities: string[];
}
