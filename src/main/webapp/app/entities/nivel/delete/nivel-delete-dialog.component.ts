import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INivel } from '../nivel.model';
import { NivelService } from '../service/nivel.service';

@Component({
  templateUrl: './nivel-delete-dialog.component.html',
})
export class NivelDeleteDialogComponent {
  nivel?: INivel;

  constructor(protected nivelService: NivelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nivelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
