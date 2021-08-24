import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDadoDocumental } from '../dado-documental.model';

@Component({
  selector: 'jhi-dado-documental-detail',
  templateUrl: './dado-documental-detail.component.html',
})
export class DadoDocumentalDetailComponent implements OnInit {
  dadoDocumental: IDadoDocumental | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadoDocumental }) => {
      this.dadoDocumental = dadoDocumental;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
