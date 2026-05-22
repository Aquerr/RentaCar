import { Component } from '@angular/core';
import {InputText} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";


@Component({
  selector: 'contact',
  templateUrl: './contact.component.html',
  imports: [
    InputText,
    ReactiveFormsModule
  ],
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent {
}
