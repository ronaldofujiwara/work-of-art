import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AndarMapaComponent } from '../list/andar-mapa.component';
import { AndarMapaDetailComponent } from '../detail/andar-mapa-detail.component';
import { AndarMapaUpdateComponent } from '../update/andar-mapa-update.component';
import { AndarMapaRoutingResolveService } from './andar-mapa-routing-resolve.service';

const andarMapaRoute: Routes = [
  {
    path: '',
    component: AndarMapaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AndarMapaDetailComponent,
    resolve: {
      andarMapa: AndarMapaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AndarMapaUpdateComponent,
    resolve: {
      andarMapa: AndarMapaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AndarMapaUpdateComponent,
    resolve: {
      andarMapa: AndarMapaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(andarMapaRoute)],
  exports: [RouterModule],
})
export class AndarMapaRoutingModule {}
