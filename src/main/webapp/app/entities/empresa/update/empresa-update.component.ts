import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmpresa, Empresa } from '../empresa.model';
import { EmpresaService } from '../service/empresa.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';

@Component({
  selector: 'jhi-empresa-update',
  templateUrl: './empresa-update.component.html',
})
export class EmpresaUpdateComponent implements OnInit {
  isSaving = false;

  cidadesSharedCollection: ICidade[] = [];

  editForm = this.fb.group({
    id: [],
    nomeEmpresa: [null, [Validators.required, Validators.maxLength(100)]],
    codigoBrad: [],
    empresa: [null, [Validators.maxLength(50)]],
    nome: [null, [Validators.maxLength(50)]],
    funcao: [null, [Validators.maxLength(50)]],
    cNPJ: [null, [Validators.maxLength(20)]],
    inscricaoEstadual: [null, [Validators.maxLength(10)]],
    obs: [null, [Validators.maxLength(150)]],
    rua: [null, [Validators.maxLength(50)]],
    numero: [null, [Validators.maxLength(5)]],
    bairro: [null, [Validators.maxLength(30)]],
    complemento: [null, [Validators.maxLength(30)]],
    cEP: [null, [Validators.maxLength(8)]],
    telefone: [null, [Validators.maxLength(50)]],
    fax: [null, [Validators.maxLength(50)]],
    celular: [null, [Validators.maxLength(30)]],
    email: [null, [Validators.maxLength(64000)]],
    credito: [null, [Validators.maxLength(150)]],
    inativo: [],
    genEmail: [],
    cidade: [],
  });

  constructor(
    protected empresaService: EmpresaService,
    protected cidadeService: CidadeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.updateForm(empresa);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empresa = this.createFromForm();
    if (empresa.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  trackCidadeById(index: number, item: ICidade): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(empresa: IEmpresa): void {
    this.editForm.patchValue({
      id: empresa.id,
      nomeEmpresa: empresa.nomeEmpresa,
      codigoBrad: empresa.codigoBrad,
      empresa: empresa.empresa,
      nome: empresa.nome,
      funcao: empresa.funcao,
      cNPJ: empresa.cNPJ,
      inscricaoEstadual: empresa.inscricaoEstadual,
      obs: empresa.obs,
      rua: empresa.rua,
      numero: empresa.numero,
      bairro: empresa.bairro,
      complemento: empresa.complemento,
      cEP: empresa.cEP,
      telefone: empresa.telefone,
      fax: empresa.fax,
      celular: empresa.celular,
      email: empresa.email,
      credito: empresa.credito,
      inativo: empresa.inativo,
      genEmail: empresa.genEmail,
      cidade: empresa.cidade,
    });

    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing(this.cidadesSharedCollection, empresa.cidade);
  }

  protected loadRelationshipsOptions(): void {
    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing(cidades, this.editForm.get('cidade')!.value)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }

  protected createFromForm(): IEmpresa {
    return {
      ...new Empresa(),
      id: this.editForm.get(['id'])!.value,
      nomeEmpresa: this.editForm.get(['nomeEmpresa'])!.value,
      codigoBrad: this.editForm.get(['codigoBrad'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      funcao: this.editForm.get(['funcao'])!.value,
      cNPJ: this.editForm.get(['cNPJ'])!.value,
      inscricaoEstadual: this.editForm.get(['inscricaoEstadual'])!.value,
      obs: this.editForm.get(['obs'])!.value,
      rua: this.editForm.get(['rua'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      cEP: this.editForm.get(['cEP'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      celular: this.editForm.get(['celular'])!.value,
      email: this.editForm.get(['email'])!.value,
      credito: this.editForm.get(['credito'])!.value,
      inativo: this.editForm.get(['inativo'])!.value,
      genEmail: this.editForm.get(['genEmail'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
    };
  }
}
