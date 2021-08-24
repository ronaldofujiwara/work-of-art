import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResponsavel, Responsavel } from '../responsavel.model';

import { ResponsavelService } from './responsavel.service';

describe('Service Tests', () => {
  describe('Responsavel Service', () => {
    let service: ResponsavelService;
    let httpMock: HttpTestingController;
    let elemDefault: IResponsavel;
    let expectedResult: IResponsavel | IResponsavel[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ResponsavelService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nome: 'AAAAAAA',
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

      it('should create a Responsavel', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Responsavel()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Responsavel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
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

      it('should partial update a Responsavel', () => {
        const patchObject = Object.assign(
          {
            inativo: true,
          },
          new Responsavel()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Responsavel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
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

      it('should delete a Responsavel', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addResponsavelToCollectionIfMissing', () => {
        it('should add a Responsavel to an empty array', () => {
          const responsavel: IResponsavel = { id: 123 };
          expectedResult = service.addResponsavelToCollectionIfMissing([], responsavel);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(responsavel);
        });

        it('should not add a Responsavel to an array that contains it', () => {
          const responsavel: IResponsavel = { id: 123 };
          const responsavelCollection: IResponsavel[] = [
            {
              ...responsavel,
            },
            { id: 456 },
          ];
          expectedResult = service.addResponsavelToCollectionIfMissing(responsavelCollection, responsavel);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Responsavel to an array that doesn't contain it", () => {
          const responsavel: IResponsavel = { id: 123 };
          const responsavelCollection: IResponsavel[] = [{ id: 456 }];
          expectedResult = service.addResponsavelToCollectionIfMissing(responsavelCollection, responsavel);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(responsavel);
        });

        it('should add only unique Responsavel to an array', () => {
          const responsavelArray: IResponsavel[] = [{ id: 123 }, { id: 456 }, { id: 47942 }];
          const responsavelCollection: IResponsavel[] = [{ id: 123 }];
          expectedResult = service.addResponsavelToCollectionIfMissing(responsavelCollection, ...responsavelArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const responsavel: IResponsavel = { id: 123 };
          const responsavel2: IResponsavel = { id: 456 };
          expectedResult = service.addResponsavelToCollectionIfMissing([], responsavel, responsavel2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(responsavel);
          expect(expectedResult).toContain(responsavel2);
        });

        it('should accept null and undefined values', () => {
          const responsavel: IResponsavel = { id: 123 };
          expectedResult = service.addResponsavelToCollectionIfMissing([], null, responsavel, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(responsavel);
        });

        it('should return initial array if no Responsavel is added', () => {
          const responsavelCollection: IResponsavel[] = [{ id: 123 }];
          expectedResult = service.addResponsavelToCollectionIfMissing(responsavelCollection, undefined, null);
          expect(expectedResult).toEqual(responsavelCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
