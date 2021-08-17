import { IContato } from 'app/entities/contato/contato.model';

export interface ICidade {
  id?: number;
  cidade?: string;
  estado?: string | null;
  pais?: string | null;
  contatoes?: IContato[] | null;
}

export class Cidade implements ICidade {
  constructor(
    public id?: number,
    public cidade?: string,
    public estado?: string | null,
    public pais?: string | null,
    public contatoes?: IContato[] | null
  ) {}
}

export function getCidadeIdentifier(cidade: ICidade): number | undefined {
  return cidade.id;
}
