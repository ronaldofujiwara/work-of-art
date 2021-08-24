import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFuncaoArtista } from '../funcao-artista.model';
import { FuncaoArtistaService } from '../service/funcao-artista.service';

@Component({
  templateUrl: './funcao-artista-delete-dialog.component.html',
})
export class FuncaoArtistaDeleteDialogComponent {
  funcaoArtista?: IFuncaoArtista;

  constructor(protected funcaoArtistaService: FuncaoArtistaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.funcaoArtistaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
