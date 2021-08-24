import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NivelComponent } from './list/nivel.component';
import { NivelDetailComponent } from './detail/nivel-detail.component';
import { NivelUpdateComponent } from './update/nivel-update.component';
import { NivelDeleteDialogComponent } from './delete/nivel-delete-dialog.component';
import { NivelRoutingModule } from './route/nivel-routing.module';

@NgModule({
  imports: [SharedModule, NivelRoutingModule],
  declarations: [NivelComponent, NivelDetailComponent, NivelUpdateComponent, NivelDeleteDialogComponent],
  entryComponents: [NivelDeleteDialogComponent],
})
export class NivelModule {}
