import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ColorPicker} from "primeng/colorpicker";
import {FormFieldContainerComponent} from "../form-field-container/form-field-container.component";
import {FloatLabel} from "primeng/floatlabel";

@Component({
  selector: 'app-color-picker',
  templateUrl: './color-picker.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    ColorPicker,
    FormFieldContainerComponent,
    FloatLabel
  ],
  styleUrl: './color-picker.component.scss'
})
export class ColorPickerComponent extends FormFieldContainerComponent {
  public model: ModelSignal<string | undefined | null> = model<string | undefined | null >(null);
  public isReadonly: InputSignal<boolean> = input<boolean>(false);
  public colorChanged = output<string>();

  public onDateChange(): void {
    if (!this.controlName()) {
      this.colorChanged.emit(this.model() as string);
    } else {
      //form
    }
  }


}
