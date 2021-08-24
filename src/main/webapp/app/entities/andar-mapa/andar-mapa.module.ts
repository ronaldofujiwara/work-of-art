import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AndarMapaComponent } from './list/andar-mapa.component';
import { AndarMapaDetailComponent } from './detail/andar-mapa-detail.component';
import { AndarMapaUpdateComponent } from './update/andar-mapa-update.component';
import { AndarMapaDeleteDialogComponent } from './delete/andar-mapa-delete-dialog.component';
import { AndarMapaRoutingModule } from './route/andar-mapa-routing.module';

@NgModule({
  imports: [SharedModule, AndarMapaRoutingModule],
  declarations: [AndarMapaComponent, AndarMapaDetailComponent, AndarMapaUpdateComponent, AndarMapaDeleteDialogComponent],
  entryComponents: [AndarMapaDeleteDialogComponent],
})
export class AndarMapaModule {}
