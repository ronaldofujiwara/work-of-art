import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAndarMapa, AndarMapa } from '../andar-mapa.model';
import { AndarMapaService } from '../service/andar-mapa.service';
import { IEspaco } from 'app/entities/espaco/espaco.model';
import { EspacoService } from 'app/entities/espaco/service/espaco.service';

@Component({
  selector: 'jhi-andar-mapa-update',
  templateUrl: './andar-mapa-update.component.html',
})
export class AndarMapaUpdateComponent implements OnInit {
  isSaving = false;

  espacosSharedCollection: IEspaco[] = [];

  editForm = this.fb.group({
    id: [],
    imagemMapa: [null, [Validators.required, Validators.maxLength(255)]],
    espaco: [],
  });

  constructor(
    protected andarMapaService: AndarMapaService,
    protected espacoService: EspacoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ andarMapa }) => {
      this.updateForm(andarMapa);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const andarMapa = this.createFromForm();
    if (andarMapa.id !== undefined) {
      this.subscribeToSaveResponse(this.andarMapaService.update(andarMapa));
    } else {
      this.subscribeToSaveResponse(this.andarMapaService.create(andarMapa));
    }
  }

  trackEspacoById(index: number, item: IEspaco): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAndarMapa>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(andarMapa: IAndarMapa): void {
    this.editForm.patchValue({
      id: andarMapa.id,
      imagemMapa: andarMapa.imagemMapa,
      espaco: andarMapa.espaco,
    });

    this.espacosSharedCollection = this.espacoService.addEspacoToCollectionIfMissing(this.espacosSharedCollection, andarMapa.espaco);
  }

  protected loadRelationshipsOptions(): void {
    this.espacoService
      .query()
      .pipe(map((res: HttpResponse<IEspaco[]>) => res.body ?? []))
      .pipe(map((espacos: IEspaco[]) => this.espacoService.addEspacoToCollectionIfMissing(espacos, this.editForm.get('espaco')!.value)))
      .subscribe((espacos: IEspaco[]) => (this.espacosSharedCollection = espacos));
  }

  protected createFromForm(): IAndarMapa {
    return {
      ...new AndarMapa(),
      id: this.editForm.get(['id'])!.value,
      imagemMapa: this.editForm.get(['imagemMapa'])!.value,
      espaco: this.editForm.get(['espaco'])!.value,
    };
  }
}
