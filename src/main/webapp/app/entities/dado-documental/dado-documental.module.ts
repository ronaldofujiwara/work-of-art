import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DadoDocumentalComponent } from './list/dado-documental.component';
import { DadoDocumentalDetailComponent } from './detail/dado-documental-detail.component';
import { DadoDocumentalUpdateComponent } from './update/dado-documental-update.component';
import { DadoDocumentalDeleteDialogComponent } from './delete/dado-documental-delete-dialog.component';
import { DadoDocumentalRoutingModule } from './route/dado-documental-routing.module';

@NgModule({
  imports: [SharedModule, DadoDocumentalRoutingModule],
  declarations: [
    DadoDocumentalComponent,
    DadoDocumentalDetailComponent,
    DadoDocumentalUpdateComponent,
    DadoDocumentalDeleteDialogComponent,
  ],
  entryComponents: [DadoDocumentalDeleteDialogComponent],
})
export class DadoDocumentalModule {}
