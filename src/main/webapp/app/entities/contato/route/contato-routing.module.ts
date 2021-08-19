import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContatoComponent } from '../list/contato.component';
import { ContatoDetailComponent } from '../detail/contato-detail.component';
import { ContatoUpdateComponent } from '../update/contato-update.component';
import { ContatoRoutingResolveService } from './contato-routing-resolve.service';

const contatoRoute: Routes = [
  {
    path: '',
    component: ContatoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContatoDetailComponent,
    resolve: {
      contato: ContatoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContatoUpdateComponent,
    resolve: {
      contato: ContatoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContatoUpdateComponent,
    resolve: {
      contato: ContatoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contatoRoute)],
  exports: [RouterModule],
})
export class ContatoRoutingModule {}
