import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEspaco, Espaco } from '../espaco.model';

import { EspacoService } from './espaco.service';

describe('Service Tests', () => {
  describe('Espaco Service', () => {
    let service: EspacoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEspaco;
    let expectedResult: IEspaco | IEspaco[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EspacoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        espaco: 'AAAAAAA',
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

      it('should create a Espaco', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Espaco()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Espaco', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            espaco: 'BBBBBB',
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

      it('should partial update a Espaco', () => {
        const patchObject = Object.assign({}, new Espaco());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Espaco', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            espaco: 'BBBBBB',
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

      it('should delete a Espaco', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEspacoToCollectionIfMissing', () => {
        it('should add a Espaco to an empty array', () => {
          const espaco: IEspaco = { id: 123 };
          expectedResult = service.addEspacoToCollectionIfMissing([], espaco);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(espaco);
        });

        it('should not add a Espaco to an array that contains it', () => {
          const espaco: IEspaco = { id: 123 };
          const espacoCollection: IEspaco[] = [
            {
              ...espaco,
            },
            { id: 456 },
          ];
          expectedResult = service.addEspacoToCollectionIfMissing(espacoCollection, espaco);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Espaco to an array that doesn't contain it", () => {
          const espaco: IEspaco = { id: 123 };
          const espacoCollection: IEspaco[] = [{ id: 456 }];
          expectedResult = service.addEspacoToCollectionIfMissing(espacoCollection, espaco);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(espaco);
        });

        it('should add only unique Espaco to an array', () => {
          const espacoArray: IEspaco[] = [{ id: 123 }, { id: 456 }, { id: 72721 }];
          const espacoCollection: IEspaco[] = [{ id: 123 }];
          expectedResult = service.addEspacoToCollectionIfMissing(espacoCollection, ...espacoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const espaco: IEspaco = { id: 123 };
          const espaco2: IEspaco = { id: 456 };
          expectedResult = service.addEspacoToCollectionIfMissing([], espaco, espaco2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(espaco);
          expect(expectedResult).toContain(espaco2);
        });

        it('should accept null and undefined values', () => {
          const espaco: IEspaco = { id: 123 };
          expectedResult = service.addEspacoToCollectionIfMissing([], null, espaco, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(espaco);
        });

        it('should return initial array if no Espaco is added', () => {
          const espacoCollection: IEspaco[] = [{ id: 123 }];
          expectedResult = service.addEspacoToCollectionIfMissing(espacoCollection, undefined, null);
          expect(expectedResult).toEqual(espacoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
