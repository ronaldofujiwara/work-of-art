import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITecnica } from '../tecnica.model';
import { TecnicaService } from '../service/tecnica.service';

@Component({
  templateUrl: './tecnica-delete-dialog.component.html',
})
export class TecnicaDeleteDialogComponent {
  tecnica?: ITecnica;

  constructor(protected tecnicaService: TecnicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tecnicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
