import { createSelector } from '@ngrx/store';
import { AppState } from '../app.state';
import { state } from '../app.selector';

export const selectUser = createSelector(state, (state: AppState) => state.auth.user);
