import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  constructor(private messageService: MessageService) {}

  public createToast(message: string, type: ToastType) {
    this.messageService.add({
      key: type,
      severity: type,
      summary: message
    });
  }
}

export enum ToastType {
  SUCCESS = 'success',
  WARN = 'warn',
  ERROR = 'error'
}
