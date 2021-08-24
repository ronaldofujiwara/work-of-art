import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IArtista, Artista } from '../artista.model';
import { ArtistaService } from '../service/artista.service';
import { IContato } from 'app/entities/contato/contato.model';
import { ContatoService } from 'app/entities/contato/service/contato.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IResponsavel } from 'app/entities/responsavel/responsavel.model';
import { ResponsavelService } from 'app/entities/responsavel/service/responsavel.service';
import { IFuncaoArtista } from 'app/entities/funcao-artista/funcao-artista.model';
import { FuncaoArtistaService } from 'app/entities/funcao-artista/service/funcao-artista.service';

@Component({
  selector: 'jhi-artista-update',
  templateUrl: './artista-update.component.html',
})
export class ArtistaUpdateComponent implements OnInit {
  isSaving = false;

  contatoesSharedCollection: IContato[] = [];
  cidadesSharedCollection: ICidade[] = [];
  responsavelsSharedCollection: IResponsavel[] = [];
  funcaoArtistasSharedCollection: IFuncaoArtista[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(255)]],
    dataNasc: [null, [Validators.maxLength(255)]],
    dataFalec: [null, [Validators.maxLength(255)]],
    biografia: [null, [Validators.maxLength(64000)]],
    verbete: [null, [Validators.maxLength(64000)]],
    dataAtualBio: [],
    dataAtualVerb: [],
    possuiBio: [],
    possuiVerb: [],
    fonteBio: [null, [Validators.maxLength(255)]],
    obrasNoAcervo: [null, [Validators.maxLength(255)]],
    inativo: [],
    agradecimentos: [null, [Validators.maxLength(64000)]],
    copyright: [null, [Validators.maxLength(64000)]],
    obsUso: [null, [Validators.maxLength(255)]],
    contatoes: [],
    cidadeNasc: [],
    cidadeFalesc: [],
    respVerbete: [],
    funcaoArtista: [],
  });

  constructor(
    protected artistaService: ArtistaService,
    protected contatoService: ContatoService,
    protected cidadeService: CidadeService,
    protected responsavelService: ResponsavelService,
    protected funcaoArtistaService: FuncaoArtistaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artista }) => {
      this.updateForm(artista);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const artista = this.createFromForm();
    if (artista.id !== undefined) {
      this.subscribeToSaveResponse(this.artistaService.update(artista));
    } else {
      this.subscribeToSaveResponse(this.artistaService.create(artista));
    }
  }

  trackContatoById(index: number, item: IContato): number {
    return item.id!;
  }

  trackCidadeById(index: number, item: ICidade): number {
    return item.id!;
  }

  trackResponsavelById(index: number, item: IResponsavel): number {
    return item.id!;
  }

  trackFuncaoArtistaById(index: number, item: IFuncaoArtista): number {
    return item.id!;
  }

  getSelectedContato(option: IContato, selectedVals?: IContato[]): IContato {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArtista>>): void {
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

  protected updateForm(artista: IArtista): void {
    this.editForm.patchValue({
      id: artista.id,
      nome: artista.nome,
      dataNasc: artista.dataNasc,
      dataFalec: artista.dataFalec,
      biografia: artista.biografia,
      verbete: artista.verbete,
      dataAtualBio: artista.dataAtualBio,
      dataAtualVerb: artista.dataAtualVerb,
      possuiBio: artista.possuiBio,
      possuiVerb: artista.possuiVerb,
      fonteBio: artista.fonteBio,
      obrasNoAcervo: artista.obrasNoAcervo,
      inativo: artista.inativo,
      agradecimentos: artista.agradecimentos,
      copyright: artista.copyright,
      obsUso: artista.obsUso,
      contatoes: artista.contatoes,
      cidadeNasc: artista.cidadeNasc,
      cidadeFalesc: artista.cidadeFalesc,
      respVerbete: artista.respVerbete,
      funcaoArtista: artista.funcaoArtista,
    });

    this.contatoesSharedCollection = this.contatoService.addContatoToCollectionIfMissing(
      this.contatoesSharedCollection,
      ...(artista.contatoes ?? [])
    );
    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing(
      this.cidadesSharedCollection,
      artista.cidadeNasc,
      artista.cidadeFalesc
    );
    this.responsavelsSharedCollection = this.responsavelService.addResponsavelToCollectionIfMissing(
      this.responsavelsSharedCollection,
      artista.respVerbete
    );
    this.funcaoArtistasSharedCollection = this.funcaoArtistaService.addFuncaoArtistaToCollectionIfMissing(
      this.funcaoArtistasSharedCollection,
      artista.funcaoArtista
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contatoService
      .query()
      .pipe(map((res: HttpResponse<IContato[]>) => res.body ?? []))
      .pipe(
        map((contatoes: IContato[]) =>
          this.contatoService.addContatoToCollectionIfMissing(contatoes, ...(this.editForm.get('contatoes')!.value ?? []))
        )
      )
      .subscribe((contatoes: IContato[]) => (this.contatoesSharedCollection = contatoes));

    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(
        map((cidades: ICidade[]) =>
          this.cidadeService.addCidadeToCollectionIfMissing(
            cidades,
            this.editForm.get('cidadeNasc')!.value,
            this.editForm.get('cidadeFalesc')!.value
          )
        )
      )
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));

    this.responsavelService
      .query()
      .pipe(map((res: HttpResponse<IResponsavel[]>) => res.body ?? []))
      .pipe(
        map((responsavels: IResponsavel[]) =>
          this.responsavelService.addResponsavelToCollectionIfMissing(responsavels, this.editForm.get('respVerbete')!.value)
        )
      )
      .subscribe((responsavels: IResponsavel[]) => (this.responsavelsSharedCollection = responsavels));

    this.funcaoArtistaService
      .query()
      .pipe(map((res: HttpResponse<IFuncaoArtista[]>) => res.body ?? []))
      .pipe(
        map((funcaoArtistas: IFuncaoArtista[]) =>
          this.funcaoArtistaService.addFuncaoArtistaToCollectionIfMissing(funcaoArtistas, this.editForm.get('funcaoArtista')!.value)
        )
      )
      .subscribe((funcaoArtistas: IFuncaoArtista[]) => (this.funcaoArtistasSharedCollection = funcaoArtistas));
  }

  protected createFromForm(): IArtista {
    return {
      ...new Artista(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      dataNasc: this.editForm.get(['dataNasc'])!.value,
      dataFalec: this.editForm.get(['dataFalec'])!.value,
      biografia: this.editForm.get(['biografia'])!.value,
      verbete: this.editForm.get(['verbete'])!.value,
      dataAtualBio: this.editForm.get(['dataAtualBio'])!.value,
      dataAtualVerb: this.editForm.get(['dataAtualVerb'])!.value,
      possuiBio: this.editForm.get(['possuiBio'])!.value,
      possuiVerb: this.editForm.get(['possuiVerb'])!.value,
      fonteBio: this.editForm.get(['fonteBio'])!.value,
      obrasNoAcervo: this.editForm.get(['obrasNoAcervo'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
      agradecimentos: this.editForm.get(['agradecimentos'])!.value,
      copyright: this.editForm.get(['copyright'])!.value,
      obsUso: this.editForm.get(['obsUso'])!.value,
      contatoes: this.editForm.get(['contatoes'])!.value,
      cidadeNasc: this.editForm.get(['cidadeNasc'])!.value,
      cidadeFalesc: this.editForm.get(['cidadeFalesc'])!.value,
      respVerbete: this.editForm.get(['respVerbete'])!.value,
      funcaoArtista: this.editForm.get(['funcaoArtista'])!.value,
    };
  }
}
