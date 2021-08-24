jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EspacoService } from '../service/espaco.service';
import { IEspaco, Espaco } from '../espaco.model';

import { EspacoUpdateComponent } from './espaco-update.component';

describe('Component Tests', () => {
  describe('Espaco Management Update Component', () => {
    let comp: EspacoUpdateComponent;
    let fixture: ComponentFixture<EspacoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let espacoService: EspacoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EspacoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EspacoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EspacoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      espacoService = TestBed.inject(EspacoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const espaco: IEspaco = { id: 456 };

        activatedRoute.data = of({ espaco });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(espaco));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Espaco>>();
        const espaco = { id: 123 };
        jest.spyOn(espacoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ espaco });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: espaco }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(espacoService.update).toHaveBeenCalledWith(espaco);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Espaco>>();
        const espaco = new Espaco();
        jest.spyOn(espacoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ espaco });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: espaco }));
        saveSubject.complete();

        // THEN
        expect(espacoService.create).toHaveBeenCalledWith(espaco);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Espaco>>();
        const espaco = { id: 123 };
        jest.spyOn(espacoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ espaco });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(espacoService.update).toHaveBeenCalledWith(espaco);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
