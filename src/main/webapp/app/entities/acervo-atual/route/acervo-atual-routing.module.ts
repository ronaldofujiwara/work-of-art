import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AcervoAtualComponent } from '../list/acervo-atual.component';
import { AcervoAtualDetailComponent } from '../detail/acervo-atual-detail.component';
import { AcervoAtualUpdateComponent } from '../update/acervo-atual-update.component';
import { AcervoAtualRoutingResolveService } from './acervo-atual-routing-resolve.service';

const acervoAtualRoute: Routes = [
  {
    path: '',
    component: AcervoAtualComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcervoAtualDetailComponent,
    resolve: {
      acervoAtual: AcervoAtualRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcervoAtualUpdateComponent,
    resolve: {
      acervoAtual: AcervoAtualRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcervoAtualUpdateComponent,
    resolve: {
      acervoAtual: AcervoAtualRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(acervoAtualRoute)],
  exports: [RouterModule],
})
export class AcervoAtualRoutingModule {}
