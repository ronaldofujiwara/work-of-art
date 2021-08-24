import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IObra } from '../obra.model';
import { ObraService } from '../service/obra.service';

@Component({
  templateUrl: './obra-delete-dialog.component.html',
})
export class ObraDeleteDialogComponent {
  obra?: IObra;

  constructor(protected obraService: ObraService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.obraService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
