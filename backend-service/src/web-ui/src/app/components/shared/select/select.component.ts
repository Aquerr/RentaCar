import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Select} from "primeng/select";

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    Select
  ],
  styleUrl: './select.component.scss'
})
export class SelectComponent {
  public model: ModelSignal<string | null> = model<string | null>(null);
  public controlName: InputSignal<string | null> = input<string | null>(null);
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
