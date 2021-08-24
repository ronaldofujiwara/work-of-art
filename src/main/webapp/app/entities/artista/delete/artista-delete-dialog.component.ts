import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IArtista } from '../artista.model';
import { ArtistaService } from '../service/artista.service';

@Component({
  templateUrl: './artista-delete-dialog.component.html',
})
export class ArtistaDeleteDialogComponent {
  artista?: IArtista;

  constructor(protected artistaService: ArtistaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.artistaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
