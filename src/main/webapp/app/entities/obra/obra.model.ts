import * as dayjs from 'dayjs';
import { IDadoDocumental } from 'app/entities/dado-documental/dado-documental.model';
import { IArtista } from 'app/entities/artista/artista.model';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { ITecnica } from 'app/entities/tecnica/tecnica.model';
import { INivel } from 'app/entities/nivel/nivel.model';
import { IData } from 'app/entities/data/data.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IMoeda } from 'app/entities/moeda/moeda.model';
import { ISeguro } from 'app/entities/seguro/seguro.model';
import { IResponsavel } from 'app/entities/responsavel/responsavel.model';
import { IAcervoAtual } from 'app/entities/acervo-atual/acervo-atual.model';
import { IContato } from 'app/entities/contato/contato.model';
import { IAndarMapa } from 'app/entities/andar-mapa/andar-mapa.model';

export interface IObra {
  id?: number;
  tombo?: string;
  multiplo?: string | null;
  numeroRegistro?: string | null;
  outrosTitulos?: string | null;
  tituloOriginal?: string | null;
  origem?: string | null;
  descricao?: string | null;
  assinatura?: boolean | null;
  localAssinatura?: string | null;
  marcaInscrObra?: string | null;
  marcaInscrSuporte?: string | null;
  medidasAprox?: boolean | null;
  alturaObra?: number | null;
  largObra?: number | null;
  profObra?: number | null;
  diametrObra?: string | null;
  alturaMold?: number | null;
  largMold?: number | null;
  profMold?: number | null;
  diametroMold?: string | null;
  dimensAdic?: string | null;
  pesObra?: string | null;
  atribuido?: boolean | null;
  nFoto?: string | null;
  conjunto?: boolean | null;
  conjTombo?: string | null;
  valorSeguro?: number | null;
  valorSeguroReal?: number | null;
  dataconversao?: dayjs.Dayjs | null;
  dataAlterApolice?: dayjs.Dayjs | null;
  localAtual?: string | null;
  localAtualNovo?: string | null;
  codParametro?: string | null;
  obs?: string | null;
  tiragem?: string | null;
  serie?: string | null;
  selo?: number | null;
  tomboAnterio?: string | null;
  verbete?: string | null;
  livro?: boolean | null;
  imagemAlta?: boolean | null;
  apabex?: boolean | null;
  bunkyo?: boolean | null;
  faseDepuracao?: string | null;
  paraAvaliacao?: boolean | null;
  paraRestauracao?: boolean | null;
  paraMoldura?: boolean | null;
  paraLegenda?: boolean | null;
  tempField?: boolean | null;
  selecaoListaAvulsa?: boolean | null;
  dominioPubl?: boolean | null;
  dtVencFoto?: dayjs.Dayjs | null;
  obsUsoFoto?: string | null;
  localFotoAlta?: string | null;
  dataInclusao?: dayjs.Dayjs | null;
  dataExclusao?: dayjs.Dayjs | null;
  bookX?: number | null;
  bookY?: number | null;
  genDescricao?: number | null;
  genField1?: number | null;
  paraFotografia?: boolean | null;
  genMarcaInscrObra?: number | null;
  palavrasChave?: string | null;
  genMarcaInscrSuporte?: number | null;
  genObs?: number | null;
  genVerbete?: number | null;
  dadoDocumentals?: IDadoDocumental[] | null;
  artista?: IArtista | null;
  categoria?: ICategoria | null;
  tecnica?: ITecnica | null;
  nivel?: INivel | null;
  data?: IData | null;
  empresa?: IEmpresa | null;
  moeda?: IMoeda | null;
  seguro?: ISeguro | null;
  responsavel?: IResponsavel | null;
  acervoatual?: IAcervoAtual | null;
  fotografo?: IContato | null;
  andarMapa?: IAndarMapa | null;
}

export class Obra implements IObra {
  constructor(
    public id?: number,
    public tombo?: string,
    public multiplo?: string | null,
    public numeroRegistro?: string | null,
    public outrosTitulos?: string | null,
    public tituloOriginal?: string | null,
    public origem?: string | null,
    public descricao?: string | null,
    public assinatura?: boolean | null,
    public localAssinatura?: string | null,
    public marcaInscrObra?: string | null,
    public marcaInscrSuporte?: string | null,
    public medidasAprox?: boolean | null,
    public alturaObra?: number | null,
    public largObra?: number | null,
    public profObra?: number | null,
    public diametrObra?: string | null,
    public alturaMold?: number | null,
    public largMold?: number | null,
    public profMold?: number | null,
    public diametroMold?: string | null,
    public dimensAdic?: string | null,
    public pesObra?: string | null,
    public atribuido?: boolean | null,
    public nFoto?: string | null,
    public conjunto?: boolean | null,
    public conjTombo?: string | null,
    public valorSeguro?: number | null,
    public valorSeguroReal?: number | null,
    public dataconversao?: dayjs.Dayjs | null,
    public dataAlterApolice?: dayjs.Dayjs | null,
    public localAtual?: string | null,
    public localAtualNovo?: string | null,
    public codParametro?: string | null,
    public obs?: string | null,
    public tiragem?: string | null,
    public serie?: string | null,
    public selo?: number | null,
    public tomboAnterio?: string | null,
    public verbete?: string | null,
    public livro?: boolean | null,
    public imagemAlta?: boolean | null,
    public apabex?: boolean | null,
    public bunkyo?: boolean | null,
    public faseDepuracao?: string | null,
    public paraAvaliacao?: boolean | null,
    public paraRestauracao?: boolean | null,
    public paraMoldura?: boolean | null,
    public paraLegenda?: boolean | null,
    public tempField?: boolean | null,
    public selecaoListaAvulsa?: boolean | null,
    public dominioPubl?: boolean | null,
    public dtVencFoto?: dayjs.Dayjs | null,
    public obsUsoFoto?: string | null,
    public localFotoAlta?: string | null,
    public dataInclusao?: dayjs.Dayjs | null,
    public dataExclusao?: dayjs.Dayjs | null,
    public bookX?: number | null,
    public bookY?: number | null,
    public genDescricao?: number | null,
    public genField1?: number | null,
    public paraFotografia?: boolean | null,
    public genMarcaInscrObra?: number | null,
    public palavrasChave?: string | null,
    public genMarcaInscrSuporte?: number | null,
    public genObs?: number | null,
    public genVerbete?: number | null,
    public dadoDocumentals?: IDadoDocumental[] | null,
    public artista?: IArtista | null,
    public categoria?: ICategoria | null,
    public tecnica?: ITecnica | null,
    public nivel?: INivel | null,
    public data?: IData | null,
    public empresa?: IEmpresa | null,
    public moeda?: IMoeda | null,
    public seguro?: ISeguro | null,
    public responsavel?: IResponsavel | null,
    public acervoatual?: IAcervoAtual | null,
    public fotografo?: IContato | null,
    public andarMapa?: IAndarMapa | null
  ) {
    this.assinatura = this.assinatura ?? false;
    this.medidasAprox = this.medidasAprox ?? false;
    this.atribuido = this.atribuido ?? false;
    this.conjunto = this.conjunto ?? false;
    this.livro = this.livro ?? false;
    this.imagemAlta = this.imagemAlta ?? false;
    this.apabex = this.apabex ?? false;
    this.bunkyo = this.bunkyo ?? false;
    this.paraAvaliacao = this.paraAvaliacao ?? false;
    this.paraRestauracao = this.paraRestauracao ?? false;
    this.paraMoldura = this.paraMoldura ?? false;
    this.paraLegenda = this.paraLegenda ?? false;
    this.tempField = this.tempField ?? false;
    this.selecaoListaAvulsa = this.selecaoListaAvulsa ?? false;
    this.dominioPubl = this.dominioPubl ?? false;
    this.paraFotografia = this.paraFotografia ?? false;
  }
}

export function getObraIdentifier(obra: IObra): number | undefined {
  return obra.id;
}
