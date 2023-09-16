import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule, DatePipe } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { MessagesModule } from 'primeng/messages';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CheckboxModule } from 'primeng/checkbox';
import { AppInterceptor } from '../components/authentication/app.interceptor';
import { TooltipModule } from 'primeng/tooltip';
import { BrowserModule } from '@angular/platform-browser';
import { TitleStrategy } from '@angular/router';
import { CustomPageTitleStrategy } from '../strategy/custom-page-title.strategy';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { provideEnvironmentNgxMask } from 'ngx-mask';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { DialogModule } from 'primeng/dialog';
import { DialogService } from 'primeng/dynamicdialog';
import { CarouselModule } from 'primeng/carousel';
import { AppGuard } from '../components/authentication/app.guard';
import { StepsModule } from 'primeng/steps';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

@NgModule({
  exports: [
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CommonModule,
    InputTextModule,
    ToastModule,
    MessagesModule,
    CheckboxModule,
    TooltipModule,
    BrowserModule,
    CalendarModule,
    DropdownModule,
    FontAwesomeModule,
    DialogModule,
    CarouselModule,
    StepsModule,
    ConfirmDialogModule
  ],
  providers: [
    MessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppInterceptor,
      multi: true
    },
    {
      provide: TitleStrategy,
      useClass: CustomPageTitleStrategy
    },
    AppGuard,
    DatePipe,
    DialogService,
    ConfirmationService,
    provideEnvironmentNgxMask()
  ]
})
export class SharedModule {
}
