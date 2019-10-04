import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudio } from 'app/shared/model/studio.model';
import { StudioService } from './studio.service';

@Component({
  selector: 'jhi-studio-delete-dialog',
  templateUrl: './studio-delete-dialog.component.html'
})
export class StudioDeleteDialogComponent {
  studio: IStudio;

  constructor(protected studioService: StudioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.studioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'studioListModification',
        content: 'Deleted an studio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-studio-delete-popup',
  template: ''
})
export class StudioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StudioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.studio = studio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/studio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/studio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
