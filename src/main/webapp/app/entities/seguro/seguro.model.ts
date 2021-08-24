import * as dayjs from 'dayjs';
import { IObra } from 'app/entities/obra/obra.model';
import { IContato } from 'app/entities/contato/contato.model';
import { IMoeda } from 'app/entities/moeda/moeda.model';

export interface ISeguro {
  id?: number;
  apolice?: string | null;
  obsSeguro?: string | null;
  contratoProposta?: string | null;
  ciaSeguradora?: string | null;
  cnpjSeguradora?: string | null;
  susepSeguradora?: string | null;
  corretora?: string | null;
  cnpjCorretora?: string | null;
  susepCorretora?: string | null;
  dataInicio?: dayjs.Dayjs | null;
  dataVenc?: dayjs.Dayjs | null;
  inativo?: boolean | null;
  premio?: string | null;
  txConversao?: number | null;
  genStatusSeguro?: number | null;
  dataAtualSeguro?: dayjs.Dayjs | null;
  obras?: IObra[] | null;
  contatoSeg?: IContato | null;
  contatoCor?: IContato | null;
  moeda?: IMoeda | null;
}

export class Seguro implements ISeguro {
  constructor(
    public id?: number,
    public apolice?: string | null,
    public obsSeguro?: string | null,
    public contratoProposta?: string | null,
    public ciaSeguradora?: string | null,
    public cnpjSeguradora?: string | null,
    public susepSeguradora?: string | null,
    public corretora?: string | null,
    public cnpjCorretora?: string | null,
    public susepCorretora?: string | null,
    public dataInicio?: dayjs.Dayjs | null,
    public dataVenc?: dayjs.Dayjs | null,
    public inativo?: boolean | null,
    public premio?: string | null,
    public txConversao?: number | null,
    public genStatusSeguro?: number | null,
    public dataAtualSeguro?: dayjs.Dayjs | null,
    public obras?: IObra[] | null,
    public contatoSeg?: IContato | null,
    public contatoCor?: IContato | null,
    public moeda?: IMoeda | null
  ) {
    this.inativo = this.inativo ?? false;
  }
}

export function getSeguroIdentifier(seguro: ISeguro): number | undefined {
  return seguro.id;
}
