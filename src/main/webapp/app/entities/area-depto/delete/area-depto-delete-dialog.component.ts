import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAreaDepto } from '../area-depto.model';
import { AreaDeptoService } from '../service/area-depto.service';

@Component({
  templateUrl: './area-depto-delete-dialog.component.html',
})
export class AreaDeptoDeleteDialogComponent {
  areaDepto?: IAreaDepto;

  constructor(protected areaDeptoService: AreaDeptoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.areaDeptoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
