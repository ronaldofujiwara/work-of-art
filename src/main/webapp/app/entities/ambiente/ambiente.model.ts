export interface IAmbiente {
  id?: number;
  ambiente?: string;
  inativo?: boolean | null;
}

export class Ambiente implements IAmbiente {
  constructor(public id?: number, public ambiente?: string, public inativo?: boolean | null) {
    this.inativo = this.inativo ?? false;
  }
}

export function getAmbienteIdentifier(ambiente: IAmbiente): number | undefined {
  return ambiente.id;
}
