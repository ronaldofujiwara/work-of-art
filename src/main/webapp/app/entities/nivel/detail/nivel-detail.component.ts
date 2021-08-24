import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INivel } from '../nivel.model';

@Component({
  selector: 'jhi-nivel-detail',
  templateUrl: './nivel-detail.component.html',
})
export class NivelDetailComponent implements OnInit {
  nivel: INivel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nivel }) => {
      this.nivel = nivel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
