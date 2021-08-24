import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITecnica, Tecnica } from '../tecnica.model';

import { TecnicaService } from './tecnica.service';

describe('Service Tests', () => {
  describe('Tecnica Service', () => {
    let service: TecnicaService;
    let httpMock: HttpTestingController;
    let elemDefault: ITecnica;
    let expectedResult: ITecnica | ITecnica[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TecnicaService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tecnica: 'AAAAAAA',
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

      it('should create a Tecnica', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Tecnica()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Tecnica', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tecnica: 'BBBBBB',
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

      it('should partial update a Tecnica', () => {
        const patchObject = Object.assign({}, new Tecnica());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Tecnica', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tecnica: 'BBBBBB',
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

      it('should delete a Tecnica', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTecnicaToCollectionIfMissing', () => {
        it('should add a Tecnica to an empty array', () => {
          const tecnica: ITecnica = { id: 123 };
          expectedResult = service.addTecnicaToCollectionIfMissing([], tecnica);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tecnica);
        });

        it('should not add a Tecnica to an array that contains it', () => {
          const tecnica: ITecnica = { id: 123 };
          const tecnicaCollection: ITecnica[] = [
            {
              ...tecnica,
            },
            { id: 456 },
          ];
          expectedResult = service.addTecnicaToCollectionIfMissing(tecnicaCollection, tecnica);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Tecnica to an array that doesn't contain it", () => {
          const tecnica: ITecnica = { id: 123 };
          const tecnicaCollection: ITecnica[] = [{ id: 456 }];
          expectedResult = service.addTecnicaToCollectionIfMissing(tecnicaCollection, tecnica);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tecnica);
        });

        it('should add only unique Tecnica to an array', () => {
          const tecnicaArray: ITecnica[] = [{ id: 123 }, { id: 456 }, { id: 58952 }];
          const tecnicaCollection: ITecnica[] = [{ id: 123 }];
          expectedResult = service.addTecnicaToCollectionIfMissing(tecnicaCollection, ...tecnicaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const tecnica: ITecnica = { id: 123 };
          const tecnica2: ITecnica = { id: 456 };
          expectedResult = service.addTecnicaToCollectionIfMissing([], tecnica, tecnica2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tecnica);
          expect(expectedResult).toContain(tecnica2);
        });

        it('should accept null and undefined values', () => {
          const tecnica: ITecnica = { id: 123 };
          expectedResult = service.addTecnicaToCollectionIfMissing([], null, tecnica, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tecnica);
        });

        it('should return initial array if no Tecnica is added', () => {
          const tecnicaCollection: ITecnica[] = [{ id: 123 }];
          expectedResult = service.addTecnicaToCollectionIfMissing(tecnicaCollection, undefined, null);
          expect(expectedResult).toEqual(tecnicaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
