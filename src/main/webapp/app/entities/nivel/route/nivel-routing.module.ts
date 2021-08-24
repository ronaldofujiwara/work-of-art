import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NivelComponent } from '../list/nivel.component';
import { NivelDetailComponent } from '../detail/nivel-detail.component';
import { NivelUpdateComponent } from '../update/nivel-update.component';
import { NivelRoutingResolveService } from './nivel-routing-resolve.service';

const nivelRoute: Routes = [
  {
    path: '',
    component: NivelComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NivelDetailComponent,
    resolve: {
      nivel: NivelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NivelUpdateComponent,
    resolve: {
      nivel: NivelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NivelUpdateComponent,
    resolve: {
      nivel: NivelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nivelRoute)],
  exports: [RouterModule],
})
export class NivelRoutingModule {}
