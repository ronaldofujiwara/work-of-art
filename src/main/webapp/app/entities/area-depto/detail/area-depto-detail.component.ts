import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAreaDepto } from '../area-depto.model';

@Component({
  selector: 'jhi-area-depto-detail',
  templateUrl: './area-depto-detail.component.html',
})
export class AreaDeptoDetailComponent implements OnInit {
  areaDepto: IAreaDepto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaDepto }) => {
      this.areaDepto = areaDepto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
