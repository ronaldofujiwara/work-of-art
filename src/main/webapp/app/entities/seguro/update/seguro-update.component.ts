import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISeguro, Seguro } from '../seguro.model';
import { SeguroService } from '../service/seguro.service';
import { IContato } from 'app/entities/contato/contato.model';
import { ContatoService } from 'app/entities/contato/service/contato.service';
import { IMoeda } from 'app/entities/moeda/moeda.model';
import { MoedaService } from 'app/entities/moeda/service/moeda.service';

@Component({
  selector: 'jhi-seguro-update',
  templateUrl: './seguro-update.component.html',
})
export class SeguroUpdateComponent implements OnInit {
  isSaving = false;

  contatoesSharedCollection: IContato[] = [];
  moedasSharedCollection: IMoeda[] = [];

  editForm = this.fb.group({
    id: [],
    apolice: [null, [Validators.maxLength(200)]],
    obsSeguro: [null, [Validators.maxLength(30)]],
    contratoProposta: [null, [Validators.maxLength(255)]],
    ciaSeguradora: [null, [Validators.maxLength(50)]],
    cnpjSeguradora: [null, [Validators.maxLength(255)]],
    susepSeguradora: [null, [Validators.maxLength(255)]],
    corretora: [null, [Validators.maxLength(50)]],
    cnpjCorretora: [null, [Validators.maxLength(255)]],
    susepCorretora: [null, [Validators.maxLength(255)]],
    dataInicio: [],
    dataVenc: [],
    inativo: [],
    premio: [null, [Validators.maxLength(255)]],
    txConversao: [],
    genStatusSeguro: [],
    dataAtualSeguro: [],
    contatoSeg: [],
    contatoCor: [],
    moeda: [],
  });

  constructor(
    protected seguroService: SeguroService,
    protected contatoService: ContatoService,
    protected moedaService: MoedaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seguro }) => {
      this.updateForm(seguro);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const seguro = this.createFromForm();
    if (seguro.id !== undefined) {
      this.subscribeToSaveResponse(this.seguroService.update(seguro));
    } else {
      this.subscribeToSaveResponse(this.seguroService.create(seguro));
    }
  }

  trackContatoById(index: number, item: IContato): number {
    return item.id!;
  }

  trackMoedaById(index: number, item: IMoeda): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeguro>>): void {
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

  protected updateForm(seguro: ISeguro): void {
    this.editForm.patchValue({
      id: seguro.id,
      apolice: seguro.apolice,
      obsSeguro: seguro.obsSeguro,
      contratoProposta: seguro.contratoProposta,
      ciaSeguradora: seguro.ciaSeguradora,
      cnpjSeguradora: seguro.cnpjSeguradora,
      susepSeguradora: seguro.susepSeguradora,
      corretora: seguro.corretora,
      cnpjCorretora: seguro.cnpjCorretora,
      susepCorretora: seguro.susepCorretora,
      dataInicio: seguro.dataInicio,
      dataVenc: seguro.dataVenc,
      inativo: seguro.inativo,
      premio: seguro.premio,
      txConversao: seguro.txConversao,
      genStatusSeguro: seguro.genStatusSeguro,
      dataAtualSeguro: seguro.dataAtualSeguro,
      contatoSeg: seguro.contatoSeg,
      contatoCor: seguro.contatoCor,
      moeda: seguro.moeda,
    });

    this.contatoesSharedCollection = this.contatoService.addContatoToCollectionIfMissing(
      this.contatoesSharedCollection,
      seguro.contatoSeg,
      seguro.contatoCor
    );
    this.moedasSharedCollection = this.moedaService.addMoedaToCollectionIfMissing(this.moedasSharedCollection, seguro.moeda);
  }

  protected loadRelationshipsOptions(): void {
    this.contatoService
      .query()
      .pipe(map((res: HttpResponse<IContato[]>) => res.body ?? []))
      .pipe(
        map((contatoes: IContato[]) =>
          this.contatoService.addContatoToCollectionIfMissing(
            contatoes,
            this.editForm.get('contatoSeg')!.value,
            this.editForm.get('contatoCor')!.value
          )
        )
      )
      .subscribe((contatoes: IContato[]) => (this.contatoesSharedCollection = contatoes));

    this.moedaService
      .query()
      .pipe(map((res: HttpResponse<IMoeda[]>) => res.body ?? []))
      .pipe(map((moedas: IMoeda[]) => this.moedaService.addMoedaToCollectionIfMissing(moedas, this.editForm.get('moeda')!.value)))
      .subscribe((moedas: IMoeda[]) => (this.moedasSharedCollection = moedas));
  }

  protected createFromForm(): ISeguro {
    return {
      ...new Seguro(),
      id: this.editForm.get(['id'])!.value,
      apolice: this.editForm.get(['apolice'])!.value,
      obsSeguro: this.editForm.get(['obsSeguro'])!.value,
      contratoProposta: this.editForm.get(['contratoProposta'])!.value,
      ciaSeguradora: this.editForm.get(['ciaSeguradora'])!.value,
      cnpjSeguradora: this.editForm.get(['cnpjSeguradora'])!.value,
      susepSeguradora: this.editForm.get(['susepSeguradora'])!.value,
      corretora: this.editForm.get(['corretora'])!.value,
      cnpjCorretora: this.editForm.get(['cnpjCorretora'])!.value,
      susepCorretora: this.editForm.get(['susepCorretora'])!.value,
      dataInicio: this.editForm.get(['dataInicio'])!.value,
      dataVenc: this.editForm.get(['dataVenc'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
      premio: this.editForm.get(['premio'])!.value,
      txConversao: this.editForm.get(['txConversao'])!.value,
      genStatusSeguro: this.editForm.get(['genStatusSeguro'])!.value,
      dataAtualSeguro: this.editForm.get(['dataAtualSeguro'])!.value,
      contatoSeg: this.editForm.get(['contatoSeg'])!.value,
      contatoCor: this.editForm.get(['contatoCor'])!.value,
      moeda: this.editForm.get(['moeda'])!.value,
    };
  }
}
