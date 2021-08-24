import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDadoDocumental } from '../dado-documental.model';
import { DadoDocumentalService } from '../service/dado-documental.service';

@Component({
  templateUrl: './dado-documental-delete-dialog.component.html',
})
export class DadoDocumentalDeleteDialogComponent {
  dadoDocumental?: IDadoDocumental;

  constructor(protected dadoDocumentalService: DadoDocumentalService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dadoDocumentalService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
