import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITecnica, Tecnica } from '../tecnica.model';
import { TecnicaService } from '../service/tecnica.service';

@Component({
  selector: 'jhi-tecnica-update',
  templateUrl: './tecnica-update.component.html',
})
export class TecnicaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tecnica: [null, [Validators.required, Validators.maxLength(150)]],
    inativo: [],
  });

  constructor(protected tecnicaService: TecnicaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tecnica }) => {
      this.updateForm(tecnica);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tecnica = this.createFromForm();
    if (tecnica.id !== undefined) {
      this.subscribeToSaveResponse(this.tecnicaService.update(tecnica));
    } else {
      this.subscribeToSaveResponse(this.tecnicaService.create(tecnica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITecnica>>): void {
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

  protected updateForm(tecnica: ITecnica): void {
    this.editForm.patchValue({
      id: tecnica.id,
      tecnica: tecnica.tecnica,
      inativo: tecnica.inativo,
    });
  }

  protected createFromForm(): ITecnica {
    return {
      ...new Tecnica(),
      id: this.editForm.get(['id'])!.value,
      tecnica: this.editForm.get(['tecnica'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
