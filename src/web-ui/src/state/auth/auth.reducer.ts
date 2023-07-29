import { createReducer, on } from '@ngrx/store';
import { setAuthorities, setUser } from './auth.action';
import { initialState } from './auth.state';

export const authReducer = createReducer(
  initialState,
  on(setUser, (state, { user }) => {
    return {
      ...state,
      user: user
    };
  }),
  on(setAuthorities, (state, { authorities }) => {
    return {
      ...state,
      authorities: authorities
    };
  })
);
