import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContato, Contato } from '../contato.model';
import { ContatoService } from '../service/contato.service';
import { IAreaDepto } from 'app/entities/area-depto/area-depto.model';
import { AreaDeptoService } from 'app/entities/area-depto/service/area-depto.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';

@Component({
  selector: 'jhi-contato-update',
  templateUrl: './contato-update.component.html',
})
export class ContatoUpdateComponent implements OnInit {
  isSaving = false;

  areaDeptosSharedCollection: IAreaDepto[] = [];
  cidadesSharedCollection: ICidade[] = [];

  editForm = this.fb.group({
    id: [],
    nomeComp: [null, [Validators.required, Validators.maxLength(120)]],
    empresa: [null, [Validators.maxLength(150)]],
    funcao: [null, [Validators.maxLength(50)]],
    rg: [null, [Validators.maxLength(15)]],
    cpf: [null, [Validators.maxLength(15)]],
    infoContato: [null, [Validators.maxLength(200)]],
    endRua: [null, [Validators.maxLength(50)]],
    endNumero: [null, [Validators.maxLength(10)]],
    endBairro: [null, [Validators.maxLength(50)]],
    endComplemento: [null, [Validators.maxLength(30)]],
    endCep: [null, [Validators.maxLength(10)]],
    telefone: [null, [Validators.maxLength(50)]],
    fax: [null, [Validators.maxLength(50)]],
    celular: [null, [Validators.maxLength(30)]],
    email: [null, [Validators.maxLength(50)]],
    site: [null, [Validators.maxLength(50)]],
    observacoes: [null, [Validators.maxLength(200)]],
    dataAtualizacao: [],
    inativo: [],
    area: [],
    cidade: [],
  });

  constructor(
    protected contatoService: ContatoService,
    protected areaDeptoService: AreaDeptoService,
    protected cidadeService: CidadeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contato }) => {
      if (contato.id === undefined) {
        const today = dayjs().startOf('day');
        contato.dataAtualizacao = today;
      }

      this.updateForm(contato);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contato = this.createFromForm();
    if (contato.id !== undefined) {
      this.subscribeToSaveResponse(this.contatoService.update(contato));
    } else {
      this.subscribeToSaveResponse(this.contatoService.create(contato));
    }
  }

  trackAreaDeptoById(index: number, item: IAreaDepto): number {
    return item.id!;
  }

  trackCidadeById(index: number, item: ICidade): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContato>>): void {
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

  protected updateForm(contato: IContato): void {
    this.editForm.patchValue({
      id: contato.id,
      nomeComp: contato.nomeComp,
      empresa: contato.empresa,
      funcao: contato.funcao,
      rg: contato.rg,
      cpf: contato.cpf,
      infoContato: contato.infoContato,
      endRua: contato.endRua,
      endNumero: contato.endNumero,
      endBairro: contato.endBairro,
      endComplemento: contato.endComplemento,
      endCep: contato.endCep,
      telefone: contato.telefone,
      fax: contato.fax,
      celular: contato.celular,
      email: contato.email,
      site: contato.site,
      observacoes: contato.observacoes,
      dataAtualizacao: contato.dataAtualizacao ? contato.dataAtualizacao.format(DATE_TIME_FORMAT) : null,
      inativo: contato.inativo,
      area: contato.area,
      cidade: contato.cidade,
    });

    this.areaDeptosSharedCollection = this.areaDeptoService.addAreaDeptoToCollectionIfMissing(
      this.areaDeptosSharedCollection,
      contato.area
    );
    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing(this.cidadesSharedCollection, contato.cidade);
  }

  protected loadRelationshipsOptions(): void {
    this.areaDeptoService
      .query()
      .pipe(map((res: HttpResponse<IAreaDepto[]>) => res.body ?? []))
      .pipe(
        map((areaDeptos: IAreaDepto[]) =>
          this.areaDeptoService.addAreaDeptoToCollectionIfMissing(areaDeptos, this.editForm.get('area')!.value)
        )
      )
      .subscribe((areaDeptos: IAreaDepto[]) => (this.areaDeptosSharedCollection = areaDeptos));

    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing(cidades, this.editForm.get('cidade')!.value)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }

  protected createFromForm(): IContato {
    return {
      ...new Contato(),
      id: this.editForm.get(['id'])!.value,
      nomeComp: this.editForm.get(['nomeComp'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
      funcao: this.editForm.get(['funcao'])!.value,
      rg: this.editForm.get(['rg'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      infoContato: this.editForm.get(['infoContato'])!.value,
      endRua: this.editForm.get(['endRua'])!.value,
      endNumero: this.editForm.get(['endNumero'])!.value,
      endBairro: this.editForm.get(['endBairro'])!.value,
      endComplemento: this.editForm.get(['endComplemento'])!.value,
      endCep: this.editForm.get(['endCep'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      celular: this.editForm.get(['celular'])!.value,
      email: this.editForm.get(['email'])!.value,
      site: this.editForm.get(['site'])!.value,
      observacoes: this.editForm.get(['observacoes'])!.value,
      dataAtualizacao: this.editForm.get(['dataAtualizacao'])!.value
        ? dayjs(this.editForm.get(['dataAtualizacao'])!.value, DATE_TIME_FORMAT)
        : undefined,
      inativo: this.editForm.get(['inativo'])!.value,
      area: this.editForm.get(['area'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
    };
  }
}
