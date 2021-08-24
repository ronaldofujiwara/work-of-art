import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IResponsavel, Responsavel } from '../responsavel.model';
import { ResponsavelService } from '../service/responsavel.service';

@Component({
  selector: 'jhi-responsavel-update',
  templateUrl: './responsavel-update.component.html',
})
export class ResponsavelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(150)]],
    inativo: [],
  });

  constructor(protected responsavelService: ResponsavelService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavel }) => {
      this.updateForm(responsavel);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const responsavel = this.createFromForm();
    if (responsavel.id !== undefined) {
      this.subscribeToSaveResponse(this.responsavelService.update(responsavel));
    } else {
      this.subscribeToSaveResponse(this.responsavelService.create(responsavel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavel>>): void {
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

  protected updateForm(responsavel: IResponsavel): void {
    this.editForm.patchValue({
      id: responsavel.id,
      nome: responsavel.nome,
      inativo: responsavel.inativo,
    });
  }

  protected createFromForm(): IResponsavel {
    return {
      ...new Responsavel(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
