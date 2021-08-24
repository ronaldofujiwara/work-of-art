import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICidade, Cidade } from '../cidade.model';
import { CidadeService } from '../service/cidade.service';

@Component({
  selector: 'jhi-cidade-update',
  templateUrl: './cidade-update.component.html',
})
export class CidadeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cidade: [null, [Validators.required, Validators.maxLength(255)]],
    estado: [null, [Validators.maxLength(255)]],
    pais: [null, [Validators.maxLength(255)]],
    cidadeUFPais: [null, [Validators.maxLength(255)]],
    estadoPais: [null, [Validators.maxLength(255)]],
    inativo: [],
  });

  constructor(protected cidadeService: CidadeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cidade }) => {
      this.updateForm(cidade);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cidade = this.createFromForm();
    if (cidade.id !== undefined) {
      this.subscribeToSaveResponse(this.cidadeService.update(cidade));
    } else {
      this.subscribeToSaveResponse(this.cidadeService.create(cidade));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICidade>>): void {
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

  protected updateForm(cidade: ICidade): void {
    this.editForm.patchValue({
      id: cidade.id,
      cidade: cidade.cidade,
      estado: cidade.estado,
      pais: cidade.pais,
      cidadeUFPais: cidade.cidadeUFPais,
      estadoPais: cidade.estadoPais,
      inativo: cidade.inativo,
    });
  }

  protected createFromForm(): ICidade {
    return {
      ...new Cidade(),
      id: this.editForm.get(['id'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      cidadeUFPais: this.editForm.get(['cidadeUFPais'])!.value,
      estadoPais: this.editForm.get(['estadoPais'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
