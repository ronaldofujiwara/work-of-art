import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAreaDepto, AreaDepto } from '../area-depto.model';
import { AreaDeptoService } from '../service/area-depto.service';

@Component({
  selector: 'jhi-area-depto-update',
  templateUrl: './area-depto-update.component.html',
})
export class AreaDeptoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    area: [null, [Validators.required, Validators.maxLength(100)]],
    inativo: [],
  });

  constructor(protected areaDeptoService: AreaDeptoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaDepto }) => {
      this.updateForm(areaDepto);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const areaDepto = this.createFromForm();
    if (areaDepto.id !== undefined) {
      this.subscribeToSaveResponse(this.areaDeptoService.update(areaDepto));
    } else {
      this.subscribeToSaveResponse(this.areaDeptoService.create(areaDepto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaDepto>>): void {
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

  protected updateForm(areaDepto: IAreaDepto): void {
    this.editForm.patchValue({
      id: areaDepto.id,
      area: areaDepto.area,
      inativo: areaDepto.inativo,
    });
  }

  protected createFromForm(): IAreaDepto {
    return {
      ...new AreaDepto(),
      id: this.editForm.get(['id'])!.value,
      area: this.editForm.get(['area'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
