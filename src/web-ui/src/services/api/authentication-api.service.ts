import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User, UserSignInRequest } from '../../models/user.model';
import { APP_BASE_URL } from '../../app/app.consts';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationApiService {
  private AUTH_URL = APP_BASE_URL + '/api/v1/auth';
  private USER_URL = APP_BASE_URL + '/api/v1/user';

  constructor(private http: HttpClient) {}

  public signinUser(request: UserSignInRequest) {
    return this.http.post<string>(this.AUTH_URL, request);
  }

  public getUserLogged() {
    const url = this.USER_URL + '/logged';
    return this.http.get<User>(url);
  }

  public logout() {
    const url = this.USER_URL + '/logout';
    return this.http.get<void>(url);
  }
}
