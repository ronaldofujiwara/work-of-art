import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMoeda, Moeda } from '../moeda.model';
import { MoedaService } from '../service/moeda.service';

@Component({
  selector: 'jhi-moeda-update',
  templateUrl: './moeda-update.component.html',
})
export class MoedaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipoMoeda: [null, [Validators.required, Validators.maxLength(15)]],
    inativo: [],
  });

  constructor(protected moedaService: MoedaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moeda }) => {
      this.updateForm(moeda);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moeda = this.createFromForm();
    if (moeda.id !== undefined) {
      this.subscribeToSaveResponse(this.moedaService.update(moeda));
    } else {
      this.subscribeToSaveResponse(this.moedaService.create(moeda));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoeda>>): void {
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

  protected updateForm(moeda: IMoeda): void {
    this.editForm.patchValue({
      id: moeda.id,
      tipoMoeda: moeda.tipoMoeda,
      inativo: moeda.inativo,
    });
  }

  protected createFromForm(): IMoeda {
    return {
      ...new Moeda(),
      id: this.editForm.get(['id'])!.value,
      tipoMoeda: this.editForm.get(['tipoMoeda'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
