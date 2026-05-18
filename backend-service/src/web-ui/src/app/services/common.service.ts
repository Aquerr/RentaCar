import { Injectable } from '@angular/core';
import { AppState } from '../state/app.state';
import { Store } from '@ngrx/store';
import { goRoute, showToast } from '../state/common/common.action';
import { ToastType } from './toast.service';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private store: Store<AppState>) {}

  showToast(messageKey: string, toastType: ToastType) {
    this.store.dispatch(showToast({ messageKey: messageKey, toastType: toastType }));
  }

  goRoute(routerPath: string) {
    this.store.dispatch(goRoute({ routingLink: routerPath }));
  }

}
