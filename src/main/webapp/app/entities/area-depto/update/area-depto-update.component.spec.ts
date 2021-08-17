jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AreaDeptoService } from '../service/area-depto.service';
import { IAreaDepto, AreaDepto } from '../area-depto.model';

import { AreaDeptoUpdateComponent } from './area-depto-update.component';

describe('Component Tests', () => {
  describe('AreaDepto Management Update Component', () => {
    let comp: AreaDeptoUpdateComponent;
    let fixture: ComponentFixture<AreaDeptoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let areaDeptoService: AreaDeptoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AreaDeptoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AreaDeptoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AreaDeptoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      areaDeptoService = TestBed.inject(AreaDeptoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const areaDepto: IAreaDepto = { id: 456 };

        activatedRoute.data = of({ areaDepto });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(areaDepto));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AreaDepto>>();
        const areaDepto = { id: 123 };
        jest.spyOn(areaDeptoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ areaDepto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: areaDepto }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(areaDeptoService.update).toHaveBeenCalledWith(areaDepto);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AreaDepto>>();
        const areaDepto = new AreaDepto();
        jest.spyOn(areaDeptoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ areaDepto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: areaDepto }));
        saveSubject.complete();

        // THEN
        expect(areaDeptoService.create).toHaveBeenCalledWith(areaDepto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AreaDepto>>();
        const areaDepto = { id: 123 };
        jest.spyOn(areaDeptoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ areaDepto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(areaDeptoService.update).toHaveBeenCalledWith(areaDepto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
