import { IContato } from 'app/entities/contato/contato.model';

export interface IAreaDepto {
  id?: number;
  area?: string;
  inativo?: boolean | null;
  contatoes?: IContato[] | null;
}

export class AreaDepto implements IAreaDepto {
  constructor(public id?: number, public area?: string, public inativo?: boolean | null, public contatoes?: IContato[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getAreaDeptoIdentifier(areaDepto: IAreaDepto): number | undefined {
  return areaDepto.id;
}
