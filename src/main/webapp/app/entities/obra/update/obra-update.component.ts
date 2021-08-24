import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IObra, Obra } from '../obra.model';
import { ObraService } from '../service/obra.service';
import { IDadoDocumental } from 'app/entities/dado-documental/dado-documental.model';
import { DadoDocumentalService } from 'app/entities/dado-documental/service/dado-documental.service';
import { IArtista } from 'app/entities/artista/artista.model';
import { ArtistaService } from 'app/entities/artista/service/artista.service';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { CategoriaService } from 'app/entities/categoria/service/categoria.service';
import { ITecnica } from 'app/entities/tecnica/tecnica.model';
import { TecnicaService } from 'app/entities/tecnica/service/tecnica.service';
import { INivel } from 'app/entities/nivel/nivel.model';
import { NivelService } from 'app/entities/nivel/service/nivel.service';
import { IData } from 'app/entities/data/data.model';
import { DataService } from 'app/entities/data/service/data.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IMoeda } from 'app/entities/moeda/moeda.model';
import { MoedaService } from 'app/entities/moeda/service/moeda.service';
import { ISeguro } from 'app/entities/seguro/seguro.model';
import { SeguroService } from 'app/entities/seguro/service/seguro.service';
import { IResponsavel } from 'app/entities/responsavel/responsavel.model';
import { ResponsavelService } from 'app/entities/responsavel/service/responsavel.service';
import { IAcervoAtual } from 'app/entities/acervo-atual/acervo-atual.model';
import { AcervoAtualService } from 'app/entities/acervo-atual/service/acervo-atual.service';
import { IContato } from 'app/entities/contato/contato.model';
import { ContatoService } from 'app/entities/contato/service/contato.service';
import { IAndarMapa } from 'app/entities/andar-mapa/andar-mapa.model';
import { AndarMapaService } from 'app/entities/andar-mapa/service/andar-mapa.service';

@Component({
  selector: 'jhi-obra-update',
  templateUrl: './obra-update.component.html',
})
export class ObraUpdateComponent implements OnInit {
  isSaving = false;

  dadoDocumentalsSharedCollection: IDadoDocumental[] = [];
  artistasSharedCollection: IArtista[] = [];
  categoriasSharedCollection: ICategoria[] = [];
  tecnicasSharedCollection: ITecnica[] = [];
  nivelsSharedCollection: INivel[] = [];
  dataSharedCollection: IData[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  moedasSharedCollection: IMoeda[] = [];
  segurosSharedCollection: ISeguro[] = [];
  responsavelsSharedCollection: IResponsavel[] = [];
  acervoAtualsSharedCollection: IAcervoAtual[] = [];
  contatoesSharedCollection: IContato[] = [];
  andarMapasSharedCollection: IAndarMapa[] = [];

  editForm = this.fb.group({
    id: [],
    tombo: [null, [Validators.required, Validators.maxLength(8)]],
    multiplo: [null, [Validators.maxLength(1)]],
    numeroRegistro: [null, [Validators.maxLength(50)]],
    outrosTitulos: [null, [Validators.maxLength(200)]],
    tituloOriginal: [null, [Validators.maxLength(200)]],
    origem: [null, [Validators.maxLength(100)]],
    descricao: [null, [Validators.maxLength(64000)]],
    assinatura: [],
    localAssinatura: [null, [Validators.maxLength(64000)]],
    marcaInscrObra: [null, [Validators.maxLength(64000)]],
    marcaInscrSuporte: [null, [Validators.maxLength(64000)]],
    medidasAprox: [],
    alturaObra: [],
    largObra: [],
    profObra: [],
    diametrObra: [null, [Validators.maxLength(6)]],
    alturaMold: [],
    largMold: [],
    profMold: [],
    diametroMold: [null, [Validators.maxLength(10)]],
    dimensAdic: [null, [Validators.maxLength(150)]],
    pesObra: [null, [Validators.maxLength(10)]],
    atribuido: [],
    nFoto: [null, [Validators.maxLength(10)]],
    conjunto: [],
    conjTombo: [null, [Validators.maxLength(100)]],
    valorSeguro: [],
    valorSeguroReal: [],
    dataconversao: [],
    dataAlterApolice: [],
    localAtual: [null, [Validators.maxLength(100)]],
    localAtualNovo: [null, [Validators.maxLength(200)]],
    codParametro: [null, [Validators.maxLength(50)]],
    obs: [null, [Validators.maxLength(64000)]],
    tiragem: [null, [Validators.maxLength(64000)]],
    serie: [null, [Validators.maxLength(200)]],
    selo: [],
    tomboAnterio: [null, [Validators.maxLength(50)]],
    verbete: [null, [Validators.maxLength(64000)]],
    livro: [],
    imagemAlta: [],
    apabex: [],
    bunkyo: [],
    faseDepuracao: [null, [Validators.maxLength(255)]],
    paraAvaliacao: [],
    paraRestauracao: [],
    paraMoldura: [],
    paraLegenda: [],
    tempField: [],
    selecaoListaAvulsa: [],
    dominioPubl: [],
    dtVencFoto: [],
    obsUsoFoto: [null, [Validators.maxLength(255)]],
    localFotoAlta: [null, [Validators.maxLength(255)]],
    dataInclusao: [],
    dataExclusao: [],
    bookX: [],
    bookY: [],
    genDescricao: [],
    genField1: [],
    paraFotografia: [],
    genMarcaInscrObra: [],
    palavrasChave: [null, [Validators.maxLength(64000)]],
    genMarcaInscrSuporte: [],
    genObs: [],
    genVerbete: [],
    dadoDocumentals: [],
    artista: [],
    categoria: [],
    tecnica: [],
    nivel: [],
    data: [],
    empresa: [],
    moeda: [],
    seguro: [],
    responsavel: [],
    acervoatual: [],
    fotografo: [],
    andarMapa: [],
  });

  constructor(
    protected obraService: ObraService,
    protected dadoDocumentalService: DadoDocumentalService,
    protected artistaService: ArtistaService,
    protected categoriaService: CategoriaService,
    protected tecnicaService: TecnicaService,
    protected nivelService: NivelService,
    protected dataService: DataService,
    protected empresaService: EmpresaService,
    protected moedaService: MoedaService,
    protected seguroService: SeguroService,
    protected responsavelService: ResponsavelService,
    protected acervoAtualService: AcervoAtualService,
    protected contatoService: ContatoService,
    protected andarMapaService: AndarMapaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ obra }) => {
      this.updateForm(obra);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const obra = this.createFromForm();
    if (obra.id !== undefined) {
      this.subscribeToSaveResponse(this.obraService.update(obra));
    } else {
      this.subscribeToSaveResponse(this.obraService.create(obra));
    }
  }

  trackDadoDocumentalById(index: number, item: IDadoDocumental): number {
    return item.id!;
  }

  trackArtistaById(index: number, item: IArtista): number {
    return item.id!;
  }

  trackCategoriaById(index: number, item: ICategoria): number {
    return item.id!;
  }

  trackTecnicaById(index: number, item: ITecnica): number {
    return item.id!;
  }

  trackNivelById(index: number, item: INivel): number {
    return item.id!;
  }

  trackDataById(index: number, item: IData): number {
    return item.id!;
  }

  trackEmpresaById(index: number, item: IEmpresa): number {
    return item.id!;
  }

  trackMoedaById(index: number, item: IMoeda): number {
    return item.id!;
  }

  trackSeguroById(index: number, item: ISeguro): number {
    return item.id!;
  }

  trackResponsavelById(index: number, item: IResponsavel): number {
    return item.id!;
  }

  trackAcervoAtualById(index: number, item: IAcervoAtual): number {
    return item.id!;
  }

  trackContatoById(index: number, item: IContato): number {
    return item.id!;
  }

  trackAndarMapaById(index: number, item: IAndarMapa): number {
    return item.id!;
  }

  getSelectedDadoDocumental(option: IDadoDocumental, selectedVals?: IDadoDocumental[]): IDadoDocumental {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObra>>): void {
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

  protected updateForm(obra: IObra): void {
    this.editForm.patchValue({
      id: obra.id,
      tombo: obra.tombo,
      multiplo: obra.multiplo,
      numeroRegistro: obra.numeroRegistro,
      outrosTitulos: obra.outrosTitulos,
      tituloOriginal: obra.tituloOriginal,
      origem: obra.origem,
      descricao: obra.descricao,
      assinatura: obra.assinatura,
      localAssinatura: obra.localAssinatura,
      marcaInscrObra: obra.marcaInscrObra,
      marcaInscrSuporte: obra.marcaInscrSuporte,
      medidasAprox: obra.medidasAprox,
      alturaObra: obra.alturaObra,
      largObra: obra.largObra,
      profObra: obra.profObra,
      diametrObra: obra.diametrObra,
      alturaMold: obra.alturaMold,
      largMold: obra.largMold,
      profMold: obra.profMold,
      diametroMold: obra.diametroMold,
      dimensAdic: obra.dimensAdic,
      pesObra: obra.pesObra,
      atribuido: obra.atribuido,
      nFoto: obra.nFoto,
      conjunto: obra.conjunto,
      conjTombo: obra.conjTombo,
      valorSeguro: obra.valorSeguro,
      valorSeguroReal: obra.valorSeguroReal,
      dataconversao: obra.dataconversao,
      dataAlterApolice: obra.dataAlterApolice,
      localAtual: obra.localAtual,
      localAtualNovo: obra.localAtualNovo,
      codParametro: obra.codParametro,
      obs: obra.obs,
      tiragem: obra.tiragem,
      serie: obra.serie,
      selo: obra.selo,
      tomboAnterio: obra.tomboAnterio,
      verbete: obra.verbete,
      livro: obra.livro,
      imagemAlta: obra.imagemAlta,
      apabex: obra.apabex,
      bunkyo: obra.bunkyo,
      faseDepuracao: obra.faseDepuracao,
      paraAvaliacao: obra.paraAvaliacao,
      paraRestauracao: obra.paraRestauracao,
      paraMoldura: obra.paraMoldura,
      paraLegenda: obra.paraLegenda,
      tempField: obra.tempField,
      selecaoListaAvulsa: obra.selecaoListaAvulsa,
      dominioPubl: obra.dominioPubl,
      dtVencFoto: obra.dtVencFoto,
      obsUsoFoto: obra.obsUsoFoto,
      localFotoAlta: obra.localFotoAlta,
      dataInclusao: obra.dataInclusao,
      dataExclusao: obra.dataExclusao,
      bookX: obra.bookX,
      bookY: obra.bookY,
      genDescricao: obra.genDescricao,
      genField1: obra.genField1,
      paraFotografia: obra.paraFotografia,
      genMarcaInscrObra: obra.genMarcaInscrObra,
      palavrasChave: obra.palavrasChave,
      genMarcaInscrSuporte: obra.genMarcaInscrSuporte,
      genObs: obra.genObs,
      genVerbete: obra.genVerbete,
      dadoDocumentals: obra.dadoDocumentals,
      artista: obra.artista,
      categoria: obra.categoria,
      tecnica: obra.tecnica,
      nivel: obra.nivel,
      data: obra.data,
      empresa: obra.empresa,
      moeda: obra.moeda,
      seguro: obra.seguro,
      responsavel: obra.responsavel,
      acervoatual: obra.acervoatual,
      fotografo: obra.fotografo,
      andarMapa: obra.andarMapa,
    });

    this.dadoDocumentalsSharedCollection = this.dadoDocumentalService.addDadoDocumentalToCollectionIfMissing(
      this.dadoDocumentalsSharedCollection,
      ...(obra.dadoDocumentals ?? [])
    );
    this.artistasSharedCollection = this.artistaService.addArtistaToCollectionIfMissing(this.artistasSharedCollection, obra.artista);
    this.categoriasSharedCollection = this.categoriaService.addCategoriaToCollectionIfMissing(
      this.categoriasSharedCollection,
      obra.categoria
    );
    this.tecnicasSharedCollection = this.tecnicaService.addTecnicaToCollectionIfMissing(this.tecnicasSharedCollection, obra.tecnica);
    this.nivelsSharedCollection = this.nivelService.addNivelToCollectionIfMissing(this.nivelsSharedCollection, obra.nivel);
    this.dataSharedCollection = this.dataService.addDataToCollectionIfMissing(this.dataSharedCollection, obra.data);
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing(this.empresasSharedCollection, obra.empresa);
    this.moedasSharedCollection = this.moedaService.addMoedaToCollectionIfMissing(this.moedasSharedCollection, obra.moeda);
    this.segurosSharedCollection = this.seguroService.addSeguroToCollectionIfMissing(this.segurosSharedCollection, obra.seguro);
    this.responsavelsSharedCollection = this.responsavelService.addResponsavelToCollectionIfMissing(
      this.responsavelsSharedCollection,
      obra.responsavel
    );
    this.acervoAtualsSharedCollection = this.acervoAtualService.addAcervoAtualToCollectionIfMissing(
      this.acervoAtualsSharedCollection,
      obra.acervoatual
    );
    this.contatoesSharedCollection = this.contatoService.addContatoToCollectionIfMissing(this.contatoesSharedCollection, obra.fotografo);
    this.andarMapasSharedCollection = this.andarMapaService.addAndarMapaToCollectionIfMissing(
      this.andarMapasSharedCollection,
      obra.andarMapa
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dadoDocumentalService
      .query()
      .pipe(map((res: HttpResponse<IDadoDocumental[]>) => res.body ?? []))
      .pipe(
        map((dadoDocumentals: IDadoDocumental[]) =>
          this.dadoDocumentalService.addDadoDocumentalToCollectionIfMissing(
            dadoDocumentals,
            ...(this.editForm.get('dadoDocumentals')!.value ?? [])
          )
        )
      )
      .subscribe((dadoDocumentals: IDadoDocumental[]) => (this.dadoDocumentalsSharedCollection = dadoDocumentals));

    this.artistaService
      .query()
      .pipe(map((res: HttpResponse<IArtista[]>) => res.body ?? []))
      .pipe(
        map((artistas: IArtista[]) => this.artistaService.addArtistaToCollectionIfMissing(artistas, this.editForm.get('artista')!.value))
      )
      .subscribe((artistas: IArtista[]) => (this.artistasSharedCollection = artistas));

    this.categoriaService
      .query()
      .pipe(map((res: HttpResponse<ICategoria[]>) => res.body ?? []))
      .pipe(
        map((categorias: ICategoria[]) =>
          this.categoriaService.addCategoriaToCollectionIfMissing(categorias, this.editForm.get('categoria')!.value)
        )
      )
      .subscribe((categorias: ICategoria[]) => (this.categoriasSharedCollection = categorias));

    this.tecnicaService
      .query()
      .pipe(map((res: HttpResponse<ITecnica[]>) => res.body ?? []))
      .pipe(
        map((tecnicas: ITecnica[]) => this.tecnicaService.addTecnicaToCollectionIfMissing(tecnicas, this.editForm.get('tecnica')!.value))
      )
      .subscribe((tecnicas: ITecnica[]) => (this.tecnicasSharedCollection = tecnicas));

    this.nivelService
      .query()
      .pipe(map((res: HttpResponse<INivel[]>) => res.body ?? []))
      .pipe(map((nivels: INivel[]) => this.nivelService.addNivelToCollectionIfMissing(nivels, this.editForm.get('nivel')!.value)))
      .subscribe((nivels: INivel[]) => (this.nivelsSharedCollection = nivels));

    this.dataService
      .query()
      .pipe(map((res: HttpResponse<IData[]>) => res.body ?? []))
      .pipe(map((data: IData[]) => this.dataService.addDataToCollectionIfMissing(data, this.editForm.get('data')!.value)))
      .subscribe((data: IData[]) => (this.dataSharedCollection = data));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing(empresas, this.editForm.get('empresa')!.value))
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.moedaService
      .query()
      .pipe(map((res: HttpResponse<IMoeda[]>) => res.body ?? []))
      .pipe(map((moedas: IMoeda[]) => this.moedaService.addMoedaToCollectionIfMissing(moedas, this.editForm.get('moeda')!.value)))
      .subscribe((moedas: IMoeda[]) => (this.moedasSharedCollection = moedas));

    this.seguroService
      .query()
      .pipe(map((res: HttpResponse<ISeguro[]>) => res.body ?? []))
      .pipe(map((seguros: ISeguro[]) => this.seguroService.addSeguroToCollectionIfMissing(seguros, this.editForm.get('seguro')!.value)))
      .subscribe((seguros: ISeguro[]) => (this.segurosSharedCollection = seguros));

    this.responsavelService
      .query()
      .pipe(map((res: HttpResponse<IResponsavel[]>) => res.body ?? []))
      .pipe(
        map((responsavels: IResponsavel[]) =>
          this.responsavelService.addResponsavelToCollectionIfMissing(responsavels, this.editForm.get('responsavel')!.value)
        )
      )
      .subscribe((responsavels: IResponsavel[]) => (this.responsavelsSharedCollection = responsavels));

    this.acervoAtualService
      .query()
      .pipe(map((res: HttpResponse<IAcervoAtual[]>) => res.body ?? []))
      .pipe(
        map((acervoAtuals: IAcervoAtual[]) =>
          this.acervoAtualService.addAcervoAtualToCollectionIfMissing(acervoAtuals, this.editForm.get('acervoatual')!.value)
        )
      )
      .subscribe((acervoAtuals: IAcervoAtual[]) => (this.acervoAtualsSharedCollection = acervoAtuals));

    this.contatoService
      .query()
      .pipe(map((res: HttpResponse<IContato[]>) => res.body ?? []))
      .pipe(
        map((contatoes: IContato[]) =>
          this.contatoService.addContatoToCollectionIfMissing(contatoes, this.editForm.get('fotografo')!.value)
        )
      )
      .subscribe((contatoes: IContato[]) => (this.contatoesSharedCollection = contatoes));

    this.andarMapaService
      .query()
      .pipe(map((res: HttpResponse<IAndarMapa[]>) => res.body ?? []))
      .pipe(
        map((andarMapas: IAndarMapa[]) =>
          this.andarMapaService.addAndarMapaToCollectionIfMissing(andarMapas, this.editForm.get('andarMapa')!.value)
        )
      )
      .subscribe((andarMapas: IAndarMapa[]) => (this.andarMapasSharedCollection = andarMapas));
  }

  protected createFromForm(): IObra {
    return {
      ...new Obra(),
      id: this.editForm.get(['id'])!.value,
      tombo: this.editForm.get(['tombo'])!.value,
      multiplo: this.editForm.get(['multiplo'])!.value,
      numeroRegistro: this.editForm.get(['numeroRegistro'])!.value,
      outrosTitulos: this.editForm.get(['outrosTitulos'])!.value,
      tituloOriginal: this.editForm.get(['tituloOriginal'])!.value,
      origem: this.editForm.get(['origem'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      assinatura: this.editForm.get(['assinatura'])!.value,
      localAssinatura: this.editForm.get(['localAssinatura'])!.value,
      marcaInscrObra: this.editForm.get(['marcaInscrObra'])!.value,
      marcaInscrSuporte: this.editForm.get(['marcaInscrSuporte'])!.value,
      medidasAprox: this.editForm.get(['medidasAprox'])!.value,
      alturaObra: this.editForm.get(['alturaObra'])!.value,
      largObra: this.editForm.get(['largObra'])!.value,
      profObra: this.editForm.get(['profObra'])!.value,
      diametrObra: this.editForm.get(['diametrObra'])!.value,
      alturaMold: this.editForm.get(['alturaMold'])!.value,
      largMold: this.editForm.get(['largMold'])!.value,
      profMold: this.editForm.get(['profMold'])!.value,
      diametroMold: this.editForm.get(['diametroMold'])!.value,
      dimensAdic: this.editForm.get(['dimensAdic'])!.value,
      pesObra: this.editForm.get(['pesObra'])!.value,
      atribuido: this.editForm.get(['atribuido'])!.value,
      nFoto: this.editForm.get(['nFoto'])!.value,
      conjunto: this.editForm.get(['conjunto'])!.value,
      conjTombo: this.editForm.get(['conjTombo'])!.value,
      valorSeguro: this.editForm.get(['valorSeguro'])!.value,
      valorSeguroReal: this.editForm.get(['valorSeguroReal'])!.value,
      dataconversao: this.editForm.get(['dataconversao'])!.value,
      dataAlterApolice: this.editForm.get(['dataAlterApolice'])!.value,
      localAtual: this.editForm.get(['localAtual'])!.value,
      localAtualNovo: this.editForm.get(['localAtualNovo'])!.value,
      codParametro: this.editForm.get(['codParametro'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      tiragem: this.editForm.get(['tiragem'])!.value,
      serie: this.editForm.get(['serie'])!.value,
      selo: this.editForm.get(['selo'])!.value,
      tomboAnterio: this.editForm.get(['tomboAnterio'])!.value,
      verbete: this.editForm.get(['verbete'])!.value,
      livro: this.editForm.get(['livro'])!.value,
      imagemAlta: this.editForm.get(['imagemAlta'])!.value,
      apabex: this.editForm.get(['apabex'])!.value,
      bunkyo: this.editForm.get(['bunkyo'])!.value,
      faseDepuracao: this.editForm.get(['faseDepuracao'])!.value,
      paraAvaliacao: this.editForm.get(['paraAvaliacao'])!.value,
      paraRestauracao: this.editForm.get(['paraRestauracao'])!.value,
      paraMoldura: this.editForm.get(['paraMoldura'])!.value,
      paraLegenda: this.editForm.get(['paraLegenda'])!.value,
      tempField: this.editForm.get(['tempField'])!.value,
      selecaoListaAvulsa: this.editForm.get(['selecaoListaAvulsa'])!.value,
      dominioPubl: this.editForm.get(['dominioPubl'])!.value,
      dtVencFoto: this.editForm.get(['dtVencFoto'])!.value,
      obsUsoFoto: this.editForm.get(['obsUsoFoto'])!.value,
      localFotoAlta: this.editForm.get(['localFotoAlta'])!.value,
      dataInclusao: this.editForm.get(['dataInclusao'])!.value,
      dataExclusao: this.editForm.get(['dataExclusao'])!.value,
      bookX: this.editForm.get(['bookX'])!.value,
      bookY: this.editForm.get(['bookY'])!.value,
      genDescricao: this.editForm.get(['genDescricao'])!.value,
      genField1: this.editForm.get(['genField1'])!.value,
      paraFotografia: this.editForm.get(['paraFotografia'])!.value,
      genMarcaInscrObra: this.editForm.get(['genMarcaInscrObra'])!.value,
      palavrasChave: this.editForm.get(['palavrasChave'])!.value,
      genMarcaInscrSuporte: this.editForm.get(['genMarcaInscrSuporte'])!.value,
      genObs: this.editForm.get(['genObs'])!.value,
      genVerbete: this.editForm.get(['genVerbete'])!.value,
      dadoDocumentals: this.editForm.get(['dadoDocumentals'])!.value,
      artista: this.editForm.get(['artista'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      tecnica: this.editForm.get(['tecnica'])!.value,
      nivel: this.editForm.get(['nivel'])!.value,
      data: this.editForm.get(['data'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
      moeda: this.editForm.get(['moeda'])!.value,
      seguro: this.editForm.get(['seguro'])!.value,
      responsavel: this.editForm.get(['responsavel'])!.value,
      acervoatual: this.editForm.get(['acervoatual'])!.value,
      fotografo: this.editForm.get(['fotografo'])!.value,
      andarMapa: this.editForm.get(['andarMapa'])!.value,
    };
  }
}
