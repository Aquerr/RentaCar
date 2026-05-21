import { Component, Input } from '@angular/core';


@Component({
    selector: 'footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss'],
    standalone: false
})

export class FooterComponent {
  year: number = new Date().getFullYear();
  @Input()
  isMobile = false;
}
