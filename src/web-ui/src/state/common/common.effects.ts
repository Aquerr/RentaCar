import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { goRoute, showToast } from './common.action';
import { ToastService } from '../../services/toast.service';
import { LanguageService } from '../../services/language.service';
import { Router } from '@angular/router';
import { tap } from 'rxjs';

@Injectable()
export class CommonEffects {

  showToast$ = createEffect(() =>
    this.actions$.pipe(
      ofType(showToast),
      tap(({ messageKey: messageKey, toastType: toastType }) =>
        this.toastService.createToast(this.getMessage(messageKey), toastType)
      )), { dispatch: false });

  goRoute$ = createEffect(() =>
    this.actions$.pipe(
      ofType(goRoute),
      tap((routerLink) =>
        this.router.navigate([routerLink.routingLink])
      )), { dispatch: false });

  constructor(
    private actions$: Actions,
    private router: Router,
    private toastService: ToastService,
    private languageService: LanguageService
  ) {}

  private getMessage(messageKey: string): string {
    return this.languageService.getMessage(messageKey);
  }

}
