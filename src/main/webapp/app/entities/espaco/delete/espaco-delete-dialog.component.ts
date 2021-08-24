import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEspaco } from '../espaco.model';
import { EspacoService } from '../service/espaco.service';

@Component({
  templateUrl: './espaco-delete-dialog.component.html',
})
export class EspacoDeleteDialogComponent {
  espaco?: IEspaco;

  constructor(protected espacoService: EspacoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.espacoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
