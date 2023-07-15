import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  constructor(private messageService: MessageService) {}

  public createSuccessToast(message: string) {
    this.messageService.add({
      key: 'success',
      severity: 'success',
      summary: message,
    });
  }

  public createErrorToast(message: string) {
    this.messageService.add({
      key: 'error',
      severity: 'error',
      summary: message,
    });
  }

  public createWarnToast(message: string) {
    this.messageService.add({
      key: 'warn',
      severity: 'warn',
      summary: message,
    });
  }
}
