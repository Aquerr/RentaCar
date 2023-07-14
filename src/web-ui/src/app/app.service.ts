import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppService {}

export const APP_BASE_URL = environment.appBaseUrl;
