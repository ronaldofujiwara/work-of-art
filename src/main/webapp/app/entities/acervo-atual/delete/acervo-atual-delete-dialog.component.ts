import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAcervoAtual } from '../acervo-atual.model';
import { AcervoAtualService } from '../service/acervo-atual.service';

@Component({
  templateUrl: './acervo-atual-delete-dialog.component.html',
})
export class AcervoAtualDeleteDialogComponent {
  acervoAtual?: IAcervoAtual;

  constructor(protected acervoAtualService: AcervoAtualService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.acervoAtualService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
