import { createReducer, on } from '@ngrx/store';
import { setUser } from './auth.action';
import { initialState } from './auth.state';

export const authReducer = createReducer(
  initialState,
  on(setUser, (state, { user }) => {
    return {
      ...state,
      user: user
    };
  })
);
