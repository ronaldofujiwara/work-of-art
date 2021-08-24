import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeguro } from '../seguro.model';

@Component({
  selector: 'jhi-seguro-detail',
  templateUrl: './seguro-detail.component.html',
})
export class SeguroDetailComponent implements OnInit {
  seguro: ISeguro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seguro }) => {
      this.seguro = seguro;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
