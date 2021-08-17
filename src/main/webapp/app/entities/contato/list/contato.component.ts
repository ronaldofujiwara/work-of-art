import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContato } from '../contato.model';
import { ContatoService } from '../service/contato.service';
import { ContatoDeleteDialogComponent } from '../delete/contato-delete-dialog.component';

@Component({
  selector: 'jhi-contato',
  templateUrl: './contato.component.html',
})
export class ContatoComponent implements OnInit {
  contatoes?: IContato[];
  isLoading = false;

  constructor(protected contatoService: ContatoService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.contatoService.query().subscribe(
      (res: HttpResponse<IContato[]>) => {
        this.isLoading = false;
        this.contatoes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IContato): number {
    return item.id!;
  }

  delete(contato: IContato): void {
    const modalRef = this.modalService.open(ContatoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contato = contato;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
