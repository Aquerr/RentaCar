import { Injectable } from '@angular/core';
import { JwtTokenResponse } from './api/authentication-api.service';
import { TokenService } from './token.service';
import { StorageService } from './storage.service';
import { Store } from '@ngrx/store';
import { AppState } from '../state/app.state';
import { logout, setUserOnAppInit, signIn } from '../state/auth/auth.action';
import { selectUser } from '../state/auth/auth.selector';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private store: Store<AppState>,
    private tokenService: TokenService,
    private storageService: StorageService
  ) {}

  signInDispatch(request: AuthenticationRequest) {
  this.store.dispatch(signIn(request));
  }

  signInSaveData(response: JwtTokenResponse, rememberMe: boolean) {
    this.tokenService.saveToken(response.jwt, rememberMe);
    this.saveAuthorities(response.authorities);
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

  logoutDispatch() {
    this.store.dispatch(logout());
    this.logoutClearData();
  }

  logoutClearData() {
    this.tokenService.removeToken();
    this.deleteAuthorities();
  }

  saveAuthorities(authorities: string[]) {
    this.storageService.saveItem('authorities', JSON.stringify(authorities));
  }

  deleteAuthorities() {
    this.storageService.deleteItem('authorities');
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
