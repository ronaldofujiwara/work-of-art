import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INivel, Nivel } from '../nivel.model';

import { NivelService } from './nivel.service';

describe('Service Tests', () => {
  describe('Nivel Service', () => {
    let service: NivelService;
    let httpMock: HttpTestingController;
    let elemDefault: INivel;
    let expectedResult: INivel | INivel[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NivelService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nivel: 'AAAAAAA',
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

      it('should create a Nivel', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Nivel()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Nivel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nivel: 'BBBBBB',
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

      it('should partial update a Nivel', () => {
        const patchObject = Object.assign(
          {
            nivel: 'BBBBBB',
          },
          new Nivel()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Nivel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nivel: 'BBBBBB',
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

      it('should delete a Nivel', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNivelToCollectionIfMissing', () => {
        it('should add a Nivel to an empty array', () => {
          const nivel: INivel = { id: 123 };
          expectedResult = service.addNivelToCollectionIfMissing([], nivel);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nivel);
        });

        it('should not add a Nivel to an array that contains it', () => {
          const nivel: INivel = { id: 123 };
          const nivelCollection: INivel[] = [
            {
              ...nivel,
            },
            { id: 456 },
          ];
          expectedResult = service.addNivelToCollectionIfMissing(nivelCollection, nivel);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Nivel to an array that doesn't contain it", () => {
          const nivel: INivel = { id: 123 };
          const nivelCollection: INivel[] = [{ id: 456 }];
          expectedResult = service.addNivelToCollectionIfMissing(nivelCollection, nivel);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nivel);
        });

        it('should add only unique Nivel to an array', () => {
          const nivelArray: INivel[] = [{ id: 123 }, { id: 456 }, { id: 54086 }];
          const nivelCollection: INivel[] = [{ id: 123 }];
          expectedResult = service.addNivelToCollectionIfMissing(nivelCollection, ...nivelArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nivel: INivel = { id: 123 };
          const nivel2: INivel = { id: 456 };
          expectedResult = service.addNivelToCollectionIfMissing([], nivel, nivel2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nivel);
          expect(expectedResult).toContain(nivel2);
        });

        it('should accept null and undefined values', () => {
          const nivel: INivel = { id: 123 };
          expectedResult = service.addNivelToCollectionIfMissing([], null, nivel, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nivel);
        });

        it('should return initial array if no Nivel is added', () => {
          const nivelCollection: INivel[] = [{ id: 123 }];
          expectedResult = service.addNivelToCollectionIfMissing(nivelCollection, undefined, null);
          expect(expectedResult).toEqual(nivelCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
