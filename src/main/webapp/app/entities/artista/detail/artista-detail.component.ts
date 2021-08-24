import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArtista } from '../artista.model';

@Component({
  selector: 'jhi-artista-detail',
  templateUrl: './artista-detail.component.html',
})
export class ArtistaDetailComponent implements OnInit {
  artista: IArtista | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artista }) => {
      this.artista = artista;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
