import { Injectable } from '@angular/core';
import { User, UserSignInRequest } from '../models/user.model';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { LanguageService } from './language.service';
import { AuthenticationApiService } from './api/authentication-api.service';
import { Subject } from 'rxjs';
import {TokenService} from "./api/token.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private user = new Subject<User>();

  constructor(
    private authenticationApiService: AuthenticationApiService,
    private router: Router,
    private toastService: ToastService,
    private languageService: LanguageService,
    private tokenService: TokenService,
  ) {}

  public signinUser(request: UserSignInRequest) {
    this.authenticationApiService.signinUser(request).subscribe({
      next: (jwt) => {
        this.tokenService.saveToken(jwt, request.rememberMe);
        this.authenticationApiService.getUserLogged().subscribe({
          next: (user) => {
            this.updateUserObservable(user);
            this.setLanguage(user);
            this.router
              .navigate([''])
              .then(() =>
                this.toastService.createSuccessToast(
                  this.languageService.getMessage('services.sign-in.success'),
                ),
              );
          },
          error: () => {
            this.toastService.createErrorToast(
              this.languageService.getMessage('services.sign-in.error'),
            );
          },
        });
      },
      error: () => {
        this.toastService.createErrorToast(
          this.languageService.getMessage('services.sign-in.error'),
        );
      },
    });
  }

  public logout() {
    this.authenticationApiService.logout().subscribe({
      next: () => {
        this.updateUserObservable(null as unknown as User);
        this.tokenService.removeToken();
        this.toastService.createSuccessToast(
          this.languageService.getMessage('services.log-out.success'),
        );
      },
      error: () => {
        this.toastService.createErrorToast(
          this.languageService.getMessage('services.log-out.error'),
        );
      },
    });
  }

  public trySetUserOnAppInit() {
    this.tokenService.saveToken('jwt', true);
    const jwt = this.tokenService.getToken();
    if (jwt)
      this.authenticationApiService.getUserLogged().subscribe({
        next: (user) => {
          this.updateUserObservable(user);
        },
      });
  }

  getUserObservable() {
    return this.user;
  }

  updateUserObservable(user: User) {
    this.user.next(user);
  }

  private setLanguage(user: User) {
    this.languageService.loadLanguage(user);
  }
}
