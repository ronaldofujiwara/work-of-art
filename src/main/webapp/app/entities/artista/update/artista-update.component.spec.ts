jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ArtistaService } from '../service/artista.service';
import { IArtista, Artista } from '../artista.model';
import { IContato } from 'app/entities/contato/contato.model';
import { ContatoService } from 'app/entities/contato/service/contato.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IResponsavel } from 'app/entities/responsavel/responsavel.model';
import { ResponsavelService } from 'app/entities/responsavel/service/responsavel.service';
import { IFuncaoArtista } from 'app/entities/funcao-artista/funcao-artista.model';
import { FuncaoArtistaService } from 'app/entities/funcao-artista/service/funcao-artista.service';

import { ArtistaUpdateComponent } from './artista-update.component';

describe('Component Tests', () => {
  describe('Artista Management Update Component', () => {
    let comp: ArtistaUpdateComponent;
    let fixture: ComponentFixture<ArtistaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let artistaService: ArtistaService;
    let contatoService: ContatoService;
    let cidadeService: CidadeService;
    let responsavelService: ResponsavelService;
    let funcaoArtistaService: FuncaoArtistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ArtistaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ArtistaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArtistaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      artistaService = TestBed.inject(ArtistaService);
      contatoService = TestBed.inject(ContatoService);
      cidadeService = TestBed.inject(CidadeService);
      responsavelService = TestBed.inject(ResponsavelService);
      funcaoArtistaService = TestBed.inject(FuncaoArtistaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Contato query and add missing value', () => {
        const artista: IArtista = { id: 456 };
        const contatoes: IContato[] = [{ id: 38976 }];
        artista.contatoes = contatoes;

        const contatoCollection: IContato[] = [{ id: 10832 }];
        jest.spyOn(contatoService, 'query').mockReturnValue(of(new HttpResponse({ body: contatoCollection })));
        const additionalContatoes = [...contatoes];
        const expectedCollection: IContato[] = [...additionalContatoes, ...contatoCollection];
        jest.spyOn(contatoService, 'addContatoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        expect(contatoService.query).toHaveBeenCalled();
        expect(contatoService.addContatoToCollectionIfMissing).toHaveBeenCalledWith(contatoCollection, ...additionalContatoes);
        expect(comp.contatoesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cidade query and add missing value', () => {
        const artista: IArtista = { id: 456 };
        const cidadeNasc: ICidade = { id: 26870 };
        artista.cidadeNasc = cidadeNasc;
        const cidadeFalesc: ICidade = { id: 1354 };
        artista.cidadeFalesc = cidadeFalesc;

        const cidadeCollection: ICidade[] = [{ id: 2506 }];
        jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
        const additionalCidades = [cidadeNasc, cidadeFalesc];
        const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
        jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        expect(cidadeService.query).toHaveBeenCalled();
        expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(cidadeCollection, ...additionalCidades);
        expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Responsavel query and add missing value', () => {
        const artista: IArtista = { id: 456 };
        const respVerbete: IResponsavel = { id: 22117 };
        artista.respVerbete = respVerbete;

        const responsavelCollection: IResponsavel[] = [{ id: 6121 }];
        jest.spyOn(responsavelService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelCollection })));
        const additionalResponsavels = [respVerbete];
        const expectedCollection: IResponsavel[] = [...additionalResponsavels, ...responsavelCollection];
        jest.spyOn(responsavelService, 'addResponsavelToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        expect(responsavelService.query).toHaveBeenCalled();
        expect(responsavelService.addResponsavelToCollectionIfMissing).toHaveBeenCalledWith(
          responsavelCollection,
          ...additionalResponsavels
        );
        expect(comp.responsavelsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call FuncaoArtista query and add missing value', () => {
        const artista: IArtista = { id: 456 };
        const funcaoArtista: IFuncaoArtista = { id: 69242 };
        artista.funcaoArtista = funcaoArtista;

        const funcaoArtistaCollection: IFuncaoArtista[] = [{ id: 6093 }];
        jest.spyOn(funcaoArtistaService, 'query').mockReturnValue(of(new HttpResponse({ body: funcaoArtistaCollection })));
        const additionalFuncaoArtistas = [funcaoArtista];
        const expectedCollection: IFuncaoArtista[] = [...additionalFuncaoArtistas, ...funcaoArtistaCollection];
        jest.spyOn(funcaoArtistaService, 'addFuncaoArtistaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        expect(funcaoArtistaService.query).toHaveBeenCalled();
        expect(funcaoArtistaService.addFuncaoArtistaToCollectionIfMissing).toHaveBeenCalledWith(
          funcaoArtistaCollection,
          ...additionalFuncaoArtistas
        );
        expect(comp.funcaoArtistasSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const artista: IArtista = { id: 456 };
        const contatoes: IContato = { id: 18411 };
        artista.contatoes = [contatoes];
        const cidadeNasc: ICidade = { id: 53710 };
        artista.cidadeNasc = cidadeNasc;
        const cidadeFalesc: ICidade = { id: 55170 };
        artista.cidadeFalesc = cidadeFalesc;
        const respVerbete: IResponsavel = { id: 72312 };
        artista.respVerbete = respVerbete;
        const funcaoArtista: IFuncaoArtista = { id: 18312 };
        artista.funcaoArtista = funcaoArtista;

        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(artista));
        expect(comp.contatoesSharedCollection).toContain(contatoes);
        expect(comp.cidadesSharedCollection).toContain(cidadeNasc);
        expect(comp.cidadesSharedCollection).toContain(cidadeFalesc);
        expect(comp.responsavelsSharedCollection).toContain(respVerbete);
        expect(comp.funcaoArtistasSharedCollection).toContain(funcaoArtista);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Artista>>();
        const artista = { id: 123 };
        jest.spyOn(artistaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: artista }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(artistaService.update).toHaveBeenCalledWith(artista);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Artista>>();
        const artista = new Artista();
        jest.spyOn(artistaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: artista }));
        saveSubject.complete();

        // THEN
        expect(artistaService.create).toHaveBeenCalledWith(artista);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Artista>>();
        const artista = { id: 123 };
        jest.spyOn(artistaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ artista });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(artistaService.update).toHaveBeenCalledWith(artista);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackContatoById', () => {
        it('Should return tracked Contato primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackContatoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCidadeById', () => {
        it('Should return tracked Cidade primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCidadeById(0, entity);
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

      describe('trackFuncaoArtistaById', () => {
        it('Should return tracked FuncaoArtista primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFuncaoArtistaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedContato', () => {
        it('Should return option if no Contato is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedContato(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Contato for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedContato(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Contato is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedContato(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
