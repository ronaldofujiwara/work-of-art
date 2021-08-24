import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SeguroComponent } from '../list/seguro.component';
import { SeguroDetailComponent } from '../detail/seguro-detail.component';
import { SeguroUpdateComponent } from '../update/seguro-update.component';
import { SeguroRoutingResolveService } from './seguro-routing-resolve.service';

const seguroRoute: Routes = [
  {
    path: '',
    component: SeguroComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SeguroDetailComponent,
    resolve: {
      seguro: SeguroRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SeguroUpdateComponent,
    resolve: {
      seguro: SeguroRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SeguroUpdateComponent,
    resolve: {
      seguro: SeguroRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(seguroRoute)],
  exports: [RouterModule],
})
export class SeguroRoutingModule {}
