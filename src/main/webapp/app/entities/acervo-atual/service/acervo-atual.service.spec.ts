import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAcervoAtual, AcervoAtual } from '../acervo-atual.model';

import { AcervoAtualService } from './acervo-atual.service';

describe('Service Tests', () => {
  describe('AcervoAtual Service', () => {
    let service: AcervoAtualService;
    let httpMock: HttpTestingController;
    let elemDefault: IAcervoAtual;
    let expectedResult: IAcervoAtual | IAcervoAtual[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AcervoAtualService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        acervoAtual: 'AAAAAAA',
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

      it('should create a AcervoAtual', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AcervoAtual()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AcervoAtual', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            acervoAtual: 'BBBBBB',
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

      it('should partial update a AcervoAtual', () => {
        const patchObject = Object.assign(
          {
            acervoAtual: 'BBBBBB',
            inativo: true,
          },
          new AcervoAtual()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AcervoAtual', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            acervoAtual: 'BBBBBB',
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

      it('should delete a AcervoAtual', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAcervoAtualToCollectionIfMissing', () => {
        it('should add a AcervoAtual to an empty array', () => {
          const acervoAtual: IAcervoAtual = { id: 123 };
          expectedResult = service.addAcervoAtualToCollectionIfMissing([], acervoAtual);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(acervoAtual);
        });

        it('should not add a AcervoAtual to an array that contains it', () => {
          const acervoAtual: IAcervoAtual = { id: 123 };
          const acervoAtualCollection: IAcervoAtual[] = [
            {
              ...acervoAtual,
            },
            { id: 456 },
          ];
          expectedResult = service.addAcervoAtualToCollectionIfMissing(acervoAtualCollection, acervoAtual);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AcervoAtual to an array that doesn't contain it", () => {
          const acervoAtual: IAcervoAtual = { id: 123 };
          const acervoAtualCollection: IAcervoAtual[] = [{ id: 456 }];
          expectedResult = service.addAcervoAtualToCollectionIfMissing(acervoAtualCollection, acervoAtual);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(acervoAtual);
        });

        it('should add only unique AcervoAtual to an array', () => {
          const acervoAtualArray: IAcervoAtual[] = [{ id: 123 }, { id: 456 }, { id: 92446 }];
          const acervoAtualCollection: IAcervoAtual[] = [{ id: 123 }];
          expectedResult = service.addAcervoAtualToCollectionIfMissing(acervoAtualCollection, ...acervoAtualArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const acervoAtual: IAcervoAtual = { id: 123 };
          const acervoAtual2: IAcervoAtual = { id: 456 };
          expectedResult = service.addAcervoAtualToCollectionIfMissing([], acervoAtual, acervoAtual2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(acervoAtual);
          expect(expectedResult).toContain(acervoAtual2);
        });

        it('should accept null and undefined values', () => {
          const acervoAtual: IAcervoAtual = { id: 123 };
          expectedResult = service.addAcervoAtualToCollectionIfMissing([], null, acervoAtual, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(acervoAtual);
        });

        it('should return initial array if no AcervoAtual is added', () => {
          const acervoAtualCollection: IAcervoAtual[] = [{ id: 123 }];
          expectedResult = service.addAcervoAtualToCollectionIfMissing(acervoAtualCollection, undefined, null);
          expect(expectedResult).toEqual(acervoAtualCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
