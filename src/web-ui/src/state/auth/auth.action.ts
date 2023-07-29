import { createAction, props } from '@ngrx/store';
import { AuthenticationRequest } from '../../services/authentication.service';
import { UserProfile } from '../../models/user-profile.model';

export const signIn = createAction('Sign in', props<AuthenticationRequest>());
export const saveToken = createAction('Save token', props<{ jwt: string, rememberMe: boolean }>());
export const getMyself = createAction('Get myself');
export const setUser = createAction('Set user', props<{ user: UserProfile | null }>());
export const setUserOnAppInit = createAction('Set user on app init');
export const logout = createAction('Log out');
export const removeToken = createAction('Remove token');
export const setAuthorities = createAction('Set authorities', props<{ authorities: string[] }>());
