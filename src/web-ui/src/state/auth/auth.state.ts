import { UserProfile } from '../../models/user-profile.model';

export interface AuthState {
  user: UserProfile | null;
  authorities: string[];
}

export const initialState: AuthState = {
  user: null,
  authorities: []
};
