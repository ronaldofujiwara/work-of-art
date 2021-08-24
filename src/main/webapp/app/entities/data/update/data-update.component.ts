import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IData, Data } from '../data.model';
import { DataService } from '../service/data.service';

@Component({
  selector: 'jhi-data-update',
  templateUrl: './data-update.component.html',
})
export class DataUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required, Validators.maxLength(50)]],
    inativo: [],
  });

  constructor(protected dataService: DataService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ data }) => {
      this.updateForm(data);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const data = this.createFromForm();
    if (data.id !== undefined) {
      this.subscribeToSaveResponse(this.dataService.update(data));
    } else {
      this.subscribeToSaveResponse(this.dataService.create(data));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IData>>): void {
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

  protected updateForm(data: IData): void {
    this.editForm.patchValue({
      id: data.id,
      data: data.data,
      inativo: data.inativo,
    });
  }

  protected createFromForm(): IData {
    return {
      ...new Data(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
    };
  }
}
