import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISeguro } from '../seguro.model';
import { SeguroService } from '../service/seguro.service';

@Component({
  templateUrl: './seguro-delete-dialog.component.html',
})
export class SeguroDeleteDialogComponent {
  seguro?: ISeguro;

  constructor(protected seguroService: SeguroService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.seguroService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
