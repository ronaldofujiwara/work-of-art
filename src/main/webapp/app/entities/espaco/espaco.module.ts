import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EspacoComponent } from './list/espaco.component';
import { EspacoDetailComponent } from './detail/espaco-detail.component';
import { EspacoUpdateComponent } from './update/espaco-update.component';
import { EspacoDeleteDialogComponent } from './delete/espaco-delete-dialog.component';
import { EspacoRoutingModule } from './route/espaco-routing.module';

@NgModule({
  imports: [SharedModule, EspacoRoutingModule],
  declarations: [EspacoComponent, EspacoDetailComponent, EspacoUpdateComponent, EspacoDeleteDialogComponent],
  entryComponents: [EspacoDeleteDialogComponent],
})
export class EspacoModule {}
