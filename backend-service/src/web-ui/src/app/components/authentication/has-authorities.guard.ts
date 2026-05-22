import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {AuthenticationService} from "../../services/authentication.service";
import {isEmpty} from "lodash";
import {of} from "rxjs";

export function hasAuthoritiesGuard(authorityName: string): CanActivateFn {
  return () => {
    const router = inject(Router);
    const authenticationService = inject(AuthenticationService);
    const authorities: string[] = authenticationService.getAuthorities() as string[];
    if (isEmpty(authorities)) {
      routeToMainPage();
      return of(false);
    }
    if (authorities.includes(authorityName)) {
      return of(true);
    }
    routeToMainPage();
    return of(false);


    function routeToMainPage() {
      router.navigate(['']);
    }
  }
}
