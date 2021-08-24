import { IObra } from 'app/entities/obra/obra.model';

export interface IData {
  id?: number;
  data?: string;
  inativo?: boolean | null;
  obras?: IObra[] | null;
}

export class Data implements IData {
  constructor(public id?: number, public data?: string, public inativo?: boolean | null, public obras?: IObra[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getDataIdentifier(data: IData): number | undefined {
  return data.id;
}
