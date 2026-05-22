import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Checkbox} from "primeng/checkbox";
import {FormFieldContainerComponent} from "../form-field-container/form-field-container.component";
import {FloatLabel} from "primeng/floatlabel";

@Component({
  selector: 'app-checkbox',
  templateUrl: './checkbox.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    Checkbox,
    FormFieldContainerComponent,
    FloatLabel
  ],
  styleUrl: './checkbox.component.scss'
})
export class CheckboxComponent extends FormFieldContainerComponent {
  public model: ModelSignal<boolean | null> = model<boolean | null>(null);
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
