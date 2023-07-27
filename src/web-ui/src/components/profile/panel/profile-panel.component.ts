import { Component, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { UserProfile } from '../../../models/user-profile.model';
import { Router } from '@angular/router';

@Component({
  selector: 'profile-panel',
  templateUrl: './profile-panel.component.html',
  styleUrls: ['./profile-panel.component.scss'],
})
export class ProfilePanelComponent {
  panelVisible = false;
  @Input() user: UserProfile | null = null;
  @Output() logoutEmitter = new EventEmitter<void>;

  constructor(private eRef: ElementRef, private router: Router) {
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

  navigateToProfileUpdate() {
    this.router.navigate(['profile-edit']).then(() => this.panelVisible = false);
  }

}
