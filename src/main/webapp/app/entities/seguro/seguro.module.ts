import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SeguroComponent } from './list/seguro.component';
import { SeguroDetailComponent } from './detail/seguro-detail.component';
import { SeguroUpdateComponent } from './update/seguro-update.component';
import { SeguroDeleteDialogComponent } from './delete/seguro-delete-dialog.component';
import { SeguroRoutingModule } from './route/seguro-routing.module';

@NgModule({
  imports: [SharedModule, SeguroRoutingModule],
  declarations: [SeguroComponent, SeguroDetailComponent, SeguroUpdateComponent, SeguroDeleteDialogComponent],
  entryComponents: [SeguroDeleteDialogComponent],
})
export class SeguroModule {}
