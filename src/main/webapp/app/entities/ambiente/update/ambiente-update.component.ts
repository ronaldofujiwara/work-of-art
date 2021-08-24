import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAmbiente, Ambiente } from '../ambiente.model';
import { AmbienteService } from '../service/ambiente.service';

@Component({
  selector: 'jhi-ambiente-update',
  templateUrl: './ambiente-update.component.html',
})
export class AmbienteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ambiente: [null, [Validators.required, Validators.maxLength(50)]],
    inativo: [],
  });

  constructor(protected ambienteService: AmbienteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ambiente }) => {
      this.updateForm(ambiente);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ambiente = this.createFromForm();
    if (ambiente.id !== undefined) {
      this.subscribeToSaveResponse(this.ambienteService.update(ambiente));
    } else {
      this.subscribeToSaveResponse(this.ambienteService.create(ambiente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmbiente>>): void {
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

  protected updateForm(ambiente: IAmbiente): void {
    this.editForm.patchValue({
      id: ambiente.id,
      ambiente: ambiente.ambiente,
      inativo: ambiente.inativo,
    });
  }

  protected createFromForm(): IAmbiente {
    return {
      ...new Ambiente(),
      id: this.editForm.get(['id'])!.value,
      ambiente: this.editForm.get(['ambiente'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
