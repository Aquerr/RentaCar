import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputText} from "primeng/inputtext";
import {FormFieldContainerComponent} from "../form-field-container/form-field-container.component";

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    InputText,
    FormFieldContainerComponent
  ],
  styleUrl: './input.component.scss'
})
export class InputComponent extends FormFieldContainerComponent {
  public model: ModelSignal<string | null> = model<string | null>(null);
  public type: InputSignal<string> = input<string>('text');
  public placeholder: InputSignal<string> = input<string>('');
  public minLength: InputSignal<number | null> = input<number | null>(0);
  public maxLength: InputSignal<number | null> = input<number | null>(null);
  public isReadonly: InputSignal<boolean> = input<boolean>(false);
  public valueChanged = output<string>();

  public onChange(): void {
    if (!this.controlName()) {
      this.valueChanged.emit(this.model() as string);
    } else {
      //form
    }
  }


}
