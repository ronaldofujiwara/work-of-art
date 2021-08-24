jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TecnicaService } from '../service/tecnica.service';
import { ITecnica, Tecnica } from '../tecnica.model';

import { TecnicaUpdateComponent } from './tecnica-update.component';

describe('Component Tests', () => {
  describe('Tecnica Management Update Component', () => {
    let comp: TecnicaUpdateComponent;
    let fixture: ComponentFixture<TecnicaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let tecnicaService: TecnicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TecnicaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TecnicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TecnicaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      tecnicaService = TestBed.inject(TecnicaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const tecnica: ITecnica = { id: 456 };

        activatedRoute.data = of({ tecnica });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(tecnica));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Tecnica>>();
        const tecnica = { id: 123 };
        jest.spyOn(tecnicaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tecnica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tecnica }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(tecnicaService.update).toHaveBeenCalledWith(tecnica);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Tecnica>>();
        const tecnica = new Tecnica();
        jest.spyOn(tecnicaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tecnica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tecnica }));
        saveSubject.complete();

        // THEN
        expect(tecnicaService.create).toHaveBeenCalledWith(tecnica);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Tecnica>>();
        const tecnica = { id: 123 };
        jest.spyOn(tecnicaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tecnica });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(tecnicaService.update).toHaveBeenCalledWith(tecnica);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
