import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EspacoComponent } from '../list/espaco.component';
import { EspacoDetailComponent } from '../detail/espaco-detail.component';
import { EspacoUpdateComponent } from '../update/espaco-update.component';
import { EspacoRoutingResolveService } from './espaco-routing-resolve.service';

const espacoRoute: Routes = [
  {
    path: '',
    component: EspacoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EspacoDetailComponent,
    resolve: {
      espaco: EspacoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EspacoUpdateComponent,
    resolve: {
      espaco: EspacoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EspacoUpdateComponent,
    resolve: {
      espaco: EspacoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(espacoRoute)],
  exports: [RouterModule],
})
export class EspacoRoutingModule {}
