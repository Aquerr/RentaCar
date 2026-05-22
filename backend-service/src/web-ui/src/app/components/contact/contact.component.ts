import {Component} from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {InputComponent} from "../shared/input/input.component";
import {ButtonComponent} from "../shared/button/button.component";


@Component({
  selector: 'contact',
  templateUrl: './contact.component.html',
  imports: [
    ReactiveFormsModule,
    InputComponent,
    ButtonComponent
  ],
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent {
  public formGroup: FormGroup;

  constructor() {
    this.formGroup = new FormGroup({
      topic: new FormControl(null),
      email: new FormControl(null),
      message: new FormControl(null)
    })
  }
  public send(): void {
    //TODO
  }
}
