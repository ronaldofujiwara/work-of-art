import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TecnicaComponent } from './list/tecnica.component';
import { TecnicaDetailComponent } from './detail/tecnica-detail.component';
import { TecnicaUpdateComponent } from './update/tecnica-update.component';
import { TecnicaDeleteDialogComponent } from './delete/tecnica-delete-dialog.component';
import { TecnicaRoutingModule } from './route/tecnica-routing.module';

@NgModule({
  imports: [SharedModule, TecnicaRoutingModule],
  declarations: [TecnicaComponent, TecnicaDetailComponent, TecnicaUpdateComponent, TecnicaDeleteDialogComponent],
  entryComponents: [TecnicaDeleteDialogComponent],
})
export class TecnicaModule {}
