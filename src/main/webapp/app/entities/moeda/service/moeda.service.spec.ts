import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMoeda, Moeda } from '../moeda.model';

import { MoedaService } from './moeda.service';

describe('Service Tests', () => {
  describe('Moeda Service', () => {
    let service: MoedaService;
    let httpMock: HttpTestingController;
    let elemDefault: IMoeda;
    let expectedResult: IMoeda | IMoeda[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MoedaService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tipoMoeda: 'AAAAAAA',
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

      it('should create a Moeda', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Moeda()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Moeda', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tipoMoeda: 'BBBBBB',
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

      it('should partial update a Moeda', () => {
        const patchObject = Object.assign(
          {
            tipoMoeda: 'BBBBBB',
            inativo: true,
          },
          new Moeda()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Moeda', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tipoMoeda: 'BBBBBB',
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

      it('should delete a Moeda', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMoedaToCollectionIfMissing', () => {
        it('should add a Moeda to an empty array', () => {
          const moeda: IMoeda = { id: 123 };
          expectedResult = service.addMoedaToCollectionIfMissing([], moeda);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(moeda);
        });

        it('should not add a Moeda to an array that contains it', () => {
          const moeda: IMoeda = { id: 123 };
          const moedaCollection: IMoeda[] = [
            {
              ...moeda,
            },
            { id: 456 },
          ];
          expectedResult = service.addMoedaToCollectionIfMissing(moedaCollection, moeda);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Moeda to an array that doesn't contain it", () => {
          const moeda: IMoeda = { id: 123 };
          const moedaCollection: IMoeda[] = [{ id: 456 }];
          expectedResult = service.addMoedaToCollectionIfMissing(moedaCollection, moeda);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(moeda);
        });

        it('should add only unique Moeda to an array', () => {
          const moedaArray: IMoeda[] = [{ id: 123 }, { id: 456 }, { id: 11133 }];
          const moedaCollection: IMoeda[] = [{ id: 123 }];
          expectedResult = service.addMoedaToCollectionIfMissing(moedaCollection, ...moedaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const moeda: IMoeda = { id: 123 };
          const moeda2: IMoeda = { id: 456 };
          expectedResult = service.addMoedaToCollectionIfMissing([], moeda, moeda2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(moeda);
          expect(expectedResult).toContain(moeda2);
        });

        it('should accept null and undefined values', () => {
          const moeda: IMoeda = { id: 123 };
          expectedResult = service.addMoedaToCollectionIfMissing([], null, moeda, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(moeda);
        });

        it('should return initial array if no Moeda is added', () => {
          const moedaCollection: IMoeda[] = [{ id: 123 }];
          expectedResult = service.addMoedaToCollectionIfMissing(moedaCollection, undefined, null);
          expect(expectedResult).toEqual(moedaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
