import { IContato } from 'app/entities/contato/contato.model';

export interface IAreaDepto {
  id?: number;
  area?: string;
  ativo?: boolean | null;
  contatoes?: IContato[] | null;
}

export class AreaDepto implements IAreaDepto {
  constructor(public id?: number, public area?: string, public ativo?: boolean | null, public contatoes?: IContato[] | null) {
    this.ativo = this.ativo ?? false;
  }
}

export function getAreaDeptoIdentifier(areaDepto: IAreaDepto): number | undefined {
  return areaDepto.id;
}
