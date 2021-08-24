jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ObraService } from '../service/obra.service';
import { IObra, Obra } from '../obra.model';
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

import { ObraUpdateComponent } from './obra-update.component';

describe('Component Tests', () => {
  describe('Obra Management Update Component', () => {
    let comp: ObraUpdateComponent;
    let fixture: ComponentFixture<ObraUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let obraService: ObraService;
    let dadoDocumentalService: DadoDocumentalService;
    let artistaService: ArtistaService;
    let categoriaService: CategoriaService;
    let tecnicaService: TecnicaService;
    let nivelService: NivelService;
    let dataService: DataService;
    let empresaService: EmpresaService;
    let moedaService: MoedaService;
    let seguroService: SeguroService;
    let responsavelService: ResponsavelService;
    let acervoAtualService: AcervoAtualService;
    let contatoService: ContatoService;
    let andarMapaService: AndarMapaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ObraUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ObraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObraUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      obraService = TestBed.inject(ObraService);
      dadoDocumentalService = TestBed.inject(DadoDocumentalService);
      artistaService = TestBed.inject(ArtistaService);
      categoriaService = TestBed.inject(CategoriaService);
      tecnicaService = TestBed.inject(TecnicaService);
      nivelService = TestBed.inject(NivelService);
      dataService = TestBed.inject(DataService);
      empresaService = TestBed.inject(EmpresaService);
      moedaService = TestBed.inject(MoedaService);
      seguroService = TestBed.inject(SeguroService);
      responsavelService = TestBed.inject(ResponsavelService);
      acervoAtualService = TestBed.inject(AcervoAtualService);
      contatoService = TestBed.inject(ContatoService);
      andarMapaService = TestBed.inject(AndarMapaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call DadoDocumental query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const dadoDocumentals: IDadoDocumental[] = [{ id: 75463 }];
        obra.dadoDocumentals = dadoDocumentals;

        const dadoDocumentalCollection: IDadoDocumental[] = [{ id: 19886 }];
        jest.spyOn(dadoDocumentalService, 'query').mockReturnValue(of(new HttpResponse({ body: dadoDocumentalCollection })));
        const additionalDadoDocumentals = [...dadoDocumentals];
        const expectedCollection: IDadoDocumental[] = [...additionalDadoDocumentals, ...dadoDocumentalCollection];
        jest.spyOn(dadoDocumentalService, 'addDadoDocumentalToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(dadoDocumentalService.query).toHaveBeenCalled();
        expect(dadoDocumentalService.addDadoDocumentalToCollectionIfMissing).toHaveBeenCalledWith(
          dadoDocumentalCollection,
          ...additionalDadoDocumentals
        );
        expect(comp.dadoDocumentalsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Artista query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const artista: IArtista = { id: 44559 };
        obra.artista = artista;

        const artistaCollection: IArtista[] = [{ id: 91588 }];
        jest.spyOn(artistaService, 'query').mockReturnValue(of(new HttpResponse({ body: artistaCollection })));
        const additionalArtistas = [artista];
        const expectedCollection: IArtista[] = [...additionalArtistas, ...artistaCollection];
        jest.spyOn(artistaService, 'addArtistaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(artistaService.query).toHaveBeenCalled();
        expect(artistaService.addArtistaToCollectionIfMissing).toHaveBeenCalledWith(artistaCollection, ...additionalArtistas);
        expect(comp.artistasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Categoria query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const categoria: ICategoria = { id: 38188 };
        obra.categoria = categoria;

        const categoriaCollection: ICategoria[] = [{ id: 44340 }];
        jest.spyOn(categoriaService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaCollection })));
        const additionalCategorias = [categoria];
        const expectedCollection: ICategoria[] = [...additionalCategorias, ...categoriaCollection];
        jest.spyOn(categoriaService, 'addCategoriaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(categoriaService.query).toHaveBeenCalled();
        expect(categoriaService.addCategoriaToCollectionIfMissing).toHaveBeenCalledWith(categoriaCollection, ...additionalCategorias);
        expect(comp.categoriasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Tecnica query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const tecnica: ITecnica = { id: 48641 };
        obra.tecnica = tecnica;

        const tecnicaCollection: ITecnica[] = [{ id: 59867 }];
        jest.spyOn(tecnicaService, 'query').mockReturnValue(of(new HttpResponse({ body: tecnicaCollection })));
        const additionalTecnicas = [tecnica];
        const expectedCollection: ITecnica[] = [...additionalTecnicas, ...tecnicaCollection];
        jest.spyOn(tecnicaService, 'addTecnicaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(tecnicaService.query).toHaveBeenCalled();
        expect(tecnicaService.addTecnicaToCollectionIfMissing).toHaveBeenCalledWith(tecnicaCollection, ...additionalTecnicas);
        expect(comp.tecnicasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Nivel query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const nivel: INivel = { id: 20017 };
        obra.nivel = nivel;

        const nivelCollection: INivel[] = [{ id: 74577 }];
        jest.spyOn(nivelService, 'query').mockReturnValue(of(new HttpResponse({ body: nivelCollection })));
        const additionalNivels = [nivel];
        const expectedCollection: INivel[] = [...additionalNivels, ...nivelCollection];
        jest.spyOn(nivelService, 'addNivelToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(nivelService.query).toHaveBeenCalled();
        expect(nivelService.addNivelToCollectionIfMissing).toHaveBeenCalledWith(nivelCollection, ...additionalNivels);
        expect(comp.nivelsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Data query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const data: IData = { id: 54223 };
        obra.data = data;

        const dataCollection: IData[] = [{ id: 70331 }];
        jest.spyOn(dataService, 'query').mockReturnValue(of(new HttpResponse({ body: dataCollection })));
        const additionalData = [data];
        const expectedCollection: IData[] = [...additionalData, ...dataCollection];
        jest.spyOn(dataService, 'addDataToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(dataService.query).toHaveBeenCalled();
        expect(dataService.addDataToCollectionIfMissing).toHaveBeenCalledWith(dataCollection, ...additionalData);
        expect(comp.dataSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Empresa query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const empresa: IEmpresa = { id: 61168 };
        obra.empresa = empresa;

        const empresaCollection: IEmpresa[] = [{ id: 96474 }];
        jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
        const additionalEmpresas = [empresa];
        const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
        jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(empresaService.query).toHaveBeenCalled();
        expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(empresaCollection, ...additionalEmpresas);
        expect(comp.empresasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Moeda query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const moeda: IMoeda = { id: 43988 };
        obra.moeda = moeda;

        const moedaCollection: IMoeda[] = [{ id: 23852 }];
        jest.spyOn(moedaService, 'query').mockReturnValue(of(new HttpResponse({ body: moedaCollection })));
        const additionalMoedas = [moeda];
        const expectedCollection: IMoeda[] = [...additionalMoedas, ...moedaCollection];
        jest.spyOn(moedaService, 'addMoedaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(moedaService.query).toHaveBeenCalled();
        expect(moedaService.addMoedaToCollectionIfMissing).toHaveBeenCalledWith(moedaCollection, ...additionalMoedas);
        expect(comp.moedasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Seguro query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const seguro: ISeguro = { id: 10246 };
        obra.seguro = seguro;

        const seguroCollection: ISeguro[] = [{ id: 63613 }];
        jest.spyOn(seguroService, 'query').mockReturnValue(of(new HttpResponse({ body: seguroCollection })));
        const additionalSeguros = [seguro];
        const expectedCollection: ISeguro[] = [...additionalSeguros, ...seguroCollection];
        jest.spyOn(seguroService, 'addSeguroToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(seguroService.query).toHaveBeenCalled();
        expect(seguroService.addSeguroToCollectionIfMissing).toHaveBeenCalledWith(seguroCollection, ...additionalSeguros);
        expect(comp.segurosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Responsavel query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const responsavel: IResponsavel = { id: 2780 };
        obra.responsavel = responsavel;

        const responsavelCollection: IResponsavel[] = [{ id: 16222 }];
        jest.spyOn(responsavelService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelCollection })));
        const additionalResponsavels = [responsavel];
        const expectedCollection: IResponsavel[] = [...additionalResponsavels, ...responsavelCollection];
        jest.spyOn(responsavelService, 'addResponsavelToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(responsavelService.query).toHaveBeenCalled();
        expect(responsavelService.addResponsavelToCollectionIfMissing).toHaveBeenCalledWith(
          responsavelCollection,
          ...additionalResponsavels
        );
        expect(comp.responsavelsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call AcervoAtual query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const acervoatual: IAcervoAtual = { id: 51940 };
        obra.acervoatual = acervoatual;

        const acervoAtualCollection: IAcervoAtual[] = [{ id: 97221 }];
        jest.spyOn(acervoAtualService, 'query').mockReturnValue(of(new HttpResponse({ body: acervoAtualCollection })));
        const additionalAcervoAtuals = [acervoatual];
        const expectedCollection: IAcervoAtual[] = [...additionalAcervoAtuals, ...acervoAtualCollection];
        jest.spyOn(acervoAtualService, 'addAcervoAtualToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(acervoAtualService.query).toHaveBeenCalled();
        expect(acervoAtualService.addAcervoAtualToCollectionIfMissing).toHaveBeenCalledWith(
          acervoAtualCollection,
          ...additionalAcervoAtuals
        );
        expect(comp.acervoAtualsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Contato query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const fotografo: IContato = { id: 30303 };
        obra.fotografo = fotografo;

        const contatoCollection: IContato[] = [{ id: 48346 }];
        jest.spyOn(contatoService, 'query').mockReturnValue(of(new HttpResponse({ body: contatoCollection })));
        const additionalContatoes = [fotografo];
        const expectedCollection: IContato[] = [...additionalContatoes, ...contatoCollection];
        jest.spyOn(contatoService, 'addContatoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(contatoService.query).toHaveBeenCalled();
        expect(contatoService.addContatoToCollectionIfMissing).toHaveBeenCalledWith(contatoCollection, ...additionalContatoes);
        expect(comp.contatoesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call AndarMapa query and add missing value', () => {
        const obra: IObra = { id: 456 };
        const andarMapa: IAndarMapa = { id: 29236 };
        obra.andarMapa = andarMapa;

        const andarMapaCollection: IAndarMapa[] = [{ id: 91100 }];
        jest.spyOn(andarMapaService, 'query').mockReturnValue(of(new HttpResponse({ body: andarMapaCollection })));
        const additionalAndarMapas = [andarMapa];
        const expectedCollection: IAndarMapa[] = [...additionalAndarMapas, ...andarMapaCollection];
        jest.spyOn(andarMapaService, 'addAndarMapaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(andarMapaService.query).toHaveBeenCalled();
        expect(andarMapaService.addAndarMapaToCollectionIfMissing).toHaveBeenCalledWith(andarMapaCollection, ...additionalAndarMapas);
        expect(comp.andarMapasSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const obra: IObra = { id: 456 };
        const dadoDocumentals: IDadoDocumental = { id: 13354 };
        obra.dadoDocumentals = [dadoDocumentals];
        const artista: IArtista = { id: 61292 };
        obra.artista = artista;
        const categoria: ICategoria = { id: 12927 };
        obra.categoria = categoria;
        const tecnica: ITecnica = { id: 84943 };
        obra.tecnica = tecnica;
        const nivel: INivel = { id: 31872 };
        obra.nivel = nivel;
        const data: IData = { id: 53321 };
        obra.data = data;
        const empresa: IEmpresa = { id: 23929 };
        obra.empresa = empresa;
        const moeda: IMoeda = { id: 21254 };
        obra.moeda = moeda;
        const seguro: ISeguro = { id: 99692 };
        obra.seguro = seguro;
        const responsavel: IResponsavel = { id: 41377 };
        obra.responsavel = responsavel;
        const acervoatual: IAcervoAtual = { id: 29909 };
        obra.acervoatual = acervoatual;
        const fotografo: IContato = { id: 82782 };
        obra.fotografo = fotografo;
        const andarMapa: IAndarMapa = { id: 71418 };
        obra.andarMapa = andarMapa;

        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(obra));
        expect(comp.dadoDocumentalsSharedCollection).toContain(dadoDocumentals);
        expect(comp.artistasSharedCollection).toContain(artista);
        expect(comp.categoriasSharedCollection).toContain(categoria);
        expect(comp.tecnicasSharedCollection).toContain(tecnica);
        expect(comp.nivelsSharedCollection).toContain(nivel);
        expect(comp.dataSharedCollection).toContain(data);
        expect(comp.empresasSharedCollection).toContain(empresa);
        expect(comp.moedasSharedCollection).toContain(moeda);
        expect(comp.segurosSharedCollection).toContain(seguro);
        expect(comp.responsavelsSharedCollection).toContain(responsavel);
        expect(comp.acervoAtualsSharedCollection).toContain(acervoatual);
        expect(comp.contatoesSharedCollection).toContain(fotografo);
        expect(comp.andarMapasSharedCollection).toContain(andarMapa);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Obra>>();
        const obra = { id: 123 };
        jest.spyOn(obraService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: obra }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(obraService.update).toHaveBeenCalledWith(obra);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Obra>>();
        const obra = new Obra();
        jest.spyOn(obraService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: obra }));
        saveSubject.complete();

        // THEN
        expect(obraService.create).toHaveBeenCalledWith(obra);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Obra>>();
        const obra = { id: 123 };
        jest.spyOn(obraService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ obra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(obraService.update).toHaveBeenCalledWith(obra);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDadoDocumentalById', () => {
        it('Should return tracked DadoDocumental primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDadoDocumentalById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackArtistaById', () => {
        it('Should return tracked Artista primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackArtistaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCategoriaById', () => {
        it('Should return tracked Categoria primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategoriaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTecnicaById', () => {
        it('Should return tracked Tecnica primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTecnicaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackNivelById', () => {
        it('Should return tracked Nivel primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNivelById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDataById', () => {
        it('Should return tracked Data primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDataById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEmpresaById', () => {
        it('Should return tracked Empresa primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEmpresaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackMoedaById', () => {
        it('Should return tracked Moeda primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMoedaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackSeguroById', () => {
        it('Should return tracked Seguro primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSeguroById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackResponsavelById', () => {
        it('Should return tracked Responsavel primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackResponsavelById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAcervoAtualById', () => {
        it('Should return tracked AcervoAtual primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAcervoAtualById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackContatoById', () => {
        it('Should return tracked Contato primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackContatoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAndarMapaById', () => {
        it('Should return tracked AndarMapa primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAndarMapaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedDadoDocumental', () => {
        it('Should return option if no DadoDocumental is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedDadoDocumental(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected DadoDocumental for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedDadoDocumental(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this DadoDocumental is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedDadoDocumental(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
