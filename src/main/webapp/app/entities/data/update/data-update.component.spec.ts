jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DataService } from '../service/data.service';
import { IData, Data } from '../data.model';

import { DataUpdateComponent } from './data-update.component';

describe('Component Tests', () => {
  describe('Data Management Update Component', () => {
    let comp: DataUpdateComponent;
    let fixture: ComponentFixture<DataUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dataService: DataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DataUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dataService = TestBed.inject(DataService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const data: IData = { id: 456 };

        activatedRoute.data = of({ data });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(data));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Data>>();
        const data = { id: 123 };
        jest.spyOn(dataService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ data });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: data }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dataService.update).toHaveBeenCalledWith(data);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Data>>();
        const data = new Data();
        jest.spyOn(dataService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ data });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: data }));
        saveSubject.complete();

        // THEN
        expect(dataService.create).toHaveBeenCalledWith(data);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Data>>();
        const data = { id: 123 };
        jest.spyOn(dataService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ data });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dataService.update).toHaveBeenCalledWith(data);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
