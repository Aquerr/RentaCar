import { createAction, props } from '@ngrx/store';
import { AuthenticationRequest } from '../../services/authentication.service';
import { UserProfile } from '../../models/user-profile.model';
import { JwtTokenResponse } from '../../services/api/authentication-api.service';

export const signIn = createAction('Sign in', props<AuthenticationRequest>());
export const signInSaveData = createAction('Sign in save data', props<{response: JwtTokenResponse, rememberMe: boolean}>());
export const getMyself = createAction('Get myself');
export const setUser = createAction('Set user', props<{ user: UserProfile | null }>());
export const setUserOnAppInit = createAction('Set user on app init');
export const logout = createAction('Log out');
export const logoutClearData = createAction('Log out clear data');
