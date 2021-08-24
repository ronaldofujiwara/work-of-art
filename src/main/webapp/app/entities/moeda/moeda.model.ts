import { IObra } from 'app/entities/obra/obra.model';
import { ISeguro } from 'app/entities/seguro/seguro.model';

export interface IMoeda {
  id?: number;
  tipoMoeda?: string;
  inativo?: boolean | null;
  obras?: IObra[] | null;
  seguros?: ISeguro[] | null;
}

export class Moeda implements IMoeda {
  constructor(
    public id?: number,
    public tipoMoeda?: string,
    public inativo?: boolean | null,
    public obras?: IObra[] | null,
    public seguros?: ISeguro[] | null
  ) {
    this.inativo = this.inativo ?? false;
  }
}

export function getMoedaIdentifier(moeda: IMoeda): number | undefined {
  return moeda.id;
}
