import { ITipoDocumento } from 'app/entities/tipo-documento/tipo-documento.model';
import { IObra } from 'app/entities/obra/obra.model';

export interface IDadoDocumental {
  id?: number;
  data?: string | null;
  emissor?: string | null;
  receptor?: string | null;
  obs?: string | null;
  transcricao?: string | null;
  finalizado?: boolean | null;
  genTranscricao?: number | null;
  tipoDocumento?: ITipoDocumento | null;
  obras?: IObra[] | null;
}

export class DadoDocumental implements IDadoDocumental {
  constructor(
    public id?: number,
    public data?: string | null,
    public emissor?: string | null,
    public receptor?: string | null,
    public obs?: string | null,
    public transcricao?: string | null,
    public finalizado?: boolean | null,
    public genTranscricao?: number | null,
    public tipoDocumento?: ITipoDocumento | null,
    public obras?: IObra[] | null
  ) {
    this.finalizado = this.finalizado ?? false;
  }
}

export function getDadoDocumentalIdentifier(dadoDocumental: IDadoDocumental): number | undefined {
  return dadoDocumental.id;
}
