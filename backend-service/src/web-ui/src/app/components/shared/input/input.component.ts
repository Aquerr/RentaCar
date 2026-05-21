import {Component, input, InputSignal, model, ModelSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputText} from "primeng/inputtext";

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    InputText
  ],
  styleUrl: './input.component.scss'
})
export class InputComponent {
  public model: ModelSignal<string | null> = model<string | null>(null);
  public controlName: InputSignal<string | null> = input<string | null>(null);
  public type: InputSignal<string> = input<string>('text');
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
