import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AmbienteComponent } from './list/ambiente.component';
import { AmbienteDetailComponent } from './detail/ambiente-detail.component';
import { AmbienteUpdateComponent } from './update/ambiente-update.component';
import { AmbienteDeleteDialogComponent } from './delete/ambiente-delete-dialog.component';
import { AmbienteRoutingModule } from './route/ambiente-routing.module';

@NgModule({
  imports: [SharedModule, AmbienteRoutingModule],
  declarations: [AmbienteComponent, AmbienteDetailComponent, AmbienteUpdateComponent, AmbienteDeleteDialogComponent],
  entryComponents: [AmbienteDeleteDialogComponent],
})
export class AmbienteModule {}
