import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {DatePicker} from "primeng/datepicker";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FormFieldContainerComponent} from "../form-field-container/form-field-container.component";

@Component({
  selector: 'app-date-picker',
  templateUrl: './date-picker.component.html',
  imports: [
    DatePicker,
    ReactiveFormsModule,
    FormsModule,
    FormFieldContainerComponent
  ],
  styleUrl: './date-picker.component.scss'
})
export class DatePickerComponent extends FormFieldContainerComponent {
  public model: ModelSignal<Date | null> = model<Date | null>(null);
  public minDate: InputSignal<Date | null> = input<Date | null>(null);
  public maxDate: InputSignal<Date | null> = input<Date | null>(null);
  public isReadonly: InputSignal<boolean> = input<boolean>(false);
  public dateChanged = output<Date>();

  public onDateChange(): void {
    if (!this.controlName()) {
      this.dateChanged.emit(this.model() as Date);
    } else {
      //form
    }
  }


}
