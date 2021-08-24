import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ArtistaComponent } from './list/artista.component';
import { ArtistaDetailComponent } from './detail/artista-detail.component';
import { ArtistaUpdateComponent } from './update/artista-update.component';
import { ArtistaDeleteDialogComponent } from './delete/artista-delete-dialog.component';
import { ArtistaRoutingModule } from './route/artista-routing.module';

@NgModule({
  imports: [SharedModule, ArtistaRoutingModule],
  declarations: [ArtistaComponent, ArtistaDetailComponent, ArtistaUpdateComponent, ArtistaDeleteDialogComponent],
  entryComponents: [ArtistaDeleteDialogComponent],
})
export class ArtistaModule {}
