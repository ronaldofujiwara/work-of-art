import { IObra } from 'app/entities/obra/obra.model';

export interface ITecnica {
  id?: number;
  tecnica?: string;
  inativo?: boolean | null;
  obras?: IObra[] | null;
}

export class Tecnica implements ITecnica {
  constructor(public id?: number, public tecnica?: string, public inativo?: boolean | null, public obras?: IObra[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getTecnicaIdentifier(tecnica: ITecnica): number | undefined {
  return tecnica.id;
}
