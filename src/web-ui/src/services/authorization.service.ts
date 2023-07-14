import { Injectable } from '@angular/core';
import { APP_BASE_URL } from '../app/app.service';
import { HttpClient } from '@angular/common/http';
import { User, UserSignInRequest } from '../models/user.model';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { LanguageService } from './language.service';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private AUTH_URL = APP_BASE_URL + '/api/v1/auth';

  constructor(
    private http: HttpClient,
    private router: Router,
    private toastService: ToastService,
    private userService: UserService,
    private languageService: LanguageService,
  ) {}

  public signinUser(request: UserSignInRequest) {
    this.http.post<string>(APP_BASE_URL + this.AUTH_URL, request).subscribe({
      next: () => {
        this.getUserLogged().subscribe({
          next: (user) => {
            this.userService.updateUserObservable(user);
            this.setLanguage(user);
            this.router
              .navigate([''])
              .then(() =>
                this.toastService.createSuccessToast('Zalogowano pomyślnie'),
              );
          },
          error: () => {
            this.toastService.createErrorToast('Logowanie nieudane');
          },
        });
      },
      error: () => {
        this.toastService.createErrorToast('Logowanie nieudane');
      },
    });
  }

  public getUserLogged() {
    return this.http.get<User>(`${APP_BASE_URL}/api/v1/user/logged`);
  }

  private setLanguage(user: User) {
    this.languageService.loadLanguage(user);
  }
}
