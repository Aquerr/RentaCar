import { createAction, props } from '@ngrx/store';
import { ToastType } from '../../services/toast.service';
import {Params} from "@angular/router";

export const showToast = createAction('Show toast', props<{
  messageKey: string | null,
  message?: string | null,
  toastType: ToastType
}>());
export const goRoute = createAction('Go route', props<{ routingLink: string, pathVariables?: string[], queryParams?: Params | null }>());
export const goBack = createAction('Go back');
