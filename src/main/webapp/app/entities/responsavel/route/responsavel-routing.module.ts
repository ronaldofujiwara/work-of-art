import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResponsavelComponent } from '../list/responsavel.component';
import { ResponsavelDetailComponent } from '../detail/responsavel-detail.component';
import { ResponsavelUpdateComponent } from '../update/responsavel-update.component';
import { ResponsavelRoutingResolveService } from './responsavel-routing-resolve.service';

const responsavelRoute: Routes = [
  {
    path: '',
    component: ResponsavelComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsavelDetailComponent,
    resolve: {
      responsavel: ResponsavelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsavelUpdateComponent,
    resolve: {
      responsavel: ResponsavelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsavelUpdateComponent,
    resolve: {
      responsavel: ResponsavelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(responsavelRoute)],
  exports: [RouterModule],
})
export class ResponsavelRoutingModule {}
