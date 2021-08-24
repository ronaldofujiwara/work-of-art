jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DadoDocumentalService } from '../service/dado-documental.service';
import { IDadoDocumental, DadoDocumental } from '../dado-documental.model';
import { ITipoDocumento } from 'app/entities/tipo-documento/tipo-documento.model';
import { TipoDocumentoService } from 'app/entities/tipo-documento/service/tipo-documento.service';

import { DadoDocumentalUpdateComponent } from './dado-documental-update.component';

describe('Component Tests', () => {
  describe('DadoDocumental Management Update Component', () => {
    let comp: DadoDocumentalUpdateComponent;
    let fixture: ComponentFixture<DadoDocumentalUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dadoDocumentalService: DadoDocumentalService;
    let tipoDocumentoService: TipoDocumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DadoDocumentalUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DadoDocumentalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DadoDocumentalUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dadoDocumentalService = TestBed.inject(DadoDocumentalService);
      tipoDocumentoService = TestBed.inject(TipoDocumentoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TipoDocumento query and add missing value', () => {
        const dadoDocumental: IDadoDocumental = { id: 456 };
        const tipoDocumento: ITipoDocumento = { id: 42457 };
        dadoDocumental.tipoDocumento = tipoDocumento;

        const tipoDocumentoCollection: ITipoDocumento[] = [{ id: 15210 }];
        jest.spyOn(tipoDocumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoDocumentoCollection })));
        const additionalTipoDocumentos = [tipoDocumento];
        const expectedCollection: ITipoDocumento[] = [...additionalTipoDocumentos, ...tipoDocumentoCollection];
        jest.spyOn(tipoDocumentoService, 'addTipoDocumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ dadoDocumental });
        comp.ngOnInit();

        expect(tipoDocumentoService.query).toHaveBeenCalled();
        expect(tipoDocumentoService.addTipoDocumentoToCollectionIfMissing).toHaveBeenCalledWith(
          tipoDocumentoCollection,
          ...additionalTipoDocumentos
        );
        expect(comp.tipoDocumentosSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const dadoDocumental: IDadoDocumental = { id: 456 };
        const tipoDocumento: ITipoDocumento = { id: 21035 };
        dadoDocumental.tipoDocumento = tipoDocumento;

        activatedRoute.data = of({ dadoDocumental });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dadoDocumental));
        expect(comp.tipoDocumentosSharedCollection).toContain(tipoDocumento);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DadoDocumental>>();
        const dadoDocumental = { id: 123 };
        jest.spyOn(dadoDocumentalService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ dadoDocumental });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dadoDocumental }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dadoDocumentalService.update).toHaveBeenCalledWith(dadoDocumental);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DadoDocumental>>();
        const dadoDocumental = new DadoDocumental();
        jest.spyOn(dadoDocumentalService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ dadoDocumental });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dadoDocumental }));
        saveSubject.complete();

        // THEN
        expect(dadoDocumentalService.create).toHaveBeenCalledWith(dadoDocumental);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DadoDocumental>>();
        const dadoDocumental = { id: 123 };
        jest.spyOn(dadoDocumentalService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ dadoDocumental });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dadoDocumentalService.update).toHaveBeenCalledWith(dadoDocumental);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTipoDocumentoById', () => {
        it('Should return tracked TipoDocumento primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTipoDocumentoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
