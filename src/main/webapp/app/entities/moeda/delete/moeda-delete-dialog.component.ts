import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoeda } from '../moeda.model';
import { MoedaService } from '../service/moeda.service';

@Component({
  templateUrl: './moeda-delete-dialog.component.html',
})
export class MoedaDeleteDialogComponent {
  moeda?: IMoeda;

  constructor(protected moedaService: MoedaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moedaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
