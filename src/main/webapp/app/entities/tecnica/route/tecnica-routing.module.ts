import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TecnicaComponent } from '../list/tecnica.component';
import { TecnicaDetailComponent } from '../detail/tecnica-detail.component';
import { TecnicaUpdateComponent } from '../update/tecnica-update.component';
import { TecnicaRoutingResolveService } from './tecnica-routing-resolve.service';

const tecnicaRoute: Routes = [
  {
    path: '',
    component: TecnicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TecnicaDetailComponent,
    resolve: {
      tecnica: TecnicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TecnicaUpdateComponent,
    resolve: {
      tecnica: TecnicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TecnicaUpdateComponent,
    resolve: {
      tecnica: TecnicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tecnicaRoute)],
  exports: [RouterModule],
})
export class TecnicaRoutingModule {}
