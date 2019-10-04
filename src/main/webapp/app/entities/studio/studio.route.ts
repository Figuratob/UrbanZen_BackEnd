import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Studio } from 'app/shared/model/studio.model';
import { StudioService } from './studio.service';
import { StudioComponent } from './studio.component';
import { StudioDetailComponent } from './studio-detail.component';
import { StudioUpdateComponent } from './studio-update.component';
import { StudioDeletePopupComponent } from './studio-delete-dialog.component';
import { IStudio } from 'app/shared/model/studio.model';

@Injectable({ providedIn: 'root' })
export class StudioResolve implements Resolve<IStudio> {
  constructor(private service: StudioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudio> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Studio>) => response.ok),
        map((studio: HttpResponse<Studio>) => studio.body)
      );
    }
    return of(new Studio());
  }
}

export const studioRoute: Routes = [
  {
    path: '',
    component: StudioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.studio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StudioDetailComponent,
    resolve: {
      studio: StudioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.studio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StudioUpdateComponent,
    resolve: {
      studio: StudioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.studio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StudioUpdateComponent,
    resolve: {
      studio: StudioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.studio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const studioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StudioDeletePopupComponent,
    resolve: {
      studio: StudioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.studio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
