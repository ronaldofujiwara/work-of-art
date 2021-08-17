export interface IAmbiente {
  id?: number;
  ambiente?: string;
  ativo?: boolean | null;
}

export class Ambiente implements IAmbiente {
  constructor(public id?: number, public ambiente?: string, public ativo?: boolean | null) {
    this.ativo = this.ativo ?? false;
  }
}

export function getAmbienteIdentifier(ambiente: IAmbiente): number | undefined {
  return ambiente.id;
}
