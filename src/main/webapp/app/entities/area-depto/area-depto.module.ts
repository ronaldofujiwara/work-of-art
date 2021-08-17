import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AreaDeptoComponent } from './list/area-depto.component';
import { AreaDeptoDetailComponent } from './detail/area-depto-detail.component';
import { AreaDeptoUpdateComponent } from './update/area-depto-update.component';
import { AreaDeptoDeleteDialogComponent } from './delete/area-depto-delete-dialog.component';
import { AreaDeptoRoutingModule } from './route/area-depto-routing.module';

@NgModule({
  imports: [SharedModule, AreaDeptoRoutingModule],
  declarations: [AreaDeptoComponent, AreaDeptoDetailComponent, AreaDeptoUpdateComponent, AreaDeptoDeleteDialogComponent],
  entryComponents: [AreaDeptoDeleteDialogComponent],
})
export class AreaDeptoModule {}
