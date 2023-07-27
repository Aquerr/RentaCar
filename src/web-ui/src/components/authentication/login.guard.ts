import { Injectable } from '@angular/core';
import { TokenService } from '../../services/token.service';

@Injectable()
export class LoginGuard {
  constructor(private tokenService: TokenService) {}

  canActivate() {
    return this.tokenService.getToken();
  }
}
