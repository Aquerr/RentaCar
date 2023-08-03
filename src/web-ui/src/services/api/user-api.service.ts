import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app/app.consts';
import { HttpClient } from '@angular/common/http';
import { UserRegistrationRequest } from '../../components/authentication/sign-up/sign-up.component';

@Injectable({
  providedIn: 'root'
})
export class UserApiService {
  private URL = APP_BASE_URL + APP_V1_URL + '/users';

  constructor(private http: HttpClient) {
  }

  public register(request: UserRegistrationRequest) {
    return this.http.post<void>(`${this.URL}/register`, request);
  }

}
