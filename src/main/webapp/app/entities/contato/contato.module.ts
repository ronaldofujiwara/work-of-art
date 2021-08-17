import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContatoComponent } from './list/contato.component';
import { ContatoDetailComponent } from './detail/contato-detail.component';
import { ContatoUpdateComponent } from './update/contato-update.component';
import { ContatoDeleteDialogComponent } from './delete/contato-delete-dialog.component';
import { ContatoRoutingModule } from './route/contato-routing.module';

@NgModule({
  imports: [SharedModule, ContatoRoutingModule],
  declarations: [ContatoComponent, ContatoDetailComponent, ContatoUpdateComponent, ContatoDeleteDialogComponent],
  entryComponents: [ContatoDeleteDialogComponent],
})
export class ContatoModule {}
