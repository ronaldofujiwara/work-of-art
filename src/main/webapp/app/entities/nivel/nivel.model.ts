import { IObra } from 'app/entities/obra/obra.model';

export interface INivel {
  id?: number;
  nivel?: string;
  inativo?: boolean | null;
  obras?: IObra[] | null;
}

export class Nivel implements INivel {
  constructor(public id?: number, public nivel?: string, public inativo?: boolean | null, public obras?: IObra[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getNivelIdentifier(nivel: INivel): number | undefined {
  return nivel.id;
}
