import { IObra } from 'app/entities/obra/obra.model';

export interface ICategoria {
  id?: number;
  categoria?: string;
  inativo?: boolean | null;
  obras?: IObra[] | null;
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public categoria?: string, public inativo?: boolean | null, public obras?: IObra[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getCategoriaIdentifier(categoria: ICategoria): number | undefined {
  return categoria.id;
}
