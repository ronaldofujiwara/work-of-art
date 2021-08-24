import * as dayjs from 'dayjs';
import { IObra } from 'app/entities/obra/obra.model';
import { IContato } from 'app/entities/contato/contato.model';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { IResponsavel } from 'app/entities/responsavel/responsavel.model';
import { IFuncaoArtista } from 'app/entities/funcao-artista/funcao-artista.model';

export interface IArtista {
  id?: number;
  nome?: string;
  dataNasc?: string | null;
  dataFalec?: string | null;
  biografia?: string | null;
  verbete?: string | null;
  dataAtualBio?: dayjs.Dayjs | null;
  dataAtualVerb?: dayjs.Dayjs | null;
  possuiBio?: boolean | null;
  possuiVerb?: boolean | null;
  fonteBio?: string | null;
  obrasNoAcervo?: string | null;
  inativo?: boolean | null;
  agradecimentos?: string | null;
  copyright?: string | null;
  obsUso?: string | null;
  obras?: IObra[] | null;
  contatoes?: IContato[] | null;
  cidadeNasc?: ICidade | null;
  cidadeFalesc?: ICidade | null;
  respVerbete?: IResponsavel | null;
  funcaoArtista?: IFuncaoArtista | null;
}

export class Artista implements IArtista {
  constructor(
    public id?: number,
    public nome?: string,
    public dataNasc?: string | null,
    public dataFalec?: string | null,
    public biografia?: string | null,
    public verbete?: string | null,
    public dataAtualBio?: dayjs.Dayjs | null,
    public dataAtualVerb?: dayjs.Dayjs | null,
    public possuiBio?: boolean | null,
    public possuiVerb?: boolean | null,
    public fonteBio?: string | null,
    public obrasNoAcervo?: string | null,
    public inativo?: boolean | null,
    public agradecimentos?: string | null,
    public copyright?: string | null,
    public obsUso?: string | null,
    public obras?: IObra[] | null,
    public contatoes?: IContato[] | null,
    public cidadeNasc?: ICidade | null,
    public cidadeFalesc?: ICidade | null,
    public respVerbete?: IResponsavel | null,
    public funcaoArtista?: IFuncaoArtista | null
  ) {
    this.possuiBio = this.possuiBio ?? false;
    this.possuiVerb = this.possuiVerb ?? false;
    this.inativo = this.inativo ?? false;
  }
}

export function getArtistaIdentifier(artista: IArtista): number | undefined {
  return artista.id;
}
