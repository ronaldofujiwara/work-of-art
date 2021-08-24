import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFuncaoArtista, FuncaoArtista } from '../funcao-artista.model';
import { FuncaoArtistaService } from '../service/funcao-artista.service';

@Component({
  selector: 'jhi-funcao-artista-update',
  templateUrl: './funcao-artista-update.component.html',
})
export class FuncaoArtistaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    funcaoArtista: [null, [Validators.required, Validators.maxLength(40)]],
    inativo: [],
  });

  constructor(protected funcaoArtistaService: FuncaoArtistaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ funcaoArtista }) => {
      this.updateForm(funcaoArtista);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const funcaoArtista = this.createFromForm();
    if (funcaoArtista.id !== undefined) {
      this.subscribeToSaveResponse(this.funcaoArtistaService.update(funcaoArtista));
    } else {
      this.subscribeToSaveResponse(this.funcaoArtistaService.create(funcaoArtista));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncaoArtista>>): void {
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

  protected updateForm(funcaoArtista: IFuncaoArtista): void {
    this.editForm.patchValue({
      id: funcaoArtista.id,
      funcaoArtista: funcaoArtista.funcaoArtista,
      inativo: funcaoArtista.inativo,
    });
  }

  protected createFromForm(): IFuncaoArtista {
    return {
      ...new FuncaoArtista(),
      id: this.editForm.get(['id'])!.value,
      funcaoArtista: this.editForm.get(['funcaoArtista'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
