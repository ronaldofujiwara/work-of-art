import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAreaDepto, AreaDepto } from '../area-depto.model';

import { AreaDeptoService } from './area-depto.service';

describe('Service Tests', () => {
  describe('AreaDepto Service', () => {
    let service: AreaDeptoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAreaDepto;
    let expectedResult: IAreaDepto | IAreaDepto[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AreaDeptoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        area: 'AAAAAAA',
        ativo: false,
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

      it('should create a AreaDepto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AreaDepto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AreaDepto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            area: 'BBBBBB',
            ativo: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a AreaDepto', () => {
        const patchObject = Object.assign(
          {
            ativo: true,
          },
          new AreaDepto()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AreaDepto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            area: 'BBBBBB',
            ativo: true,
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

      it('should delete a AreaDepto', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAreaDeptoToCollectionIfMissing', () => {
        it('should add a AreaDepto to an empty array', () => {
          const areaDepto: IAreaDepto = { id: 123 };
          expectedResult = service.addAreaDeptoToCollectionIfMissing([], areaDepto);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(areaDepto);
        });

        it('should not add a AreaDepto to an array that contains it', () => {
          const areaDepto: IAreaDepto = { id: 123 };
          const areaDeptoCollection: IAreaDepto[] = [
            {
              ...areaDepto,
            },
            { id: 456 },
          ];
          expectedResult = service.addAreaDeptoToCollectionIfMissing(areaDeptoCollection, areaDepto);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AreaDepto to an array that doesn't contain it", () => {
          const areaDepto: IAreaDepto = { id: 123 };
          const areaDeptoCollection: IAreaDepto[] = [{ id: 456 }];
          expectedResult = service.addAreaDeptoToCollectionIfMissing(areaDeptoCollection, areaDepto);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(areaDepto);
        });

        it('should add only unique AreaDepto to an array', () => {
          const areaDeptoArray: IAreaDepto[] = [{ id: 123 }, { id: 456 }, { id: 48813 }];
          const areaDeptoCollection: IAreaDepto[] = [{ id: 123 }];
          expectedResult = service.addAreaDeptoToCollectionIfMissing(areaDeptoCollection, ...areaDeptoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const areaDepto: IAreaDepto = { id: 123 };
          const areaDepto2: IAreaDepto = { id: 456 };
          expectedResult = service.addAreaDeptoToCollectionIfMissing([], areaDepto, areaDepto2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(areaDepto);
          expect(expectedResult).toContain(areaDepto2);
        });

        it('should accept null and undefined values', () => {
          const areaDepto: IAreaDepto = { id: 123 };
          expectedResult = service.addAreaDeptoToCollectionIfMissing([], null, areaDepto, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(areaDepto);
        });

        it('should return initial array if no AreaDepto is added', () => {
          const areaDeptoCollection: IAreaDepto[] = [{ id: 123 }];
          expectedResult = service.addAreaDeptoToCollectionIfMissing(areaDeptoCollection, undefined, null);
          expect(expectedResult).toEqual(areaDeptoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
