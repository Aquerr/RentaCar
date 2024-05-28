import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../../models/user-profile.model';
import { APP_BASE_URL, APP_V1_URL } from '../../app.consts';
import {ActivationRequest, AuthenticationRequest, MfaAuthenticationRequest} from '../authentication.service';
import {Observable} from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationApiService {
  private AUTH_URL = APP_BASE_URL + APP_V1_URL + '/auth';

  constructor(private http: HttpClient) {}

  public signinUser(request: AuthenticationRequest) {
    return this.http.post<AuthResponse>(this.AUTH_URL, request);
  }

  signInMfaUser(request: MfaAuthenticationRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.AUTH_URL}/mfa`, request);
  }

  public activate(request: ActivationRequest) {
    return this.http.post<AuthResponse>(`${this.AUTH_URL}/activation`, request);
  }

  public resendActivationEmail(login: string) {
    return this.http.post<AuthResponse>(`${this.AUTH_URL}/resend-activation-email`, login);
  }

  public resetPassword(request: InitPasswordResetRequest) {
    return this.http.post<void>(`${this.AUTH_URL}/password-reset/init`, request);
  }

  public isTokenValid(token: string) {
    return this.http.get<boolean>(`${this.AUTH_URL}/password-reset/token/${token}/valid`);
  }

  public setNewPassword(request: PasswordResetRequest) {
    return this.http.post<void>(`${this.AUTH_URL}/password-reset`, request);
  }

  public getMyself() {
    const url = this.AUTH_URL + '/myself';
    return this.http.get<MyselfResponse>(url);
  }

  public logout() {
    const url = this.AUTH_URL + '/invalidate';
    return this.http.post<void>(url, {});
  }
}

export interface AuthResponse {
  jwt: string;
  username: string;
  authorities: string[];
  status: string;
  mfaChallenge: string;
}

export interface MyselfResponse {
  userProfile: UserProfile;
  authorities: string[];
}

export interface InitPasswordResetRequest {
  email: string
}

export interface PasswordResetRequest {
  token: string,
  password: string
}
