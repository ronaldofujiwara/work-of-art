import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FuncaoArtistaComponent } from '../list/funcao-artista.component';
import { FuncaoArtistaDetailComponent } from '../detail/funcao-artista-detail.component';
import { FuncaoArtistaUpdateComponent } from '../update/funcao-artista-update.component';
import { FuncaoArtistaRoutingResolveService } from './funcao-artista-routing-resolve.service';

const funcaoArtistaRoute: Routes = [
  {
    path: '',
    component: FuncaoArtistaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuncaoArtistaDetailComponent,
    resolve: {
      funcaoArtista: FuncaoArtistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuncaoArtistaUpdateComponent,
    resolve: {
      funcaoArtista: FuncaoArtistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuncaoArtistaUpdateComponent,
    resolve: {
      funcaoArtista: FuncaoArtistaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(funcaoArtistaRoute)],
  exports: [RouterModule],
})
export class FuncaoArtistaRoutingModule {}
