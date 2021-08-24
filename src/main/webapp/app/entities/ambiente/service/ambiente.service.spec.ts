import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAmbiente, Ambiente } from '../ambiente.model';

import { AmbienteService } from './ambiente.service';

describe('Service Tests', () => {
  describe('Ambiente Service', () => {
    let service: AmbienteService;
    let httpMock: HttpTestingController;
    let elemDefault: IAmbiente;
    let expectedResult: IAmbiente | IAmbiente[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AmbienteService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        ambiente: 'AAAAAAA',
        inativo: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Ambiente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Ambiente()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Ambiente', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ambiente: 'BBBBBB',
            inativo: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Ambiente', () => {
        const patchObject = Object.assign(
          {
            ambiente: 'BBBBBB',
          },
          new Ambiente()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Ambiente', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ambiente: 'BBBBBB',
            inativo: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Ambiente', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAmbienteToCollectionIfMissing', () => {
        it('should add a Ambiente to an empty array', () => {
          const ambiente: IAmbiente = { id: 123 };
          expectedResult = service.addAmbienteToCollectionIfMissing([], ambiente);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ambiente);
        });

        it('should not add a Ambiente to an array that contains it', () => {
          const ambiente: IAmbiente = { id: 123 };
          const ambienteCollection: IAmbiente[] = [
            {
              ...ambiente,
            },
            { id: 456 },
          ];
          expectedResult = service.addAmbienteToCollectionIfMissing(ambienteCollection, ambiente);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Ambiente to an array that doesn't contain it", () => {
          const ambiente: IAmbiente = { id: 123 };
          const ambienteCollection: IAmbiente[] = [{ id: 456 }];
          expectedResult = service.addAmbienteToCollectionIfMissing(ambienteCollection, ambiente);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ambiente);
        });

        it('should add only unique Ambiente to an array', () => {
          const ambienteArray: IAmbiente[] = [{ id: 123 }, { id: 456 }, { id: 138 }];
          const ambienteCollection: IAmbiente[] = [{ id: 123 }];
          expectedResult = service.addAmbienteToCollectionIfMissing(ambienteCollection, ...ambienteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ambiente: IAmbiente = { id: 123 };
          const ambiente2: IAmbiente = { id: 456 };
          expectedResult = service.addAmbienteToCollectionIfMissing([], ambiente, ambiente2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ambiente);
          expect(expectedResult).toContain(ambiente2);
        });

        it('should accept null and undefined values', () => {
          const ambiente: IAmbiente = { id: 123 };
          expectedResult = service.addAmbienteToCollectionIfMissing([], null, ambiente, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ambiente);
        });

        it('should return initial array if no Ambiente is added', () => {
          const ambienteCollection: IAmbiente[] = [{ id: 123 }];
          expectedResult = service.addAmbienteToCollectionIfMissing(ambienteCollection, undefined, null);
          expect(expectedResult).toEqual(ambienteCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
