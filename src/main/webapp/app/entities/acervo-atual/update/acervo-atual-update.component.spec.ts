jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AcervoAtualService } from '../service/acervo-atual.service';
import { IAcervoAtual, AcervoAtual } from '../acervo-atual.model';

import { AcervoAtualUpdateComponent } from './acervo-atual-update.component';

describe('Component Tests', () => {
  describe('AcervoAtual Management Update Component', () => {
    let comp: AcervoAtualUpdateComponent;
    let fixture: ComponentFixture<AcervoAtualUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let acervoAtualService: AcervoAtualService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AcervoAtualUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AcervoAtualUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcervoAtualUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      acervoAtualService = TestBed.inject(AcervoAtualService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const acervoAtual: IAcervoAtual = { id: 456 };

        activatedRoute.data = of({ acervoAtual });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(acervoAtual));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AcervoAtual>>();
        const acervoAtual = { id: 123 };
        jest.spyOn(acervoAtualService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ acervoAtual });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: acervoAtual }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(acervoAtualService.update).toHaveBeenCalledWith(acervoAtual);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AcervoAtual>>();
        const acervoAtual = new AcervoAtual();
        jest.spyOn(acervoAtualService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ acervoAtual });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: acervoAtual }));
        saveSubject.complete();

        // THEN
        expect(acervoAtualService.create).toHaveBeenCalledWith(acervoAtual);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AcervoAtual>>();
        const acervoAtual = { id: 123 };
        jest.spyOn(acervoAtualService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ acervoAtual });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(acervoAtualService.update).toHaveBeenCalledWith(acervoAtual);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
