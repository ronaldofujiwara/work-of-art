import * as dayjs from 'dayjs';
import { IObra } from 'app/entities/obra/obra.model';
import { ISeguro } from 'app/entities/seguro/seguro.model';
import { IAreaDepto } from 'app/entities/area-depto/area-depto.model';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { IArtista } from 'app/entities/artista/artista.model';

export interface IContato {
  id?: number;
  nomeComp?: string;
  empresa?: string | null;
  funcao?: string | null;
  rg?: string | null;
  cpf?: string | null;
  infoContato?: string | null;
  endRua?: string | null;
  endNumero?: string | null;
  endBairro?: string | null;
  endComplemento?: string | null;
  endCep?: string | null;
  telefone?: string | null;
  fax?: string | null;
  celular?: string | null;
  email?: string | null;
  site?: string | null;
  observacoes?: string | null;
  dataAtualizacao?: dayjs.Dayjs | null;
  inativo?: boolean | null;
  obras?: IObra[] | null;
  seguroSegs?: ISeguro[] | null;
  seguroCors?: ISeguro[] | null;
  area?: IAreaDepto | null;
  cidade?: ICidade | null;
  artistas?: IArtista[] | null;
}

export class Contato implements IContato {
  constructor(
    public id?: number,
    public nomeComp?: string,
    public empresa?: string | null,
    public funcao?: string | null,
    public rg?: string | null,
    public cpf?: string | null,
    public infoContato?: string | null,
    public endRua?: string | null,
    public endNumero?: string | null,
    public endBairro?: string | null,
    public endComplemento?: string | null,
    public endCep?: string | null,
    public telefone?: string | null,
    public fax?: string | null,
    public celular?: string | null,
    public email?: string | null,
    public site?: string | null,
    public observacoes?: string | null,
    public dataAtualizacao?: dayjs.Dayjs | null,
    public inativo?: boolean | null,
    public obras?: IObra[] | null,
    public seguroSegs?: ISeguro[] | null,
    public seguroCors?: ISeguro[] | null,
    public area?: IAreaDepto | null,
    public cidade?: ICidade | null,
    public artistas?: IArtista[] | null
  ) {
    this.inativo = this.inativo ?? false;
  }
}

export function getContatoIdentifier(contato: IContato): number | undefined {
  return contato.id;
}
