import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoeda } from '../moeda.model';

@Component({
  selector: 'jhi-moeda-detail',
  templateUrl: './moeda-detail.component.html',
})
export class MoedaDetailComponent implements OnInit {
  moeda: IMoeda | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moeda }) => {
      this.moeda = moeda;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
