import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FormFieldContainerComponent} from "../form-field-container/form-field-container.component";
import {MultiSelect} from "primeng/multiselect";

@Component({
  selector: 'app-multi-select',
  templateUrl: './multi-select.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    FormFieldContainerComponent,
    MultiSelect
  ],
  styleUrl: './multi-select.component.scss'
})
export class MultiSelectComponent extends FormFieldContainerComponent {
  public model: ModelSignal<string | null> = model<string | null>(null);
  public displayType: InputSignal<string> = model<string>('chip');
  public selectionLimit: InputSignal<number | null> = model<number | null>(null);
  public options: InputSignal<any[]> = input<any[]>([]);
  public isReadonly: InputSignal<boolean> = input<boolean>(false);
  public valueChanged = output<string>();

  public onSelectChange(): void {
    if (!this.controlName()) {
      this.valueChanged.emit(this.model() as string);
    } else {
      //form
    }
  }


}
