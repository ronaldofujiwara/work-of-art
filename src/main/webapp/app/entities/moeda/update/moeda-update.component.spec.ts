jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MoedaService } from '../service/moeda.service';
import { IMoeda, Moeda } from '../moeda.model';

import { MoedaUpdateComponent } from './moeda-update.component';

describe('Component Tests', () => {
  describe('Moeda Management Update Component', () => {
    let comp: MoedaUpdateComponent;
    let fixture: ComponentFixture<MoedaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let moedaService: MoedaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MoedaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MoedaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MoedaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      moedaService = TestBed.inject(MoedaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const moeda: IMoeda = { id: 456 };

        activatedRoute.data = of({ moeda });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(moeda));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Moeda>>();
        const moeda = { id: 123 };
        jest.spyOn(moedaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ moeda });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: moeda }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(moedaService.update).toHaveBeenCalledWith(moeda);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Moeda>>();
        const moeda = new Moeda();
        jest.spyOn(moedaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ moeda });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: moeda }));
        saveSubject.complete();

        // THEN
        expect(moedaService.create).toHaveBeenCalledWith(moeda);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Moeda>>();
        const moeda = { id: 123 };
        jest.spyOn(moedaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ moeda });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(moedaService.update).toHaveBeenCalledWith(moeda);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
