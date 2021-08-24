jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NivelService } from '../service/nivel.service';
import { INivel, Nivel } from '../nivel.model';

import { NivelUpdateComponent } from './nivel-update.component';

describe('Component Tests', () => {
  describe('Nivel Management Update Component', () => {
    let comp: NivelUpdateComponent;
    let fixture: ComponentFixture<NivelUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let nivelService: NivelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NivelUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NivelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      nivelService = TestBed.inject(NivelService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const nivel: INivel = { id: 456 };

        activatedRoute.data = of({ nivel });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nivel));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Nivel>>();
        const nivel = { id: 123 };
        jest.spyOn(nivelService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nivel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nivel }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(nivelService.update).toHaveBeenCalledWith(nivel);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Nivel>>();
        const nivel = new Nivel();
        jest.spyOn(nivelService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nivel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nivel }));
        saveSubject.complete();

        // THEN
        expect(nivelService.create).toHaveBeenCalledWith(nivel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Nivel>>();
        const nivel = { id: 123 };
        jest.spyOn(nivelService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nivel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(nivelService.update).toHaveBeenCalledWith(nivel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
