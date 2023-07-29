import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, of, switchMap, tap } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { AuthenticationApiService } from '../../services/api/authentication-api.service';
import {
  getMyself,
  logout,
  removeToken,
  setAuthorities,
  setUser,
  setUserOnAppInit,
  signIn,
  saveToken
} from './auth.action';
import { ToastService, ToastType } from '../../services/toast.service';
import { AuthenticationRequest, AuthenticationService } from '../../services/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { goRoute, showToast } from '../common/common.action';

@Injectable()
export class AuthEffects {

  signInUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(signIn),
      mergeMap((request: AuthenticationRequest) => this.apiService.signinUser(request)
      .pipe(
        switchMap((response) => [
          saveToken({ jwt: response.jwt, rememberMe: request.rememberMe }),
          setAuthorities({ authorities: response.authorities }),
          getMyself()
        ]),
        catchError(() =>
          of(showToast({ messageKey: 'services.sign-in.error', toastType: ToastType.ERROR }))
        )
      ))
    ));

  getMySelf$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getMyself),
      mergeMap(() => this.apiService.getMyself()
      .pipe(
        mergeMap((response) => [
          setUser({ user: response }),
          goRoute({ routingLink: '' }),
          showToast({
            messageKey: 'services.sign-in.success',
            toastType: ToastType.SUCCESS
          })
        ]),
        catchError(() => of(showToast({ messageKey: 'services.sign-in.error', toastType: ToastType.ERROR }))
        )
      ))
    ));

  setUserOnAppInit$ = createEffect(() =>
    this.actions$.pipe(
      ofType(setUserOnAppInit),
      mergeMap(() => this.apiService.getMyself()
      .pipe(
        map((response) => setUser({ user: response })),
        catchError((error: HttpErrorResponse) => {
          const actions = [];
          if (error.status === 401) {
            actions.push(removeToken());
            actions.push(goRoute({ routingLink: '' }));
            actions.push(showToast({
              messageKey: 'services.token-expire',
              toastType: ToastType.WARN
            }));
          }
          return actions;
        })))
    ));

  logout$ = createEffect(() =>
    this.actions$.pipe(
      ofType(logout),
      mergeMap(() => this.apiService.logout()
      .pipe(
        switchMap(() => [
          setUser({ user: null }),
          setAuthorities({ authorities: [] }),
          showToast({ messageKey: 'services.log-out.success', toastType: ToastType.SUCCESS }),
          goRoute({ routingLink: '' }),
          removeToken()
        ]),
        catchError(() => of(showToast({ messageKey: 'services.log-out.error', toastType: ToastType.ERROR })))
      ))
    ));

  saveToken$ = createEffect(() =>
    this.actions$.pipe(
      ofType(saveToken),
      tap(({ jwt: jwt, rememberMe: rememberMe }) =>
        this.authenticationService.saveToken(jwt, rememberMe)
      )), { dispatch: false });

  removeToken$ = createEffect(() =>
    this.actions$.pipe(
      ofType(removeToken),
      tap(() =>
        this.authenticationService.removeToken()
      )), { dispatch: false });

  constructor(
    private actions$: Actions,
    private toastService: ToastService,
    private authenticationService: AuthenticationService,
    private apiService: AuthenticationApiService
  ) {}
}
