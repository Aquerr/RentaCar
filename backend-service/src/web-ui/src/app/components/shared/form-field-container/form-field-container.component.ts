import {Component, inject, input, InputSignal, OnInit, signal, WritableSignal} from "@angular/core";
import {AbstractControl, ControlContainer, FormGroup} from "@angular/forms";
import {FloatLabel} from "primeng/floatlabel";

@Component({
  selector: 'app-form-field-container',
  templateUrl: 'form-field-container.component.html',
  imports: [
    FloatLabel
  ],
  styleUrl: 'form-field-container.component.scss'
})
export class FormFieldContainerComponent implements OnInit {
  public formGroup: WritableSignal<FormGroup | any> = signal<FormGroup | any>(null);
  public label: InputSignal<string | null> = input<string | null>('');
  public controlName: InputSignal<string | null> = input<string | null>(null);
  protected control: AbstractControl | undefined;
  protected controlContainer = inject(ControlContainer, {optional: true, host: true});

  public ngOnInit(): void {
    if (this.controlName()) {
      this.control = this.controlContainer?.control?.get(this.controlName() as string) as AbstractControl;
      this.formGroup.set(this.controlContainer?.control as FormGroup);
    }
  }
}
