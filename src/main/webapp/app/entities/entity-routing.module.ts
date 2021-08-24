import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ambiente',
        data: { pageTitle: 'artesApp.ambiente.home.title' },
        loadChildren: () => import('./ambiente/ambiente.module').then(m => m.AmbienteModule),
      },
      {
        path: 'area-depto',
        data: { pageTitle: 'artesApp.areaDepto.home.title' },
        loadChildren: () => import('./area-depto/area-depto.module').then(m => m.AreaDeptoModule),
      },
      {
        path: 'cidade',
        data: { pageTitle: 'artesApp.cidade.home.title' },
        loadChildren: () => import('./cidade/cidade.module').then(m => m.CidadeModule),
      },
      {
        path: 'contato',
        data: { pageTitle: 'artesApp.contato.home.title' },
        loadChildren: () => import('./contato/contato.module').then(m => m.ContatoModule),
      },
      {
        path: 'acervo-atual',
        data: { pageTitle: 'artesApp.acervoAtual.home.title' },
        loadChildren: () => import('./acervo-atual/acervo-atual.module').then(m => m.AcervoAtualModule),
      },
      {
        path: 'andar-mapa',
        data: { pageTitle: 'artesApp.andarMapa.home.title' },
        loadChildren: () => import('./andar-mapa/andar-mapa.module').then(m => m.AndarMapaModule),
      },
      {
        path: 'artista',
        data: { pageTitle: 'artesApp.artista.home.title' },
        loadChildren: () => import('./artista/artista.module').then(m => m.ArtistaModule),
      },
      {
        path: 'categoria',
        data: { pageTitle: 'artesApp.categoria.home.title' },
        loadChildren: () => import('./categoria/categoria.module').then(m => m.CategoriaModule),
      },
      {
        path: 'dado-documental',
        data: { pageTitle: 'artesApp.dadoDocumental.home.title' },
        loadChildren: () => import('./dado-documental/dado-documental.module').then(m => m.DadoDocumentalModule),
      },
      {
        path: 'data',
        data: { pageTitle: 'artesApp.data.home.title' },
        loadChildren: () => import('./data/data.module').then(m => m.DataModule),
      },
      {
        path: 'empresa',
        data: { pageTitle: 'artesApp.empresa.home.title' },
        loadChildren: () => import('./empresa/empresa.module').then(m => m.EmpresaModule),
      },
      {
        path: 'espaco',
        data: { pageTitle: 'artesApp.espaco.home.title' },
        loadChildren: () => import('./espaco/espaco.module').then(m => m.EspacoModule),
      },
      {
        path: 'funcao-artista',
        data: { pageTitle: 'artesApp.funcaoArtista.home.title' },
        loadChildren: () => import('./funcao-artista/funcao-artista.module').then(m => m.FuncaoArtistaModule),
      },
      {
        path: 'moeda',
        data: { pageTitle: 'artesApp.moeda.home.title' },
        loadChildren: () => import('./moeda/moeda.module').then(m => m.MoedaModule),
      },
      {
        path: 'nivel',
        data: { pageTitle: 'artesApp.nivel.home.title' },
        loadChildren: () => import('./nivel/nivel.module').then(m => m.NivelModule),
      },
      {
        path: 'obra',
        data: { pageTitle: 'artesApp.obra.home.title' },
        loadChildren: () => import('./obra/obra.module').then(m => m.ObraModule),
      },
      {
        path: 'responsavel',
        data: { pageTitle: 'artesApp.responsavel.home.title' },
        loadChildren: () => import('./responsavel/responsavel.module').then(m => m.ResponsavelModule),
      },
      {
        path: 'seguro',
        data: { pageTitle: 'artesApp.seguro.home.title' },
        loadChildren: () => import('./seguro/seguro.module').then(m => m.SeguroModule),
      },
      {
        path: 'tecnica',
        data: { pageTitle: 'artesApp.tecnica.home.title' },
        loadChildren: () => import('./tecnica/tecnica.module').then(m => m.TecnicaModule),
      },
      {
        path: 'tipo-documento',
        data: { pageTitle: 'artesApp.tipoDocumento.home.title' },
        loadChildren: () => import('./tipo-documento/tipo-documento.module').then(m => m.TipoDocumentoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
