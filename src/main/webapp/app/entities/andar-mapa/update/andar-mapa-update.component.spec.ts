jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AndarMapaService } from '../service/andar-mapa.service';
import { IAndarMapa, AndarMapa } from '../andar-mapa.model';
import { IEspaco } from 'app/entities/espaco/espaco.model';
import { EspacoService } from 'app/entities/espaco/service/espaco.service';

import { AndarMapaUpdateComponent } from './andar-mapa-update.component';

describe('Component Tests', () => {
  describe('AndarMapa Management Update Component', () => {
    let comp: AndarMapaUpdateComponent;
    let fixture: ComponentFixture<AndarMapaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let andarMapaService: AndarMapaService;
    let espacoService: EspacoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AndarMapaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AndarMapaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AndarMapaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      andarMapaService = TestBed.inject(AndarMapaService);
      espacoService = TestBed.inject(EspacoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Espaco query and add missing value', () => {
        const andarMapa: IAndarMapa = { id: 456 };
        const espaco: IEspaco = { id: 72665 };
        andarMapa.espaco = espaco;

        const espacoCollection: IEspaco[] = [{ id: 45707 }];
        jest.spyOn(espacoService, 'query').mockReturnValue(of(new HttpResponse({ body: espacoCollection })));
        const additionalEspacos = [espaco];
        const expectedCollection: IEspaco[] = [...additionalEspacos, ...espacoCollection];
        jest.spyOn(espacoService, 'addEspacoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ andarMapa });
        comp.ngOnInit();

        expect(espacoService.query).toHaveBeenCalled();
        expect(espacoService.addEspacoToCollectionIfMissing).toHaveBeenCalledWith(espacoCollection, ...additionalEspacos);
        expect(comp.espacosSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const andarMapa: IAndarMapa = { id: 456 };
        const espaco: IEspaco = { id: 58079 };
        andarMapa.espaco = espaco;

        activatedRoute.data = of({ andarMapa });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(andarMapa));
        expect(comp.espacosSharedCollection).toContain(espaco);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AndarMapa>>();
        const andarMapa = { id: 123 };
        jest.spyOn(andarMapaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ andarMapa });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: andarMapa }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(andarMapaService.update).toHaveBeenCalledWith(andarMapa);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AndarMapa>>();
        const andarMapa = new AndarMapa();
        jest.spyOn(andarMapaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ andarMapa });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: andarMapa }));
        saveSubject.complete();

        // THEN
        expect(andarMapaService.create).toHaveBeenCalledWith(andarMapa);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AndarMapa>>();
        const andarMapa = { id: 123 };
        jest.spyOn(andarMapaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ andarMapa });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(andarMapaService.update).toHaveBeenCalledWith(andarMapa);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEspacoById', () => {
        it('Should return tracked Espaco primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEspacoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
