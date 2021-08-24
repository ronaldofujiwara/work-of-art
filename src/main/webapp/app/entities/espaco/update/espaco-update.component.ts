import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEspaco, Espaco } from '../espaco.model';
import { EspacoService } from '../service/espaco.service';

@Component({
  selector: 'jhi-espaco-update',
  templateUrl: './espaco-update.component.html',
})
export class EspacoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    espaco: [null, [Validators.required, Validators.maxLength(100)]],
    inativo: [],
  });

  constructor(protected espacoService: EspacoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ espaco }) => {
      this.updateForm(espaco);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const espaco = this.createFromForm();
    if (espaco.id !== undefined) {
      this.subscribeToSaveResponse(this.espacoService.update(espaco));
    } else {
      this.subscribeToSaveResponse(this.espacoService.create(espaco));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEspaco>>): void {
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

  protected updateForm(espaco: IEspaco): void {
    this.editForm.patchValue({
      id: espaco.id,
      espaco: espaco.espaco,
      inativo: espaco.inativo,
    });
  }

  protected createFromForm(): IEspaco {
    return {
      ...new Espaco(),
      id: this.editForm.get(['id'])!.value,
      espaco: this.editForm.get(['espaco'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
