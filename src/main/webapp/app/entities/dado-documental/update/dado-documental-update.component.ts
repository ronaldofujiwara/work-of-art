import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDadoDocumental, DadoDocumental } from '../dado-documental.model';
import { DadoDocumentalService } from '../service/dado-documental.service';
import { ITipoDocumento } from 'app/entities/tipo-documento/tipo-documento.model';
import { TipoDocumentoService } from 'app/entities/tipo-documento/service/tipo-documento.service';

@Component({
  selector: 'jhi-dado-documental-update',
  templateUrl: './dado-documental-update.component.html',
})
export class DadoDocumentalUpdateComponent implements OnInit {
  isSaving = false;

  tipoDocumentosSharedCollection: ITipoDocumento[] = [];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.maxLength(50)]],
    emissor: [null, [Validators.maxLength(60)]],
    receptor: [null, [Validators.maxLength(30)]],
    obs: [null, [Validators.maxLength(200)]],
    transcricao: [null, [Validators.maxLength(64000)]],
    finalizado: [],
    genTranscricao: [],
    tipoDocumento: [],
  });

  constructor(
    protected dadoDocumentalService: DadoDocumentalService,
    protected tipoDocumentoService: TipoDocumentoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadoDocumental }) => {
      this.updateForm(dadoDocumental);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dadoDocumental = this.createFromForm();
    if (dadoDocumental.id !== undefined) {
      this.subscribeToSaveResponse(this.dadoDocumentalService.update(dadoDocumental));
    } else {
      this.subscribeToSaveResponse(this.dadoDocumentalService.create(dadoDocumental));
    }
  }

  trackTipoDocumentoById(index: number, item: ITipoDocumento): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDadoDocumental>>): void {
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

  protected updateForm(dadoDocumental: IDadoDocumental): void {
    this.editForm.patchValue({
      id: dadoDocumental.id,
      data: dadoDocumental.data,
      emissor: dadoDocumental.emissor,
      receptor: dadoDocumental.receptor,
      obs: dadoDocumental.obs,
      transcricao: dadoDocumental.transcricao,
      finalizado: dadoDocumental.finalizado,
      genTranscricao: dadoDocumental.genTranscricao,
      tipoDocumento: dadoDocumental.tipoDocumento,
    });

    this.tipoDocumentosSharedCollection = this.tipoDocumentoService.addTipoDocumentoToCollectionIfMissing(
      this.tipoDocumentosSharedCollection,
      dadoDocumental.tipoDocumento
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tipoDocumentoService
      .query()
      .pipe(map((res: HttpResponse<ITipoDocumento[]>) => res.body ?? []))
      .pipe(
        map((tipoDocumentos: ITipoDocumento[]) =>
          this.tipoDocumentoService.addTipoDocumentoToCollectionIfMissing(tipoDocumentos, this.editForm.get('tipoDocumento')!.value)
        )
      )
      .subscribe((tipoDocumentos: ITipoDocumento[]) => (this.tipoDocumentosSharedCollection = tipoDocumentos));
  }

  protected createFromForm(): IDadoDocumental {
    return {
      ...new DadoDocumental(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value,
      emissor: this.editForm.get(['emissor'])!.value,
      receptor: this.editForm.get(['receptor'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      transcricao: this.editForm.get(['transcricao'])!.value,
      finalizado: this.editForm.get(['finalizado'])!.value,
      genTranscricao: this.editForm.get(['genTranscricao'])!.value,
      tipoDocumento: this.editForm.get(['tipoDocumento'])!.value,
    };
  }
}
