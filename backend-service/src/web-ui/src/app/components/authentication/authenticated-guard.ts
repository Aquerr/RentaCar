import {inject} from '@angular/core';
import {TokenService} from '../../services/token.service';
import {CanActivateFn, GuardResult, MaybeAsync} from '@angular/router';
import {of} from "rxjs";

export const authenticatedGuard: CanActivateFn = (): MaybeAsync<GuardResult> => {
  const tokenService = inject(TokenService);
  return of(tokenService.getToken() !== null);
}
