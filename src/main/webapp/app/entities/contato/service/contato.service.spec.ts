import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContato, Contato } from '../contato.model';

import { ContatoService } from './contato.service';

describe('Service Tests', () => {
  describe('Contato Service', () => {
    let service: ContatoService;
    let httpMock: HttpTestingController;
    let elemDefault: IContato;
    let expectedResult: IContato | IContato[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ContatoService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        nomeComp: 'AAAAAAA',
        empresa: 'AAAAAAA',
        funcao: 'AAAAAAA',
        rg: 'AAAAAAA',
        cpf: 'AAAAAAA',
        infoContato: 'AAAAAAA',
        endRua: 'AAAAAAA',
        endNumero: 'AAAAAAA',
        endBairro: 'AAAAAAA',
        endComplemento: 'AAAAAAA',
        endCep: 'AAAAAAA',
        telefone: 'AAAAAAA',
        fax: 'AAAAAAA',
        celular: 'AAAAAAA',
        email: 'AAAAAAA',
        site: 'AAAAAAA',
        observacoes: 'AAAAAAA',
        dataAtualizacao: currentDate,
        ativo: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataAtualizacao: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Contato', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataAtualizacao: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAtualizacao: currentDate,
          },
          returnedFromService
        );

        service.create(new Contato()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Contato', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nomeComp: 'BBBBBB',
            empresa: 'BBBBBB',
            funcao: 'BBBBBB',
            rg: 'BBBBBB',
            cpf: 'BBBBBB',
            infoContato: 'BBBBBB',
            endRua: 'BBBBBB',
            endNumero: 'BBBBBB',
            endBairro: 'BBBBBB',
            endComplemento: 'BBBBBB',
            endCep: 'BBBBBB',
            telefone: 'BBBBBB',
            fax: 'BBBBBB',
            celular: 'BBBBBB',
            email: 'BBBBBB',
            site: 'BBBBBB',
            observacoes: 'BBBBBB',
            dataAtualizacao: currentDate.format(DATE_TIME_FORMAT),
            ativo: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAtualizacao: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Contato', () => {
        const patchObject = Object.assign(
          {
            infoContato: 'BBBBBB',
            endBairro: 'BBBBBB',
            fax: 'BBBBBB',
            email: 'BBBBBB',
            site: 'BBBBBB',
            dataAtualizacao: currentDate.format(DATE_TIME_FORMAT),
            ativo: true,
          },
          new Contato()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataAtualizacao: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Contato', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nomeComp: 'BBBBBB',
            empresa: 'BBBBBB',
            funcao: 'BBBBBB',
            rg: 'BBBBBB',
            cpf: 'BBBBBB',
            infoContato: 'BBBBBB',
            endRua: 'BBBBBB',
            endNumero: 'BBBBBB',
            endBairro: 'BBBBBB',
            endComplemento: 'BBBBBB',
            endCep: 'BBBBBB',
            telefone: 'BBBBBB',
            fax: 'BBBBBB',
            celular: 'BBBBBB',
            email: 'BBBBBB',
            site: 'BBBBBB',
            observacoes: 'BBBBBB',
            dataAtualizacao: currentDate.format(DATE_TIME_FORMAT),
            ativo: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAtualizacao: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Contato', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addContatoToCollectionIfMissing', () => {
        it('should add a Contato to an empty array', () => {
          const contato: IContato = { id: 123 };
          expectedResult = service.addContatoToCollectionIfMissing([], contato);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(contato);
        });

        it('should not add a Contato to an array that contains it', () => {
          const contato: IContato = { id: 123 };
          const contatoCollection: IContato[] = [
            {
              ...contato,
            },
            { id: 456 },
          ];
          expectedResult = service.addContatoToCollectionIfMissing(contatoCollection, contato);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Contato to an array that doesn't contain it", () => {
          const contato: IContato = { id: 123 };
          const contatoCollection: IContato[] = [{ id: 456 }];
          expectedResult = service.addContatoToCollectionIfMissing(contatoCollection, contato);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(contato);
        });

        it('should add only unique Contato to an array', () => {
          const contatoArray: IContato[] = [{ id: 123 }, { id: 456 }, { id: 19955 }];
          const contatoCollection: IContato[] = [{ id: 123 }];
          expectedResult = service.addContatoToCollectionIfMissing(contatoCollection, ...contatoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const contato: IContato = { id: 123 };
          const contato2: IContato = { id: 456 };
          expectedResult = service.addContatoToCollectionIfMissing([], contato, contato2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(contato);
          expect(expectedResult).toContain(contato2);
        });

        it('should accept null and undefined values', () => {
          const contato: IContato = { id: 123 };
          expectedResult = service.addContatoToCollectionIfMissing([], null, contato, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(contato);
        });

        it('should return initial array if no Contato is added', () => {
          const contatoCollection: IContato[] = [{ id: 123 }];
          expectedResult = service.addContatoToCollectionIfMissing(contatoCollection, undefined, null);
          expect(expectedResult).toEqual(contatoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
