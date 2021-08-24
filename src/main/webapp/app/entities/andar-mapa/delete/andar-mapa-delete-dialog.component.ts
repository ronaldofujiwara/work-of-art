import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAndarMapa } from '../andar-mapa.model';
import { AndarMapaService } from '../service/andar-mapa.service';

@Component({
  templateUrl: './andar-mapa-delete-dialog.component.html',
})
export class AndarMapaDeleteDialogComponent {
  andarMapa?: IAndarMapa;

  constructor(protected andarMapaService: AndarMapaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.andarMapaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
