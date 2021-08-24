import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDadoDocumental, DadoDocumental } from '../dado-documental.model';

import { DadoDocumentalService } from './dado-documental.service';

describe('Service Tests', () => {
  describe('DadoDocumental Service', () => {
    let service: DadoDocumentalService;
    let httpMock: HttpTestingController;
    let elemDefault: IDadoDocumental;
    let expectedResult: IDadoDocumental | IDadoDocumental[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DadoDocumentalService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        data: 'AAAAAAA',
        emissor: 'AAAAAAA',
        receptor: 'AAAAAAA',
        obs: 'AAAAAAA',
        transcricao: 'AAAAAAA',
        finalizado: false,
        genTranscricao: 0,
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

      it('should create a DadoDocumental', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DadoDocumental()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DadoDocumental', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            data: 'BBBBBB',
            emissor: 'BBBBBB',
            receptor: 'BBBBBB',
            obs: 'BBBBBB',
            transcricao: 'BBBBBB',
            finalizado: true,
            genTranscricao: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DadoDocumental', () => {
        const patchObject = Object.assign(
          {
            data: 'BBBBBB',
            receptor: 'BBBBBB',
            obs: 'BBBBBB',
            transcricao: 'BBBBBB',
            finalizado: true,
            genTranscricao: 1,
          },
          new DadoDocumental()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DadoDocumental', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            data: 'BBBBBB',
            emissor: 'BBBBBB',
            receptor: 'BBBBBB',
            obs: 'BBBBBB',
            transcricao: 'BBBBBB',
            finalizado: true,
            genTranscricao: 1,
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

      it('should delete a DadoDocumental', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDadoDocumentalToCollectionIfMissing', () => {
        it('should add a DadoDocumental to an empty array', () => {
          const dadoDocumental: IDadoDocumental = { id: 123 };
          expectedResult = service.addDadoDocumentalToCollectionIfMissing([], dadoDocumental);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dadoDocumental);
        });

        it('should not add a DadoDocumental to an array that contains it', () => {
          const dadoDocumental: IDadoDocumental = { id: 123 };
          const dadoDocumentalCollection: IDadoDocumental[] = [
            {
              ...dadoDocumental,
            },
            { id: 456 },
          ];
          expectedResult = service.addDadoDocumentalToCollectionIfMissing(dadoDocumentalCollection, dadoDocumental);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DadoDocumental to an array that doesn't contain it", () => {
          const dadoDocumental: IDadoDocumental = { id: 123 };
          const dadoDocumentalCollection: IDadoDocumental[] = [{ id: 456 }];
          expectedResult = service.addDadoDocumentalToCollectionIfMissing(dadoDocumentalCollection, dadoDocumental);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dadoDocumental);
        });

        it('should add only unique DadoDocumental to an array', () => {
          const dadoDocumentalArray: IDadoDocumental[] = [{ id: 123 }, { id: 456 }, { id: 87032 }];
          const dadoDocumentalCollection: IDadoDocumental[] = [{ id: 123 }];
          expectedResult = service.addDadoDocumentalToCollectionIfMissing(dadoDocumentalCollection, ...dadoDocumentalArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dadoDocumental: IDadoDocumental = { id: 123 };
          const dadoDocumental2: IDadoDocumental = { id: 456 };
          expectedResult = service.addDadoDocumentalToCollectionIfMissing([], dadoDocumental, dadoDocumental2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dadoDocumental);
          expect(expectedResult).toContain(dadoDocumental2);
        });

        it('should accept null and undefined values', () => {
          const dadoDocumental: IDadoDocumental = { id: 123 };
          expectedResult = service.addDadoDocumentalToCollectionIfMissing([], null, dadoDocumental, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dadoDocumental);
        });

        it('should return initial array if no DadoDocumental is added', () => {
          const dadoDocumentalCollection: IDadoDocumental[] = [{ id: 123 }];
          expectedResult = service.addDadoDocumentalToCollectionIfMissing(dadoDocumentalCollection, undefined, null);
          expect(expectedResult).toEqual(dadoDocumentalCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
