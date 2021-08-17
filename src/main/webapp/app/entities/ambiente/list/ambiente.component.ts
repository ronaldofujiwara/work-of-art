import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAmbiente } from '../ambiente.model';
import { AmbienteService } from '../service/ambiente.service';
import { AmbienteDeleteDialogComponent } from '../delete/ambiente-delete-dialog.component';

@Component({
  selector: 'jhi-ambiente',
  templateUrl: './ambiente.component.html',
})
export class AmbienteComponent implements OnInit {
  ambientes?: IAmbiente[];
  isLoading = false;

  constructor(protected ambienteService: AmbienteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ambienteService.query().subscribe(
      (res: HttpResponse<IAmbiente[]>) => {
        this.isLoading = false;
        this.ambientes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAmbiente): number {
    return item.id!;
  }

  delete(ambiente: IAmbiente): void {
    const modalRef = this.modalService.open(AmbienteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ambiente = ambiente;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
