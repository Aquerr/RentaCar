import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {DatePicker} from "primeng/datepicker";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-date-picker',
  templateUrl: './date-picker.component.html',
  imports: [
    DatePicker,
    ReactiveFormsModule,
    FormsModule
  ],
  styleUrl: './date-picker.component.scss'
})
export class DatePickerComponent {
  public model: ModelSignal<Date | null> = model<Date | null>(null);
  public controlName: InputSignal<string | null> = input<string | null>(null);
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
