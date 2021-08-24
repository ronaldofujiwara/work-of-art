jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SeguroService } from '../service/seguro.service';
import { ISeguro, Seguro } from '../seguro.model';
import { IContato } from 'app/entities/contato/contato.model';
import { ContatoService } from 'app/entities/contato/service/contato.service';
import { IMoeda } from 'app/entities/moeda/moeda.model';
import { MoedaService } from 'app/entities/moeda/service/moeda.service';

import { SeguroUpdateComponent } from './seguro-update.component';

describe('Component Tests', () => {
  describe('Seguro Management Update Component', () => {
    let comp: SeguroUpdateComponent;
    let fixture: ComponentFixture<SeguroUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let seguroService: SeguroService;
    let contatoService: ContatoService;
    let moedaService: MoedaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SeguroUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SeguroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SeguroUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      seguroService = TestBed.inject(SeguroService);
      contatoService = TestBed.inject(ContatoService);
      moedaService = TestBed.inject(MoedaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Contato query and add missing value', () => {
        const seguro: ISeguro = { id: 456 };
        const contatoSeg: IContato = { id: 35533 };
        seguro.contatoSeg = contatoSeg;
        const contatoCor: IContato = { id: 61518 };
        seguro.contatoCor = contatoCor;

        const contatoCollection: IContato[] = [{ id: 2499 }];
        jest.spyOn(contatoService, 'query').mockReturnValue(of(new HttpResponse({ body: contatoCollection })));
        const additionalContatoes = [contatoSeg, contatoCor];
        const expectedCollection: IContato[] = [...additionalContatoes, ...contatoCollection];
        jest.spyOn(contatoService, 'addContatoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ seguro });
        comp.ngOnInit();

        expect(contatoService.query).toHaveBeenCalled();
        expect(contatoService.addContatoToCollectionIfMissing).toHaveBeenCalledWith(contatoCollection, ...additionalContatoes);
        expect(comp.contatoesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Moeda query and add missing value', () => {
        const seguro: ISeguro = { id: 456 };
        const moeda: IMoeda = { id: 99969 };
        seguro.moeda = moeda;

        const moedaCollection: IMoeda[] = [{ id: 71474 }];
        jest.spyOn(moedaService, 'query').mockReturnValue(of(new HttpResponse({ body: moedaCollection })));
        const additionalMoedas = [moeda];
        const expectedCollection: IMoeda[] = [...additionalMoedas, ...moedaCollection];
        jest.spyOn(moedaService, 'addMoedaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ seguro });
        comp.ngOnInit();

        expect(moedaService.query).toHaveBeenCalled();
        expect(moedaService.addMoedaToCollectionIfMissing).toHaveBeenCalledWith(moedaCollection, ...additionalMoedas);
        expect(comp.moedasSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const seguro: ISeguro = { id: 456 };
        const contatoSeg: IContato = { id: 15068 };
        seguro.contatoSeg = contatoSeg;
        const contatoCor: IContato = { id: 89241 };
        seguro.contatoCor = contatoCor;
        const moeda: IMoeda = { id: 47575 };
        seguro.moeda = moeda;

        activatedRoute.data = of({ seguro });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(seguro));
        expect(comp.contatoesSharedCollection).toContain(contatoSeg);
        expect(comp.contatoesSharedCollection).toContain(contatoCor);
        expect(comp.moedasSharedCollection).toContain(moeda);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Seguro>>();
        const seguro = { id: 123 };
        jest.spyOn(seguroService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ seguro });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: seguro }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(seguroService.update).toHaveBeenCalledWith(seguro);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Seguro>>();
        const seguro = new Seguro();
        jest.spyOn(seguroService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ seguro });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: seguro }));
        saveSubject.complete();

        // THEN
        expect(seguroService.create).toHaveBeenCalledWith(seguro);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Seguro>>();
        const seguro = { id: 123 };
        jest.spyOn(seguroService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ seguro });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(seguroService.update).toHaveBeenCalledWith(seguro);
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

      describe('trackMoedaById', () => {
        it('Should return tracked Moeda primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMoedaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
