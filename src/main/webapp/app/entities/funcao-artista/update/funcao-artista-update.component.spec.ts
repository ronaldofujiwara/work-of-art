jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FuncaoArtistaService } from '../service/funcao-artista.service';
import { IFuncaoArtista, FuncaoArtista } from '../funcao-artista.model';

import { FuncaoArtistaUpdateComponent } from './funcao-artista-update.component';

describe('Component Tests', () => {
  describe('FuncaoArtista Management Update Component', () => {
    let comp: FuncaoArtistaUpdateComponent;
    let fixture: ComponentFixture<FuncaoArtistaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let funcaoArtistaService: FuncaoArtistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FuncaoArtistaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FuncaoArtistaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuncaoArtistaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      funcaoArtistaService = TestBed.inject(FuncaoArtistaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const funcaoArtista: IFuncaoArtista = { id: 456 };

        activatedRoute.data = of({ funcaoArtista });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(funcaoArtista));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FuncaoArtista>>();
        const funcaoArtista = { id: 123 };
        jest.spyOn(funcaoArtistaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ funcaoArtista });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: funcaoArtista }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(funcaoArtistaService.update).toHaveBeenCalledWith(funcaoArtista);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FuncaoArtista>>();
        const funcaoArtista = new FuncaoArtista();
        jest.spyOn(funcaoArtistaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ funcaoArtista });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: funcaoArtista }));
        saveSubject.complete();

        // THEN
        expect(funcaoArtistaService.create).toHaveBeenCalledWith(funcaoArtista);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FuncaoArtista>>();
        const funcaoArtista = { id: 123 };
        jest.spyOn(funcaoArtistaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ funcaoArtista });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(funcaoArtistaService.update).toHaveBeenCalledWith(funcaoArtista);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
