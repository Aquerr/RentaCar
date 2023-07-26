import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'profile-panel',
  templateUrl: './profile-panel.component.html',
  styleUrls: ['./profile-panel.component.scss'],
})
export class ProfilePanelComponent {

  @Output() logoutEmitter = new EventEmitter;

  emitLogoutRequest() {
    this.logoutEmitter.emit();
  }

}
