import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DadoDocumentalComponent } from '../list/dado-documental.component';
import { DadoDocumentalDetailComponent } from '../detail/dado-documental-detail.component';
import { DadoDocumentalUpdateComponent } from '../update/dado-documental-update.component';
import { DadoDocumentalRoutingResolveService } from './dado-documental-routing-resolve.service';

const dadoDocumentalRoute: Routes = [
  {
    path: '',
    component: DadoDocumentalComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DadoDocumentalDetailComponent,
    resolve: {
      dadoDocumental: DadoDocumentalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DadoDocumentalUpdateComponent,
    resolve: {
      dadoDocumental: DadoDocumentalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DadoDocumentalUpdateComponent,
    resolve: {
      dadoDocumental: DadoDocumentalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dadoDocumentalRoute)],
  exports: [RouterModule],
})
export class DadoDocumentalRoutingModule {}
