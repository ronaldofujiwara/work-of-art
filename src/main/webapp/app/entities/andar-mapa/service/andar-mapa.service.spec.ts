import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAndarMapa, AndarMapa } from '../andar-mapa.model';

import { AndarMapaService } from './andar-mapa.service';

describe('Service Tests', () => {
  describe('AndarMapa Service', () => {
    let service: AndarMapaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAndarMapa;
    let expectedResult: IAndarMapa | IAndarMapa[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AndarMapaService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        imagemMapa: 'AAAAAAA',
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

      it('should create a AndarMapa', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AndarMapa()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AndarMapa', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            imagemMapa: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a AndarMapa', () => {
        const patchObject = Object.assign({}, new AndarMapa());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AndarMapa', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            imagemMapa: 'BBBBBB',
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

      it('should delete a AndarMapa', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAndarMapaToCollectionIfMissing', () => {
        it('should add a AndarMapa to an empty array', () => {
          const andarMapa: IAndarMapa = { id: 123 };
          expectedResult = service.addAndarMapaToCollectionIfMissing([], andarMapa);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(andarMapa);
        });

        it('should not add a AndarMapa to an array that contains it', () => {
          const andarMapa: IAndarMapa = { id: 123 };
          const andarMapaCollection: IAndarMapa[] = [
            {
              ...andarMapa,
            },
            { id: 456 },
          ];
          expectedResult = service.addAndarMapaToCollectionIfMissing(andarMapaCollection, andarMapa);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AndarMapa to an array that doesn't contain it", () => {
          const andarMapa: IAndarMapa = { id: 123 };
          const andarMapaCollection: IAndarMapa[] = [{ id: 456 }];
          expectedResult = service.addAndarMapaToCollectionIfMissing(andarMapaCollection, andarMapa);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(andarMapa);
        });

        it('should add only unique AndarMapa to an array', () => {
          const andarMapaArray: IAndarMapa[] = [{ id: 123 }, { id: 456 }, { id: 37387 }];
          const andarMapaCollection: IAndarMapa[] = [{ id: 123 }];
          expectedResult = service.addAndarMapaToCollectionIfMissing(andarMapaCollection, ...andarMapaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const andarMapa: IAndarMapa = { id: 123 };
          const andarMapa2: IAndarMapa = { id: 456 };
          expectedResult = service.addAndarMapaToCollectionIfMissing([], andarMapa, andarMapa2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(andarMapa);
          expect(expectedResult).toContain(andarMapa2);
        });

        it('should accept null and undefined values', () => {
          const andarMapa: IAndarMapa = { id: 123 };
          expectedResult = service.addAndarMapaToCollectionIfMissing([], null, andarMapa, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(andarMapa);
        });

        it('should return initial array if no AndarMapa is added', () => {
          const andarMapaCollection: IAndarMapa[] = [{ id: 123 }];
          expectedResult = service.addAndarMapaToCollectionIfMissing(andarMapaCollection, undefined, null);
          expect(expectedResult).toEqual(andarMapaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
