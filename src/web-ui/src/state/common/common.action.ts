import { createAction, props } from '@ngrx/store';
import { ToastType } from '../../services/toast.service';

export const showToast = createAction('Show toast', props<{messageKey: string, toastType: ToastType}>());
export const goRoute = createAction('Go route', props<{routingLink: string}>());