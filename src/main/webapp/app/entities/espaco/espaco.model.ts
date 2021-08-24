import { IAndarMapa } from 'app/entities/andar-mapa/andar-mapa.model';

export interface IEspaco {
  id?: number;
  espaco?: string;
  inativo?: boolean | null;
  andarMapas?: IAndarMapa[] | null;
}

export class Espaco implements IEspaco {
  constructor(public id?: number, public espaco?: string, public inativo?: boolean | null, public andarMapas?: IAndarMapa[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getEspacoIdentifier(espaco: IEspaco): number | undefined {
  return espaco.id;
}
