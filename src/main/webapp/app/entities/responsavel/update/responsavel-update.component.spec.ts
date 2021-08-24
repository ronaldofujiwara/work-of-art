jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ResponsavelService } from '../service/responsavel.service';
import { IResponsavel, Responsavel } from '../responsavel.model';

import { ResponsavelUpdateComponent } from './responsavel-update.component';

describe('Component Tests', () => {
  describe('Responsavel Management Update Component', () => {
    let comp: ResponsavelUpdateComponent;
    let fixture: ComponentFixture<ResponsavelUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let responsavelService: ResponsavelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ResponsavelUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ResponsavelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResponsavelUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      responsavelService = TestBed.inject(ResponsavelService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const responsavel: IResponsavel = { id: 456 };

        activatedRoute.data = of({ responsavel });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(responsavel));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Responsavel>>();
        const responsavel = { id: 123 };
        jest.spyOn(responsavelService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ responsavel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: responsavel }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(responsavelService.update).toHaveBeenCalledWith(responsavel);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Responsavel>>();
        const responsavel = new Responsavel();
        jest.spyOn(responsavelService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ responsavel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: responsavel }));
        saveSubject.complete();

        // THEN
        expect(responsavelService.create).toHaveBeenCalledWith(responsavel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Responsavel>>();
        const responsavel = { id: 123 };
        jest.spyOn(responsavelService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ responsavel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(responsavelService.update).toHaveBeenCalledWith(responsavel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
