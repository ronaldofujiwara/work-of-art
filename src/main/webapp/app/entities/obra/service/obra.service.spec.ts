import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IObra, Obra } from '../obra.model';

import { ObraService } from './obra.service';

describe('Service Tests', () => {
  describe('Obra Service', () => {
    let service: ObraService;
    let httpMock: HttpTestingController;
    let elemDefault: IObra;
    let expectedResult: IObra | IObra[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ObraService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        tombo: 'AAAAAAA',
        multiplo: 'AAAAAAA',
        numeroRegistro: 'AAAAAAA',
        outrosTitulos: 'AAAAAAA',
        tituloOriginal: 'AAAAAAA',
        origem: 'AAAAAAA',
        descricao: 'AAAAAAA',
        assinatura: false,
        localAssinatura: 'AAAAAAA',
        marcaInscrObra: 'AAAAAAA',
        marcaInscrSuporte: 'AAAAAAA',
        medidasAprox: false,
        alturaObra: 0,
        largObra: 0,
        profObra: 0,
        diametrObra: 'AAAAAAA',
        alturaMold: 0,
        largMold: 0,
        profMold: 0,
        diametroMold: 'AAAAAAA',
        dimensAdic: 'AAAAAAA',
        pesObra: 'AAAAAAA',
        atribuido: false,
        nFoto: 'AAAAAAA',
        conjunto: false,
        conjTombo: 'AAAAAAA',
        valorSeguro: 0,
        valorSeguroReal: 0,
        dataconversao: currentDate,
        dataAlterApolice: currentDate,
        localAtual: 'AAAAAAA',
        localAtualNovo: 'AAAAAAA',
        codParametro: 'AAAAAAA',
        obs: 'AAAAAAA',
        tiragem: 'AAAAAAA',
        serie: 'AAAAAAA',
        selo: 0,
        tomboAnterio: 'AAAAAAA',
        verbete: 'AAAAAAA',
        livro: false,
        imagemAlta: false,
        apabex: false,
        bunkyo: false,
        faseDepuracao: 'AAAAAAA',
        paraAvaliacao: false,
        paraRestauracao: false,
        paraMoldura: false,
        paraLegenda: false,
        tempField: false,
        selecaoListaAvulsa: false,
        dominioPubl: false,
        dtVencFoto: currentDate,
        obsUsoFoto: 'AAAAAAA',
        localFotoAlta: 'AAAAAAA',
        dataInclusao: currentDate,
        dataExclusao: currentDate,
        bookX: 0,
        bookY: 0,
        genDescricao: 0,
        genField1: 0,
        paraFotografia: false,
        genMarcaInscrObra: 0,
        palavrasChave: 'AAAAAAA',
        genMarcaInscrSuporte: 0,
        genObs: 0,
        genVerbete: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataconversao: currentDate.format(DATE_FORMAT),
            dataAlterApolice: currentDate.format(DATE_FORMAT),
            dtVencFoto: currentDate.format(DATE_FORMAT),
            dataInclusao: currentDate.format(DATE_FORMAT),
            dataExclusao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Obra', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataconversao: currentDate.format(DATE_FORMAT),
            dataAlterApolice: currentDate.format(DATE_FORMAT),
            dtVencFoto: currentDate.format(DATE_FORMAT),
            dataInclusao: currentDate.format(DATE_FORMAT),
            dataExclusao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataconversao: currentDate,
            dataAlterApolice: currentDate,
            dtVencFoto: currentDate,
            dataInclusao: currentDate,
            dataExclusao: currentDate,
          },
          returnedFromService
        );

        service.create(new Obra()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Obra', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tombo: 'BBBBBB',
            multiplo: 'BBBBBB',
            numeroRegistro: 'BBBBBB',
            outrosTitulos: 'BBBBBB',
            tituloOriginal: 'BBBBBB',
            origem: 'BBBBBB',
            descricao: 'BBBBBB',
            assinatura: true,
            localAssinatura: 'BBBBBB',
            marcaInscrObra: 'BBBBBB',
            marcaInscrSuporte: 'BBBBBB',
            medidasAprox: true,
            alturaObra: 1,
            largObra: 1,
            profObra: 1,
            diametrObra: 'BBBBBB',
            alturaMold: 1,
            largMold: 1,
            profMold: 1,
            diametroMold: 'BBBBBB',
            dimensAdic: 'BBBBBB',
            pesObra: 'BBBBBB',
            atribuido: true,
            nFoto: 'BBBBBB',
            conjunto: true,
            conjTombo: 'BBBBBB',
            valorSeguro: 1,
            valorSeguroReal: 1,
            dataconversao: currentDate.format(DATE_FORMAT),
            dataAlterApolice: currentDate.format(DATE_FORMAT),
            localAtual: 'BBBBBB',
            localAtualNovo: 'BBBBBB',
            codParametro: 'BBBBBB',
            obs: 'BBBBBB',
            tiragem: 'BBBBBB',
            serie: 'BBBBBB',
            selo: 1,
            tomboAnterio: 'BBBBBB',
            verbete: 'BBBBBB',
            livro: true,
            imagemAlta: true,
            apabex: true,
            bunkyo: true,
            faseDepuracao: 'BBBBBB',
            paraAvaliacao: true,
            paraRestauracao: true,
            paraMoldura: true,
            paraLegenda: true,
            tempField: true,
            selecaoListaAvulsa: true,
            dominioPubl: true,
            dtVencFoto: currentDate.format(DATE_FORMAT),
            obsUsoFoto: 'BBBBBB',
            localFotoAlta: 'BBBBBB',
            dataInclusao: currentDate.format(DATE_FORMAT),
            dataExclusao: currentDate.format(DATE_FORMAT),
            bookX: 1,
            bookY: 1,
            genDescricao: 1,
            genField1: 1,
            paraFotografia: true,
            genMarcaInscrObra: 1,
            palavrasChave: 'BBBBBB',
            genMarcaInscrSuporte: 1,
            genObs: 1,
            genVerbete: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataconversao: currentDate,
            dataAlterApolice: currentDate,
            dtVencFoto: currentDate,
            dataInclusao: currentDate,
            dataExclusao: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Obra', () => {
        const patchObject = Object.assign(
          {
            tombo: 'BBBBBB',
            descricao: 'BBBBBB',
            assinatura: true,
            marcaInscrSuporte: 'BBBBBB',
            medidasAprox: true,
            largObra: 1,
            profObra: 1,
            profMold: 1,
            pesObra: 'BBBBBB',
            atribuido: true,
            conjunto: true,
            conjTombo: 'BBBBBB',
            valorSeguro: 1,
            dataconversao: currentDate.format(DATE_FORMAT),
            dataAlterApolice: currentDate.format(DATE_FORMAT),
            serie: 'BBBBBB',
            apabex: true,
            bunkyo: true,
            paraAvaliacao: true,
            paraRestauracao: true,
            tempField: true,
            selecaoListaAvulsa: true,
            dtVencFoto: currentDate.format(DATE_FORMAT),
            obsUsoFoto: 'BBBBBB',
            dataExclusao: currentDate.format(DATE_FORMAT),
            bookX: 1,
            palavrasChave: 'BBBBBB',
            genObs: 1,
            genVerbete: 1,
          },
          new Obra()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataconversao: currentDate,
            dataAlterApolice: currentDate,
            dtVencFoto: currentDate,
            dataInclusao: currentDate,
            dataExclusao: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Obra', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tombo: 'BBBBBB',
            multiplo: 'BBBBBB',
            numeroRegistro: 'BBBBBB',
            outrosTitulos: 'BBBBBB',
            tituloOriginal: 'BBBBBB',
            origem: 'BBBBBB',
            descricao: 'BBBBBB',
            assinatura: true,
            localAssinatura: 'BBBBBB',
            marcaInscrObra: 'BBBBBB',
            marcaInscrSuporte: 'BBBBBB',
            medidasAprox: true,
            alturaObra: 1,
            largObra: 1,
            profObra: 1,
            diametrObra: 'BBBBBB',
            alturaMold: 1,
            largMold: 1,
            profMold: 1,
            diametroMold: 'BBBBBB',
            dimensAdic: 'BBBBBB',
            pesObra: 'BBBBBB',
            atribuido: true,
            nFoto: 'BBBBBB',
            conjunto: true,
            conjTombo: 'BBBBBB',
            valorSeguro: 1,
            valorSeguroReal: 1,
            dataconversao: currentDate.format(DATE_FORMAT),
            dataAlterApolice: currentDate.format(DATE_FORMAT),
            localAtual: 'BBBBBB',
            localAtualNovo: 'BBBBBB',
            codParametro: 'BBBBBB',
            obs: 'BBBBBB',
            tiragem: 'BBBBBB',
            serie: 'BBBBBB',
            selo: 1,
            tomboAnterio: 'BBBBBB',
            verbete: 'BBBBBB',
            livro: true,
            imagemAlta: true,
            apabex: true,
            bunkyo: true,
            faseDepuracao: 'BBBBBB',
            paraAvaliacao: true,
            paraRestauracao: true,
            paraMoldura: true,
            paraLegenda: true,
            tempField: true,
            selecaoListaAvulsa: true,
            dominioPubl: true,
            dtVencFoto: currentDate.format(DATE_FORMAT),
            obsUsoFoto: 'BBBBBB',
            localFotoAlta: 'BBBBBB',
            dataInclusao: currentDate.format(DATE_FORMAT),
            dataExclusao: currentDate.format(DATE_FORMAT),
            bookX: 1,
            bookY: 1,
            genDescricao: 1,
            genField1: 1,
            paraFotografia: true,
            genMarcaInscrObra: 1,
            palavrasChave: 'BBBBBB',
            genMarcaInscrSuporte: 1,
            genObs: 1,
            genVerbete: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataconversao: currentDate,
            dataAlterApolice: currentDate,
            dtVencFoto: currentDate,
            dataInclusao: currentDate,
            dataExclusao: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Obra', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addObraToCollectionIfMissing', () => {
        it('should add a Obra to an empty array', () => {
          const obra: IObra = { id: 123 };
          expectedResult = service.addObraToCollectionIfMissing([], obra);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(obra);
        });

        it('should not add a Obra to an array that contains it', () => {
          const obra: IObra = { id: 123 };
          const obraCollection: IObra[] = [
            {
              ...obra,
            },
            { id: 456 },
          ];
          expectedResult = service.addObraToCollectionIfMissing(obraCollection, obra);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Obra to an array that doesn't contain it", () => {
          const obra: IObra = { id: 123 };
          const obraCollection: IObra[] = [{ id: 456 }];
          expectedResult = service.addObraToCollectionIfMissing(obraCollection, obra);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(obra);
        });

        it('should add only unique Obra to an array', () => {
          const obraArray: IObra[] = [{ id: 123 }, { id: 456 }, { id: 36280 }];
          const obraCollection: IObra[] = [{ id: 123 }];
          expectedResult = service.addObraToCollectionIfMissing(obraCollection, ...obraArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const obra: IObra = { id: 123 };
          const obra2: IObra = { id: 456 };
          expectedResult = service.addObraToCollectionIfMissing([], obra, obra2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(obra);
          expect(expectedResult).toContain(obra2);
        });

        it('should accept null and undefined values', () => {
          const obra: IObra = { id: 123 };
          expectedResult = service.addObraToCollectionIfMissing([], null, obra, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(obra);
        });

        it('should return initial array if no Obra is added', () => {
          const obraCollection: IObra[] = [{ id: 123 }];
          expectedResult = service.addObraToCollectionIfMissing(obraCollection, undefined, null);
          expect(expectedResult).toEqual(obraCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
