import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAcervoAtual, AcervoAtual } from '../acervo-atual.model';
import { AcervoAtualService } from '../service/acervo-atual.service';

@Component({
  selector: 'jhi-acervo-atual-update',
  templateUrl: './acervo-atual-update.component.html',
})
export class AcervoAtualUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    acervoAtual: [null, [Validators.required, Validators.maxLength(50)]],
    inativo: [],
  });

  constructor(protected acervoAtualService: AcervoAtualService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acervoAtual }) => {
      this.updateForm(acervoAtual);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const acervoAtual = this.createFromForm();
    if (acervoAtual.id !== undefined) {
      this.subscribeToSaveResponse(this.acervoAtualService.update(acervoAtual));
    } else {
      this.subscribeToSaveResponse(this.acervoAtualService.create(acervoAtual));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcervoAtual>>): void {
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

  protected updateForm(acervoAtual: IAcervoAtual): void {
    this.editForm.patchValue({
      id: acervoAtual.id,
      acervoAtual: acervoAtual.acervoAtual,
      inativo: acervoAtual.inativo,
    });
  }

  protected createFromForm(): IAcervoAtual {
    return {
      ...new AcervoAtual(),
      id: this.editForm.get(['id'])!.value,
      acervoAtual: this.editForm.get(['acervoAtual'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
