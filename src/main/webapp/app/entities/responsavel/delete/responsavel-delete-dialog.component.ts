import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResponsavel } from '../responsavel.model';
import { ResponsavelService } from '../service/responsavel.service';

@Component({
  templateUrl: './responsavel-delete-dialog.component.html',
})
export class ResponsavelDeleteDialogComponent {
  responsavel?: IResponsavel;

  constructor(protected responsavelService: ResponsavelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
