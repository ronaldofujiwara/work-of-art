jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AmbienteService } from '../service/ambiente.service';
import { IAmbiente, Ambiente } from '../ambiente.model';

import { AmbienteUpdateComponent } from './ambiente-update.component';

describe('Component Tests', () => {
  describe('Ambiente Management Update Component', () => {
    let comp: AmbienteUpdateComponent;
    let fixture: ComponentFixture<AmbienteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ambienteService: AmbienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AmbienteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AmbienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmbienteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ambienteService = TestBed.inject(AmbienteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const ambiente: IAmbiente = { id: 456 };

        activatedRoute.data = of({ ambiente });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ambiente));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Ambiente>>();
        const ambiente = { id: 123 };
        jest.spyOn(ambienteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ ambiente });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ambiente }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ambienteService.update).toHaveBeenCalledWith(ambiente);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Ambiente>>();
        const ambiente = new Ambiente();
        jest.spyOn(ambienteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ ambiente });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ambiente }));
        saveSubject.complete();

        // THEN
        expect(ambienteService.create).toHaveBeenCalledWith(ambiente);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Ambiente>>();
        const ambiente = { id: 123 };
        jest.spyOn(ambienteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ ambiente });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ambienteService.update).toHaveBeenCalledWith(ambiente);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
