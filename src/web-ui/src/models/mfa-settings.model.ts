import { MfaType } from '../enums/mfa-type.enum';

export interface MfaSettings {
  mfaType: MfaType;
  active: boolean;
  activationDate: string;
}
