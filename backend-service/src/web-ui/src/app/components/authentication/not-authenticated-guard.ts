import {inject} from '@angular/core';
import {TokenService} from '../../services/token.service';
import {CanActivateFn, GuardResult} from '@angular/router';
import {Observable} from "rxjs/internal/Observable";
import {of} from "rxjs";

export const notAuthenticatedGuard: CanActivateFn = (): Observable<GuardResult> => {
  const tokenService = inject(TokenService);
  return of(!tokenService.getToken());
}
