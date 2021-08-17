import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContato } from '../contato.model';
import { ContatoService } from '../service/contato.service';

@Component({
  templateUrl: './contato-delete-dialog.component.html',
})
export class ContatoDeleteDialogComponent {
  contato?: IContato;

  constructor(protected contatoService: ContatoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contatoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
