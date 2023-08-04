import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Store } from '@ngrx/store';
import { AppState } from '../state/app.state';
import { logout, setUser, setUserOnAppInit, signIn } from '../state/auth/auth.action';
import { selectAuthorities, selectUser } from '../state/auth/auth.selector';
import { UserProfile } from '../models/user-profile.model';

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

  updateUser(user: UserProfile) {
    return this.store.dispatch(setUser({ user: user }));
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

export class ActivationRequest {
  token: string;

  constructor(token: string) {
    this.token = token;
  }
}
