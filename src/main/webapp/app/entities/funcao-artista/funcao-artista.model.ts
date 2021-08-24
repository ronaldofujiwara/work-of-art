import { IArtista } from 'app/entities/artista/artista.model';

export interface IFuncaoArtista {
  id?: number;
  funcaoArtista?: string;
  inativo?: boolean | null;
  artistas?: IArtista[] | null;
}

export class FuncaoArtista implements IFuncaoArtista {
  constructor(public id?: number, public funcaoArtista?: string, public inativo?: boolean | null, public artistas?: IArtista[] | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getFuncaoArtistaIdentifier(funcaoArtista: IFuncaoArtista): number | undefined {
  return funcaoArtista.id;
}
