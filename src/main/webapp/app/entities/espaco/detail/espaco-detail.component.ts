import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEspaco } from '../espaco.model';

@Component({
  selector: 'jhi-espaco-detail',
  templateUrl: './espaco-detail.component.html',
})
export class EspacoDetailComponent implements OnInit {
  espaco: IEspaco | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ espaco }) => {
      this.espaco = espaco;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
