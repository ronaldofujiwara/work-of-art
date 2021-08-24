import { IObra } from 'app/entities/obra/obra.model';
import { IEspaco } from 'app/entities/espaco/espaco.model';

export interface IAndarMapa {
  id?: number;
  imagemMapa?: string;
  obras?: IObra[] | null;
  espaco?: IEspaco | null;
}

export class AndarMapa implements IAndarMapa {
  constructor(public id?: number, public imagemMapa?: string, public obras?: IObra[] | null, public espaco?: IEspaco | null) {}
}

export function getAndarMapaIdentifier(andarMapa: IAndarMapa): number | undefined {
  return andarMapa.id;
}
