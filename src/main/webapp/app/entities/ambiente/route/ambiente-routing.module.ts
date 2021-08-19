import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AmbienteComponent } from '../list/ambiente.component';
import { AmbienteDetailComponent } from '../detail/ambiente-detail.component';
import { AmbienteUpdateComponent } from '../update/ambiente-update.component';
import { AmbienteRoutingResolveService } from './ambiente-routing-resolve.service';

const ambienteRoute: Routes = [
  {
    path: '',
    component: AmbienteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AmbienteDetailComponent,
    resolve: {
      ambiente: AmbienteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AmbienteUpdateComponent,
    resolve: {
      ambiente: AmbienteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AmbienteUpdateComponent,
    resolve: {
      ambiente: AmbienteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ambienteRoute)],
  exports: [RouterModule],
})
export class AmbienteRoutingModule {}
