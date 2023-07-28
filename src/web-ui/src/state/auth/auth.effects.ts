import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, of, switchMap, tap } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { AuthenticationApiService } from '../../services/api/authentication-api.service';
import { getMyself, logout, logoutClearData, setUser, setUserOnAppInit, signIn, signInSaveData } from './auth.action';
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
          signInSaveData({ response: response, rememberMe: request.rememberMe }),
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
            actions.push(logoutClearData());
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
          showToast({ messageKey: 'services.log-out.success', toastType: ToastType.SUCCESS }),
          goRoute({ routingLink: '' }),
          logoutClearData()
        ]),
        catchError(() => of(showToast({ messageKey: 'services.log-out.error', toastType: ToastType.ERROR })))
      ))
    ));

  signInSaveData$ = createEffect(() =>
    this.actions$.pipe(
      ofType(signInSaveData),
      tap(({ response: response, rememberMe: rememberMe }) =>
        this.authenticationService.signInSaveData(response, rememberMe)
      )), { dispatch: false });

  logoutClearData$ = createEffect(() =>
    this.actions$.pipe(
      ofType(logoutClearData),
      tap(() =>
        this.authenticationService.logoutClearData()
      )), { dispatch: false });

  constructor(
    private actions$: Actions,
    private toastService: ToastService,
    private authenticationService: AuthenticationService,
    private apiService: AuthenticationApiService
  ) {}
}
