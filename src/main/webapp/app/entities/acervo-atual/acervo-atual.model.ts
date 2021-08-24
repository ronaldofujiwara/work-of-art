import { IObra } from 'app/entities/obra/obra.model';

export interface IAcervoAtual {
  id?: number;
  acervoAtual?: string;
  inativo?: boolean | null;
  obras?: IObra[] | null;
}

export class AcervoAtual implements IAcervoAtual {
  constructor(public id?: number, public acervoAtual?: string, public inativo?: boolean | null, public obras?: IObra[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getAcervoAtualIdentifier(acervoAtual: IAcervoAtual): number | undefined {
  return acervoAtual.id;
}
