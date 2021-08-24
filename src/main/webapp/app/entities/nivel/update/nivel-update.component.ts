import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INivel, Nivel } from '../nivel.model';
import { NivelService } from '../service/nivel.service';

@Component({
  selector: 'jhi-nivel-update',
  templateUrl: './nivel-update.component.html',
})
export class NivelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nivel: [null, [Validators.required, Validators.maxLength(250)]],
    inativo: [],
  });

  constructor(protected nivelService: NivelService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nivel }) => {
      this.updateForm(nivel);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nivel = this.createFromForm();
    if (nivel.id !== undefined) {
      this.subscribeToSaveResponse(this.nivelService.update(nivel));
    } else {
      this.subscribeToSaveResponse(this.nivelService.create(nivel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INivel>>): void {
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

  protected updateForm(nivel: INivel): void {
    this.editForm.patchValue({
      id: nivel.id,
      nivel: nivel.nivel,
      inativo: nivel.inativo,
    });
  }

  protected createFromForm(): INivel {
    return {
      ...new Nivel(),
      id: this.editForm.get(['id'])!.value,
      nivel: this.editForm.get(['nivel'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
