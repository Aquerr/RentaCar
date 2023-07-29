import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Store } from '@ngrx/store';
import { AppState } from '../state/app.state';
import { logout, setUserOnAppInit, signIn } from '../state/auth/auth.action';
import { selectAuthorities, selectUser } from '../state/auth/auth.selector';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private store: Store<AppState>,
    private tokenService: TokenService
  ) {}

  signInDispatch(request: AuthenticationRequest) {
  this.store.dispatch(signIn(request));
  }

  saveToken(jwt: string, rememberMe: boolean) {
    this.tokenService.saveToken(jwt, rememberMe);
  }

  setUserOnAppInitDispatch() {
    const token = this.tokenService.getToken();
    if (token) {
      this.store.dispatch(setUserOnAppInit());
    }
  }

  getUser() {
    return this.store.select(selectUser);
  }

  getAuthorities() {
    return this.store.select(selectAuthorities);
  }

  logoutDispatch() {
    this.store.dispatch(logout());
    this.removeToken();
  }

  removeToken() {
    this.tokenService.removeToken();
  }
}

export class AuthenticationRequest {
  login: string;
  password: string;
  rememberMe: boolean;

  constructor(login: string, password: string, rememberMe: boolean) {
    this.login = login;
    this.password = password;
    this.rememberMe = rememberMe;
  }
}
