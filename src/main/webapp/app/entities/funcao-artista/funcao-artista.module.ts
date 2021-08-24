import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FuncaoArtistaComponent } from './list/funcao-artista.component';
import { FuncaoArtistaDetailComponent } from './detail/funcao-artista-detail.component';
import { FuncaoArtistaUpdateComponent } from './update/funcao-artista-update.component';
import { FuncaoArtistaDeleteDialogComponent } from './delete/funcao-artista-delete-dialog.component';
import { FuncaoArtistaRoutingModule } from './route/funcao-artista-routing.module';

@NgModule({
  imports: [SharedModule, FuncaoArtistaRoutingModule],
  declarations: [FuncaoArtistaComponent, FuncaoArtistaDetailComponent, FuncaoArtistaUpdateComponent, FuncaoArtistaDeleteDialogComponent],
  entryComponents: [FuncaoArtistaDeleteDialogComponent],
})
export class FuncaoArtistaModule {}
