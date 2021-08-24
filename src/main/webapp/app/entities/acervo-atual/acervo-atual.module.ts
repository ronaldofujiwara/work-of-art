import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AcervoAtualComponent } from './list/acervo-atual.component';
import { AcervoAtualDetailComponent } from './detail/acervo-atual-detail.component';
import { AcervoAtualUpdateComponent } from './update/acervo-atual-update.component';
import { AcervoAtualDeleteDialogComponent } from './delete/acervo-atual-delete-dialog.component';
import { AcervoAtualRoutingModule } from './route/acervo-atual-routing.module';

@NgModule({
  imports: [SharedModule, AcervoAtualRoutingModule],
  declarations: [AcervoAtualComponent, AcervoAtualDetailComponent, AcervoAtualUpdateComponent, AcervoAtualDeleteDialogComponent],
  entryComponents: [AcervoAtualDeleteDialogComponent],
})
export class AcervoAtualModule {}
