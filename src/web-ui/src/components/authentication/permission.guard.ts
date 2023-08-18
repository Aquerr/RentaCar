import { Injectable } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { filter, map } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { isEmpty } from 'lodash';

@Injectable()
export class PermissionGuard {
  constructor(private router: Router,
              private authenticationService: AuthenticationService) {}

  canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {
    //TODO WYMAGANE MIN 1 AUTHORITY DLA KAZDEGO USERA
    return this.authenticationService.getAuthorities().pipe(
      filter(authorities => !isEmpty(authorities)),
      map((authorities) => {
        if (authorities.includes(route.data['permission'])) {
          return true;
        }
        this.router.navigate(['']);
        return false;
      })
    );
  }
}
