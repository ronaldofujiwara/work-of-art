import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAmbiente } from '../ambiente.model';
import { AmbienteService } from '../service/ambiente.service';

@Component({
  templateUrl: './ambiente-delete-dialog.component.html',
})
export class AmbienteDeleteDialogComponent {
  ambiente?: IAmbiente;

  constructor(protected ambienteService: AmbienteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ambienteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
