import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IArtista } from 'app/entities/artista/artista.model';
import { IContato } from 'app/entities/contato/contato.model';

export interface ICidade {
  id?: number;
  cidade?: string;
  estado?: string | null;
  pais?: string | null;
  cidadeUFPais?: string | null;
  estadoPais?: string | null;
  inativo?: boolean | null;
  empresas?: IEmpresa[] | null;
  artistaNascs?: IArtista[] | null;
  artistaFalescs?: IArtista[] | null;
  contatoes?: IContato[] | null;
}

export class Cidade implements ICidade {
  constructor(
    public id?: number,
    public cidade?: string,
    public estado?: string | null,
    public pais?: string | null,
    public cidadeUFPais?: string | null,
    public estadoPais?: string | null,
    public inativo?: boolean | null,
    public empresas?: IEmpresa[] | null,
    public artistaNascs?: IArtista[] | null,
    public artistaFalescs?: IArtista[] | null,
    public contatoes?: IContato[] | null
  ) {
    this.inativo = this.inativo ?? false;
  }
}

export function getCidadeIdentifier(cidade: ICidade): number | undefined {
  return cidade.id;
}
