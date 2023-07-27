import { Component, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { User } from '../../models/user.model';

@Component({
  selector: 'profile-panel',
  templateUrl: './profile-panel.component.html',
  styleUrls: ['./profile-panel.component.scss'],
})
export class ProfilePanelComponent {
  panelVisible = false;
  @Input() user: User | null = null;
  @Output() logoutEmitter = new EventEmitter<void>;

  constructor(private eRef: ElementRef) {
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

}
