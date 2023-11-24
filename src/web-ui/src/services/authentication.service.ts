import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Store } from '@ngrx/store';
import { AppState } from '../state/app.state';
import {logout, setUser, setUserOnAppInit, signIn, signInMfa} from '../state/auth/auth.action';
import { selectUser } from '../state/auth/auth.selector';
import { UserProfile } from '../models/user-profile.model';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private store: Store<AppState>,
    private storageService: StorageService,
    private tokenService: TokenService
  ) {}

  signInDispatch(request: AuthenticationRequest) {
    this.store.dispatch(signIn(request));
  }

  signInMfaDispatch(request: MfaAuthenticationRequest) {
    this.store.dispatch(signInMfa(request));
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

  setUsername(username: string) {
    this.storageService.saveItem('username', username);
  }

  getUsername() {
    return this.storageService.getItem('username');
  }

  updateUser(user: UserProfile) {
    return this.store.dispatch(setUser({ user: user }));
  }

  getUser() {
    return this.store.select(selectUser);
  }

  getAuthorities() {
    return this.storageService.getItem('authorities') as unknown as string[];
  }

  logoutDispatch() {
    this.store.dispatch(logout());
    this.removeToken();
    this.removeAuthorities();
  }

  saveAuthorities(authorities: string[]) {
    this.storageService.saveItem('authorities', authorities);
  }

  removeAuthorities() {
    this.storageService.deleteItem('authorities');
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

export class ActivationRequest {
  token: string;

  constructor(token: string) {
    this.token = token;
  }
}

export class MfaAuthenticationRequest {
  code: string;
  challenge: string;

  constructor(code: string, challenge: string) {
    this.code = code;
    this.challenge = challenge;
  }
}
