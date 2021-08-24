import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MoedaComponent } from '../list/moeda.component';
import { MoedaDetailComponent } from '../detail/moeda-detail.component';
import { MoedaUpdateComponent } from '../update/moeda-update.component';
import { MoedaRoutingResolveService } from './moeda-routing-resolve.service';

const moedaRoute: Routes = [
  {
    path: '',
    component: MoedaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MoedaDetailComponent,
    resolve: {
      moeda: MoedaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MoedaUpdateComponent,
    resolve: {
      moeda: MoedaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MoedaUpdateComponent,
    resolve: {
      moeda: MoedaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(moedaRoute)],
  exports: [RouterModule],
})
export class MoedaRoutingModule {}
