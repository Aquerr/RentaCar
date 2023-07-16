import { Injectable } from '@angular/core';
import {TokenService} from "../../services/api/token.service";

@Injectable()
export class AnonymousGuard {
  constructor(private tokenService: TokenService) {}

  canActivate() {
    const jwt = this.tokenService.getToken();
    return !jwt;
  }
}
