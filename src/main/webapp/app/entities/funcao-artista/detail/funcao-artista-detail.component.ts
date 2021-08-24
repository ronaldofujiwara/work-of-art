import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuncaoArtista } from '../funcao-artista.model';

@Component({
  selector: 'jhi-funcao-artista-detail',
  templateUrl: './funcao-artista-detail.component.html',
})
export class FuncaoArtistaDetailComponent implements OnInit {
  funcaoArtista: IFuncaoArtista | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcaoArtista }) => {
      this.funcaoArtista = funcaoArtista;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
