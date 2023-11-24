import { UserProfile } from '../../models/user-profile.model';

export interface AuthState {
  user: UserProfile | null;
}

export const initialState: AuthState = {
  user: null
};
