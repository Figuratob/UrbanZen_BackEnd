import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStudio } from 'app/shared/model/studio.model';
import { AccountService } from 'app/core';
import { StudioService } from './studio.service';

@Component({
  selector: 'jhi-studio',
  templateUrl: './studio.component.html'
})
export class StudioComponent implements OnInit, OnDestroy {
  studios: IStudio[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected studioService: StudioService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.studioService
      .query()
      .pipe(
        filter((res: HttpResponse<IStudio[]>) => res.ok),
        map((res: HttpResponse<IStudio[]>) => res.body)
      )
      .subscribe(
        (res: IStudio[]) => {
          this.studios = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStudios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStudio) {
    return item.id;
  }

  registerChangeInStudios() {
    this.eventSubscriber = this.eventManager.subscribe('studioListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
