import {
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Input,
  OnChanges,
  OnDestroy,
  Output,
  SimpleChanges
} from '@angular/core';
import { UserProfile } from '../../../models/user-profile.model';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'profile-panel',
  templateUrl: './profile-panel.component.html',
  styleUrls: ['./profile-panel.component.scss']
})
export class ProfilePanelComponent implements OnChanges, OnDestroy {
  iconUrl = '';
  panelVisible = false;
  subscription: Subscription = new Subscription();
  @Input() user: UserProfile | null = null;
  @Output() logoutEmitter = new EventEmitter<void>;
  authorities: string[] = [];

  constructor(private eRef: ElementRef,
              private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['user'].currentValue) {
      this.getImage();
      this.getAuthorities();
    }
  }


  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getAuthorities() {
    this.authorities = this.authenticationService.getAuthorities();
  }

  @HostListener('document:click', ['$event'])
  clickOut(event: any) {
    if (this.eRef.nativeElement.contains(event.target)) {
    } else {
      this.panelVisible = false;
    }
  }

  toggleProfilePanel() {
    this.panelVisible = !this.panelVisible;
  }

  emitLogoutRequest() {
    this.logoutEmitter.emit();
    this.panelVisible = false;
  }

  navigateToProfile() {
    this.router.navigate(['profile/edit']).then(() => this.panelVisible = false);
  }

  navigateToAdminPanel() {
    this.router.navigate(['admin-panel']).then(() => this.panelVisible = false);
  }

  navigateToMyReservations() {
    //TODO @Bobus123 dodanie routingu i przekierowanie do nowego komponentu który bedzie pobieral rezerwacje użytkownika zalogowaneg z rest-a
    // (REST GOTOWY - klasa ReservationController)
    // Rozszerzenie klasy reservationApiService o nową metodę
  }

  getImage() {
    this.iconUrl = this.user?.iconUrl as string;
  }

  hasUserAuthority(authorityName: string) {
    return this.authorities.includes(authorityName);
  }
}
