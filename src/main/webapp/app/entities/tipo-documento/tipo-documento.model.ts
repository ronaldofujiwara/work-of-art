import { IDadoDocumental } from 'app/entities/dado-documental/dado-documental.model';

export interface ITipoDocumento {
  id?: number;
  tipoDocumento?: string;
  inativo?: boolean | null;
  dadoDocumentals?: IDadoDocumental[] | null;
}

export class TipoDocumento implements ITipoDocumento {
  constructor(
    public id?: number,
    public tipoDocumento?: string,
    public inativo?: boolean | null,
    public dadoDocumentals?: IDadoDocumental[] | null
  ) {
    this.inativo = this.inativo ?? false;
  }
}

export function getTipoDocumentoIdentifier(tipoDocumento: ITipoDocumento): number | undefined {
  return tipoDocumento.id;
}
