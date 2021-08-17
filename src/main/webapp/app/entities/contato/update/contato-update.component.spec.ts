jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ContatoService } from '../service/contato.service';
import { IContato, Contato } from '../contato.model';
import { IAreaDepto } from 'app/entities/area-depto/area-depto.model';
import { AreaDeptoService } from 'app/entities/area-depto/service/area-depto.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';

import { ContatoUpdateComponent } from './contato-update.component';

describe('Component Tests', () => {
  describe('Contato Management Update Component', () => {
    let comp: ContatoUpdateComponent;
    let fixture: ComponentFixture<ContatoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let contatoService: ContatoService;
    let areaDeptoService: AreaDeptoService;
    let cidadeService: CidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ContatoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ContatoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContatoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      contatoService = TestBed.inject(ContatoService);
      areaDeptoService = TestBed.inject(AreaDeptoService);
      cidadeService = TestBed.inject(CidadeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call AreaDepto query and add missing value', () => {
        const contato: IContato = { id: 456 };
        const area: IAreaDepto = { id: 16355 };
        contato.area = area;

        const areaDeptoCollection: IAreaDepto[] = [{ id: 1970 }];
        jest.spyOn(areaDeptoService, 'query').mockReturnValue(of(new HttpResponse({ body: areaDeptoCollection })));
        const additionalAreaDeptos = [area];
        const expectedCollection: IAreaDepto[] = [...additionalAreaDeptos, ...areaDeptoCollection];
        jest.spyOn(areaDeptoService, 'addAreaDeptoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ contato });
        comp.ngOnInit();

        expect(areaDeptoService.query).toHaveBeenCalled();
        expect(areaDeptoService.addAreaDeptoToCollectionIfMissing).toHaveBeenCalledWith(areaDeptoCollection, ...additionalAreaDeptos);
        expect(comp.areaDeptosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cidade query and add missing value', () => {
        const contato: IContato = { id: 456 };
        const cidade: ICidade = { id: 2983 };
        contato.cidade = cidade;

        const cidadeCollection: ICidade[] = [{ id: 87113 }];
        jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
        const additionalCidades = [cidade];
        const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
        jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ contato });
        comp.ngOnInit();

        expect(cidadeService.query).toHaveBeenCalled();
        expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(cidadeCollection, ...additionalCidades);
        expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const contato: IContato = { id: 456 };
        const area: IAreaDepto = { id: 24103 };
        contato.area = area;
        const cidade: ICidade = { id: 31336 };
        contato.cidade = cidade;

        activatedRoute.data = of({ contato });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(contato));
        expect(comp.areaDeptosSharedCollection).toContain(area);
        expect(comp.cidadesSharedCollection).toContain(cidade);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contato>>();
        const contato = { id: 123 };
        jest.spyOn(contatoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contato });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contato }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(contatoService.update).toHaveBeenCalledWith(contato);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contato>>();
        const contato = new Contato();
        jest.spyOn(contatoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contato });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contato }));
        saveSubject.complete();

        // THEN
        expect(contatoService.create).toHaveBeenCalledWith(contato);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contato>>();
        const contato = { id: 123 };
        jest.spyOn(contatoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contato });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(contatoService.update).toHaveBeenCalledWith(contato);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAreaDeptoById', () => {
        it('Should return tracked AreaDepto primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAreaDeptoById(0, entity);
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
    });
  });
});
