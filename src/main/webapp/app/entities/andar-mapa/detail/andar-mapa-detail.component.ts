import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAndarMapa } from '../andar-mapa.model';

@Component({
  selector: 'jhi-andar-mapa-detail',
  templateUrl: './andar-mapa-detail.component.html',
})
export class AndarMapaDetailComponent implements OnInit {
  andarMapa: IAndarMapa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ andarMapa }) => {
      this.andarMapa = andarMapa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
