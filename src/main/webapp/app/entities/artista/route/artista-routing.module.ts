import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ArtistaComponent } from '../list/artista.component';
import { ArtistaDetailComponent } from '../detail/artista-detail.component';
import { ArtistaUpdateComponent } from '../update/artista-update.component';
import { ArtistaRoutingResolveService } from './artista-routing-resolve.service';

const artistaRoute: Routes = [
  {
    path: '',
    component: ArtistaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArtistaDetailComponent,
    resolve: {
      artista: ArtistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArtistaUpdateComponent,
    resolve: {
      artista: ArtistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArtistaUpdateComponent,
    resolve: {
      artista: ArtistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(artistaRoute)],
  exports: [RouterModule],
})
export class ArtistaRoutingModule {}
