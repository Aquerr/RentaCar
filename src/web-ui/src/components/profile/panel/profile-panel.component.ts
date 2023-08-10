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
import { ImageService, ImageType } from '../../../services/image.service';
import { Subscription } from 'rxjs';

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

  constructor(private eRef: ElementRef,
              private router: Router,
              private imageService: ImageService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['user'].currentValue) {
      this.getImage();
    }
  }


  ngOnDestroy() {
    this.subscription.unsubscribe();
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

  getImage() {
    this.iconUrl = this.user?.iconUrl as string;
  }
}
