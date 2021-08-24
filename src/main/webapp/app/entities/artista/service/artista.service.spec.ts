import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IArtista, Artista } from '../artista.model';

import { ArtistaService } from './artista.service';

describe('Service Tests', () => {
  describe('Artista Service', () => {
    let service: ArtistaService;
    let httpMock: HttpTestingController;
    let elemDefault: IArtista;
    let expectedResult: IArtista | IArtista[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ArtistaService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        nome: 'AAAAAAA',
        dataNasc: 'AAAAAAA',
        dataFalec: 'AAAAAAA',
        biografia: 'AAAAAAA',
        verbete: 'AAAAAAA',
        dataAtualBio: currentDate,
        dataAtualVerb: currentDate,
        possuiBio: false,
        possuiVerb: false,
        fonteBio: 'AAAAAAA',
        obrasNoAcervo: 'AAAAAAA',
        inativo: false,
        agradecimentos: 'AAAAAAA',
        copyright: 'AAAAAAA',
        obsUso: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataAtualBio: currentDate.format(DATE_FORMAT),
            dataAtualVerb: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Artista', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataAtualBio: currentDate.format(DATE_FORMAT),
            dataAtualVerb: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAtualBio: currentDate,
            dataAtualVerb: currentDate,
          },
          returnedFromService
        );

        service.create(new Artista()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Artista', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            dataNasc: 'BBBBBB',
            dataFalec: 'BBBBBB',
            biografia: 'BBBBBB',
            verbete: 'BBBBBB',
            dataAtualBio: currentDate.format(DATE_FORMAT),
            dataAtualVerb: currentDate.format(DATE_FORMAT),
            possuiBio: true,
            possuiVerb: true,
            fonteBio: 'BBBBBB',
            obrasNoAcervo: 'BBBBBB',
            inativo: true,
            agradecimentos: 'BBBBBB',
            copyright: 'BBBBBB',
            obsUso: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAtualBio: currentDate,
            dataAtualVerb: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Artista', () => {
        const patchObject = Object.assign(
          {
            dataNasc: 'BBBBBB',
            dataAtualBio: currentDate.format(DATE_FORMAT),
            dataAtualVerb: currentDate.format(DATE_FORMAT),
            fonteBio: 'BBBBBB',
            obrasNoAcervo: 'BBBBBB',
          },
          new Artista()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataAtualBio: currentDate,
            dataAtualVerb: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Artista', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            dataNasc: 'BBBBBB',
            dataFalec: 'BBBBBB',
            biografia: 'BBBBBB',
            verbete: 'BBBBBB',
            dataAtualBio: currentDate.format(DATE_FORMAT),
            dataAtualVerb: currentDate.format(DATE_FORMAT),
            possuiBio: true,
            possuiVerb: true,
            fonteBio: 'BBBBBB',
            obrasNoAcervo: 'BBBBBB',
            inativo: true,
            agradecimentos: 'BBBBBB',
            copyright: 'BBBBBB',
            obsUso: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAtualBio: currentDate,
            dataAtualVerb: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Artista', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addArtistaToCollectionIfMissing', () => {
        it('should add a Artista to an empty array', () => {
          const artista: IArtista = { id: 123 };
          expectedResult = service.addArtistaToCollectionIfMissing([], artista);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(artista);
        });

        it('should not add a Artista to an array that contains it', () => {
          const artista: IArtista = { id: 123 };
          const artistaCollection: IArtista[] = [
            {
              ...artista,
            },
            { id: 456 },
          ];
          expectedResult = service.addArtistaToCollectionIfMissing(artistaCollection, artista);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Artista to an array that doesn't contain it", () => {
          const artista: IArtista = { id: 123 };
          const artistaCollection: IArtista[] = [{ id: 456 }];
          expectedResult = service.addArtistaToCollectionIfMissing(artistaCollection, artista);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(artista);
        });

        it('should add only unique Artista to an array', () => {
          const artistaArray: IArtista[] = [{ id: 123 }, { id: 456 }, { id: 11467 }];
          const artistaCollection: IArtista[] = [{ id: 123 }];
          expectedResult = service.addArtistaToCollectionIfMissing(artistaCollection, ...artistaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const artista: IArtista = { id: 123 };
          const artista2: IArtista = { id: 456 };
          expectedResult = service.addArtistaToCollectionIfMissing([], artista, artista2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(artista);
          expect(expectedResult).toContain(artista2);
        });

        it('should accept null and undefined values', () => {
          const artista: IArtista = { id: 123 };
          expectedResult = service.addArtistaToCollectionIfMissing([], null, artista, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(artista);
        });

        it('should return initial array if no Artista is added', () => {
          const artistaCollection: IArtista[] = [{ id: 123 }];
          expectedResult = service.addArtistaToCollectionIfMissing(artistaCollection, undefined, null);
          expect(expectedResult).toEqual(artistaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
