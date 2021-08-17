import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAreaDepto } from '../area-depto.model';
import { AreaDeptoService } from '../service/area-depto.service';
import { AreaDeptoDeleteDialogComponent } from '../delete/area-depto-delete-dialog.component';

@Component({
  selector: 'jhi-area-depto',
  templateUrl: './area-depto.component.html',
})
export class AreaDeptoComponent implements OnInit {
  areaDeptos?: IAreaDepto[];
  isLoading = false;

  constructor(protected areaDeptoService: AreaDeptoService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.areaDeptoService.query().subscribe(
      (res: HttpResponse<IAreaDepto[]>) => {
        this.isLoading = false;
        this.areaDeptos = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAreaDepto): number {
    return item.id!;
  }

  delete(areaDepto: IAreaDepto): void {
    const modalRef = this.modalService.open(AreaDeptoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.areaDepto = areaDepto;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
