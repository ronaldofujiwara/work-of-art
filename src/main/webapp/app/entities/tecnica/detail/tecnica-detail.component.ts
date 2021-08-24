import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITecnica } from '../tecnica.model';

@Component({
  selector: 'jhi-tecnica-detail',
  templateUrl: './tecnica-detail.component.html',
})
export class TecnicaDetailComponent implements OnInit {
  tecnica: ITecnica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tecnica }) => {
      this.tecnica = tecnica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
