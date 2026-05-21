import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Checkbox} from "primeng/checkbox";

@Component({
  selector: 'app-checkbox',
  templateUrl: './checkbox.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    Checkbox
  ],
  styleUrl: './checkbox.component.scss'
})
export class CheckboxComponent {
  public model: ModelSignal<boolean | null> = model<boolean | null>(null);
  public controlName: InputSignal<string | null> = input<string | null>(null);
  public type: InputSignal<string> = input<string>('text');
  public maxLength: InputSignal<number | null> = input<number | null>(null);
  public isReadonly: InputSignal<boolean> = input<boolean>(false);
  public valueChanged = output<boolean>();

  public onChange(): void {
    if (!this.controlName()) {
      this.valueChanged.emit(this.model() as boolean);
    } else {
      //form
    }
  }


}
