import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmbiente } from '../ambiente.model';

@Component({
  selector: 'jhi-ambiente-detail',
  templateUrl: './ambiente-detail.component.html',
})
export class AmbienteDetailComponent implements OnInit {
  ambiente: IAmbiente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ambiente }) => {
      this.ambiente = ambiente;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
