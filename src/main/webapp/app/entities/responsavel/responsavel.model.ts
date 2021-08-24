import { IArtista } from 'app/entities/artista/artista.model';
import { IObra } from 'app/entities/obra/obra.model';

export interface IResponsavel {
  id?: number;
  nome?: string;
  inativo?: boolean | null;
  artVerbetes?: IArtista[] | null;
  obras?: IObra[] | null;
}

export class Responsavel implements IResponsavel {
  constructor(
    public id?: number,
    public nome?: string,
    public inativo?: boolean | null,
    public artVerbetes?: IArtista[] | null,
    public obras?: IObra[] | null
  ) {
    this.inativo = this.inativo ?? false;
  }
}

export function getResponsavelIdentifier(responsavel: IResponsavel): number | undefined {
  return responsavel.id;
}
