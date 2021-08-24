import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFuncaoArtista, FuncaoArtista } from '../funcao-artista.model';

import { FuncaoArtistaService } from './funcao-artista.service';

describe('Service Tests', () => {
  describe('FuncaoArtista Service', () => {
    let service: FuncaoArtistaService;
    let httpMock: HttpTestingController;
    let elemDefault: IFuncaoArtista;
    let expectedResult: IFuncaoArtista | IFuncaoArtista[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FuncaoArtistaService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        funcaoArtista: 'AAAAAAA',
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

      it('should create a FuncaoArtista', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FuncaoArtista()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FuncaoArtista', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            funcaoArtista: 'BBBBBB',
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

      it('should partial update a FuncaoArtista', () => {
        const patchObject = Object.assign(
          {
            funcaoArtista: 'BBBBBB',
            inativo: true,
          },
          new FuncaoArtista()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FuncaoArtista', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            funcaoArtista: 'BBBBBB',
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

      it('should delete a FuncaoArtista', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFuncaoArtistaToCollectionIfMissing', () => {
        it('should add a FuncaoArtista to an empty array', () => {
          const funcaoArtista: IFuncaoArtista = { id: 123 };
          expectedResult = service.addFuncaoArtistaToCollectionIfMissing([], funcaoArtista);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(funcaoArtista);
        });

        it('should not add a FuncaoArtista to an array that contains it', () => {
          const funcaoArtista: IFuncaoArtista = { id: 123 };
          const funcaoArtistaCollection: IFuncaoArtista[] = [
            {
              ...funcaoArtista,
            },
            { id: 456 },
          ];
          expectedResult = service.addFuncaoArtistaToCollectionIfMissing(funcaoArtistaCollection, funcaoArtista);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FuncaoArtista to an array that doesn't contain it", () => {
          const funcaoArtista: IFuncaoArtista = { id: 123 };
          const funcaoArtistaCollection: IFuncaoArtista[] = [{ id: 456 }];
          expectedResult = service.addFuncaoArtistaToCollectionIfMissing(funcaoArtistaCollection, funcaoArtista);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(funcaoArtista);
        });

        it('should add only unique FuncaoArtista to an array', () => {
          const funcaoArtistaArray: IFuncaoArtista[] = [{ id: 123 }, { id: 456 }, { id: 63371 }];
          const funcaoArtistaCollection: IFuncaoArtista[] = [{ id: 123 }];
          expectedResult = service.addFuncaoArtistaToCollectionIfMissing(funcaoArtistaCollection, ...funcaoArtistaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const funcaoArtista: IFuncaoArtista = { id: 123 };
          const funcaoArtista2: IFuncaoArtista = { id: 456 };
          expectedResult = service.addFuncaoArtistaToCollectionIfMissing([], funcaoArtista, funcaoArtista2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(funcaoArtista);
          expect(expectedResult).toContain(funcaoArtista2);
        });

        it('should accept null and undefined values', () => {
          const funcaoArtista: IFuncaoArtista = { id: 123 };
          expectedResult = service.addFuncaoArtistaToCollectionIfMissing([], null, funcaoArtista, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(funcaoArtista);
        });

        it('should return initial array if no FuncaoArtista is added', () => {
          const funcaoArtistaCollection: IFuncaoArtista[] = [{ id: 123 }];
          expectedResult = service.addFuncaoArtistaToCollectionIfMissing(funcaoArtistaCollection, undefined, null);
          expect(expectedResult).toEqual(funcaoArtistaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
