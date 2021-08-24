jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmpresaService } from '../service/empresa.service';
import { IEmpresa, Empresa } from '../empresa.model';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';

import { EmpresaUpdateComponent } from './empresa-update.component';

describe('Component Tests', () => {
  describe('Empresa Management Update Component', () => {
    let comp: EmpresaUpdateComponent;
    let fixture: ComponentFixture<EmpresaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let empresaService: EmpresaService;
    let cidadeService: CidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmpresaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EmpresaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpresaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      empresaService = TestBed.inject(EmpresaService);
      cidadeService = TestBed.inject(CidadeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Cidade query and add missing value', () => {
        const empresa: IEmpresa = { id: 456 };
        const cidade: ICidade = { id: 51006 };
        empresa.cidade = cidade;

        const cidadeCollection: ICidade[] = [{ id: 14495 }];
        jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
        const additionalCidades = [cidade];
        const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
        jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ empresa });
        comp.ngOnInit();

        expect(cidadeService.query).toHaveBeenCalled();
        expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(cidadeCollection, ...additionalCidades);
        expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const empresa: IEmpresa = { id: 456 };
        const cidade: ICidade = { id: 52017 };
        empresa.cidade = cidade;

        activatedRoute.data = of({ empresa });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(empresa));
        expect(comp.cidadesSharedCollection).toContain(cidade);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Empresa>>();
        const empresa = { id: 123 };
        jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ empresa });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: empresa }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(empresaService.update).toHaveBeenCalledWith(empresa);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Empresa>>();
        const empresa = new Empresa();
        jest.spyOn(empresaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ empresa });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: empresa }));
        saveSubject.complete();

        // THEN
        expect(empresaService.create).toHaveBeenCalledWith(empresa);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Empresa>>();
        const empresa = { id: 123 };
        jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ empresa });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(empresaService.update).toHaveBeenCalledWith(empresa);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCidadeById', () => {
        it('Should return tracked Cidade primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCidadeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
