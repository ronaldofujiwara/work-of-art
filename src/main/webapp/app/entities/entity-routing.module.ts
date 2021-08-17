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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
