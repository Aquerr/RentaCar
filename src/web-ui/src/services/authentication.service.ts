import { Injectable } from '@angular/core';
import { User, UserSignInRequest } from '../models/user.model';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { LanguageService } from './language.service';
import { AuthenticationApiService } from './api/authentication-api.service';
import { Subject } from 'rxjs';
import {TokenService} from "./token.service";
import { StorageService } from './storage.service';
import { HttpErrorResponse } from '@angular/common/http';

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
    private storageService: StorageService
  ) {}

  public signinUser(request: UserSignInRequest) {
    this.authenticationApiService.signinUser(request).subscribe({
      next: (response) => {
        this.tokenService.saveToken(response.jwt, request.rememberMe);
        this.saveAuthorities(response.authorities);
        this.authenticationApiService.getMyself().subscribe({
          next: (user) => {
            this.updateUserObservable(user);
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
    const jwt = this.tokenService.getToken();
    if (jwt)
      this.authenticationApiService.getMyself().subscribe({
        next: (user) => {
          this.updateUserObservable(user);
        },
        error: (error: HttpErrorResponse) => {
          if (error.status === 401) {
            this.tokenService.removeToken();
            this.router.navigate(['']).then(() => this.toastService.createWarnToast(this.languageService.getMessage('services.token-expire')));
          }
        }
      });
  }

  getUserObservable() {
    return this.user;
  }

  updateUserObservable(user: User) {
    this.user.next(user);
  }

  saveAuthorities(authorities: string[]) {
    this.storageService.saveItem('authorities', JSON.stringify(authorities));
  }
}
