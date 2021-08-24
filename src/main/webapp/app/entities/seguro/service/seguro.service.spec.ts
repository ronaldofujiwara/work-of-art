import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISeguro, Seguro } from '../seguro.model';

import { SeguroService } from './seguro.service';

describe('Service Tests', () => {
  describe('Seguro Service', () => {
    let service: SeguroService;
    let httpMock: HttpTestingController;
    let elemDefault: ISeguro;
    let expectedResult: ISeguro | ISeguro[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SeguroService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        apolice: 'AAAAAAA',
        obsSeguro: 'AAAAAAA',
        contratoProposta: 'AAAAAAA',
        ciaSeguradora: 'AAAAAAA',
        cnpjSeguradora: 'AAAAAAA',
        susepSeguradora: 'AAAAAAA',
        corretora: 'AAAAAAA',
        cnpjCorretora: 'AAAAAAA',
        susepCorretora: 'AAAAAAA',
        dataInicio: currentDate,
        dataVenc: currentDate,
        inativo: false,
        premio: 'AAAAAAA',
        txConversao: 0,
        genStatusSeguro: 0,
        dataAtualSeguro: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataInicio: currentDate.format(DATE_FORMAT),
            dataVenc: currentDate.format(DATE_FORMAT),
            dataAtualSeguro: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Seguro', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataInicio: currentDate.format(DATE_FORMAT),
            dataVenc: currentDate.format(DATE_FORMAT),
            dataAtualSeguro: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataVenc: currentDate,
            dataAtualSeguro: currentDate,
          },
          returnedFromService
        );

        service.create(new Seguro()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Seguro', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            apolice: 'BBBBBB',
            obsSeguro: 'BBBBBB',
            contratoProposta: 'BBBBBB',
            ciaSeguradora: 'BBBBBB',
            cnpjSeguradora: 'BBBBBB',
            susepSeguradora: 'BBBBBB',
            corretora: 'BBBBBB',
            cnpjCorretora: 'BBBBBB',
            susepCorretora: 'BBBBBB',
            dataInicio: currentDate.format(DATE_FORMAT),
            dataVenc: currentDate.format(DATE_FORMAT),
            inativo: true,
            premio: 'BBBBBB',
            txConversao: 1,
            genStatusSeguro: 1,
            dataAtualSeguro: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataVenc: currentDate,
            dataAtualSeguro: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Seguro', () => {
        const patchObject = Object.assign(
          {
            obsSeguro: 'BBBBBB',
            contratoProposta: 'BBBBBB',
            ciaSeguradora: 'BBBBBB',
            cnpjSeguradora: 'BBBBBB',
            susepSeguradora: 'BBBBBB',
            cnpjCorretora: 'BBBBBB',
            dataVenc: currentDate.format(DATE_FORMAT),
            inativo: true,
            genStatusSeguro: 1,
          },
          new Seguro()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataVenc: currentDate,
            dataAtualSeguro: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Seguro', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            apolice: 'BBBBBB',
            obsSeguro: 'BBBBBB',
            contratoProposta: 'BBBBBB',
            ciaSeguradora: 'BBBBBB',
            cnpjSeguradora: 'BBBBBB',
            susepSeguradora: 'BBBBBB',
            corretora: 'BBBBBB',
            cnpjCorretora: 'BBBBBB',
            susepCorretora: 'BBBBBB',
            dataInicio: currentDate.format(DATE_FORMAT),
            dataVenc: currentDate.format(DATE_FORMAT),
            inativo: true,
            premio: 'BBBBBB',
            txConversao: 1,
            genStatusSeguro: 1,
            dataAtualSeguro: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataVenc: currentDate,
            dataAtualSeguro: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Seguro', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSeguroToCollectionIfMissing', () => {
        it('should add a Seguro to an empty array', () => {
          const seguro: ISeguro = { id: 123 };
          expectedResult = service.addSeguroToCollectionIfMissing([], seguro);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(seguro);
        });

        it('should not add a Seguro to an array that contains it', () => {
          const seguro: ISeguro = { id: 123 };
          const seguroCollection: ISeguro[] = [
            {
              ...seguro,
            },
            { id: 456 },
          ];
          expectedResult = service.addSeguroToCollectionIfMissing(seguroCollection, seguro);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Seguro to an array that doesn't contain it", () => {
          const seguro: ISeguro = { id: 123 };
          const seguroCollection: ISeguro[] = [{ id: 456 }];
          expectedResult = service.addSeguroToCollectionIfMissing(seguroCollection, seguro);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(seguro);
        });

        it('should add only unique Seguro to an array', () => {
          const seguroArray: ISeguro[] = [{ id: 123 }, { id: 456 }, { id: 73504 }];
          const seguroCollection: ISeguro[] = [{ id: 123 }];
          expectedResult = service.addSeguroToCollectionIfMissing(seguroCollection, ...seguroArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const seguro: ISeguro = { id: 123 };
          const seguro2: ISeguro = { id: 456 };
          expectedResult = service.addSeguroToCollectionIfMissing([], seguro, seguro2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(seguro);
          expect(expectedResult).toContain(seguro2);
        });

        it('should accept null and undefined values', () => {
          const seguro: ISeguro = { id: 123 };
          expectedResult = service.addSeguroToCollectionIfMissing([], null, seguro, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(seguro);
        });

        it('should return initial array if no Seguro is added', () => {
          const seguroCollection: ISeguro[] = [{ id: 123 }];
          expectedResult = service.addSeguroToCollectionIfMissing(seguroCollection, undefined, null);
          expect(expectedResult).toEqual(seguroCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
