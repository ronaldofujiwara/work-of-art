import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MoedaComponent } from './list/moeda.component';
import { MoedaDetailComponent } from './detail/moeda-detail.component';
import { MoedaUpdateComponent } from './update/moeda-update.component';
import { MoedaDeleteDialogComponent } from './delete/moeda-delete-dialog.component';
import { MoedaRoutingModule } from './route/moeda-routing.module';

@NgModule({
  imports: [SharedModule, MoedaRoutingModule],
  declarations: [MoedaComponent, MoedaDetailComponent, MoedaUpdateComponent, MoedaDeleteDialogComponent],
  entryComponents: [MoedaDeleteDialogComponent],
})
export class MoedaModule {}
