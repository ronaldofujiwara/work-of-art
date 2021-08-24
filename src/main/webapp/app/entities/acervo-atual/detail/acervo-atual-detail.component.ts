import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcervoAtual } from '../acervo-atual.model';

@Component({
  selector: 'jhi-acervo-atual-detail',
  templateUrl: './acervo-atual-detail.component.html',
})
export class AcervoAtualDetailComponent implements OnInit {
  acervoAtual: IAcervoAtual | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acervoAtual }) => {
      this.acervoAtual = acervoAtual;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
