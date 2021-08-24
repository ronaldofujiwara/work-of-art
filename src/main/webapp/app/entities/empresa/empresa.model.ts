import { IObra } from 'app/entities/obra/obra.model';
import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IEmpresa {
  id?: number;
  nomeEmpresa?: string;
  codigoBrad?: number | null;
  empresa?: string | null;
  nome?: string | null;
  funcao?: string | null;
  cNPJ?: string | null;
  inscricaoEstadual?: string | null;
  obs?: string | null;
  rua?: string | null;
  numero?: string | null;
  bairro?: string | null;
  complemento?: string | null;
  cEP?: string | null;
  telefone?: string | null;
  fax?: string | null;
  celular?: string | null;
  email?: string | null;
  credito?: string | null;
  inativo?: boolean | null;
  genEmail?: number | null;
  obras?: IObra[] | null;
  cidade?: ICidade | null;
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public nomeEmpresa?: string,
    public codigoBrad?: number | null,
    public empresa?: string | null,
    public nome?: string | null,
    public funcao?: string | null,
    public cNPJ?: string | null,
    public inscricaoEstadual?: string | null,
    public obs?: string | null,
    public rua?: string | null,
    public numero?: string | null,
    public bairro?: string | null,
    public complemento?: string | null,
    public cEP?: string | null,
    public telefone?: string | null,
    public fax?: string | null,
    public celular?: string | null,
    public email?: string | null,
    public credito?: string | null,
    public inativo?: boolean | null,
    public genEmail?: number | null,
    public obras?: IObra[] | null,
    public cidade?: ICidade | null
  ) {
    this.inativo = this.inativo ?? false;
  }
}

export function getEmpresaIdentifier(empresa: IEmpresa): number | undefined {
  return empresa.id;
}
