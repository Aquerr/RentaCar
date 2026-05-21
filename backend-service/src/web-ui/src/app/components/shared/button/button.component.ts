import {Component, input, InputSignal, output} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Tooltip} from "primeng/tooltip";

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    Tooltip
  ],
  styleUrl: './button.component.scss'
})
export class ButtonComponent {
  public label: InputSignal<string> = input.required<string>();
  public tooltip: InputSignal<string> = input<string>('');
  public isDisabled: InputSignal<boolean> = input<boolean>(false);
  public click = output<void>();

  public onClick(): void {
    this.click.emit();
  }


}
