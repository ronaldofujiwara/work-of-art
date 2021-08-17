import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AreaDeptoComponent } from '../list/area-depto.component';
import { AreaDeptoDetailComponent } from '../detail/area-depto-detail.component';
import { AreaDeptoUpdateComponent } from '../update/area-depto-update.component';
import { AreaDeptoRoutingResolveService } from './area-depto-routing-resolve.service';

const areaDeptoRoute: Routes = [
  {
    path: '',
    component: AreaDeptoComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AreaDeptoDetailComponent,
    resolve: {
      areaDepto: AreaDeptoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaDeptoUpdateComponent,
    resolve: {
      areaDepto: AreaDeptoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AreaDeptoUpdateComponent,
    resolve: {
      areaDepto: AreaDeptoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(areaDeptoRoute)],
  exports: [RouterModule],
})
export class AreaDeptoRoutingModule {}
