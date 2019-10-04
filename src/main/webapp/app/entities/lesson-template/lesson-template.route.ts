import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LessonTemplate } from 'app/shared/model/lesson-template.model';
import { LessonTemplateService } from './lesson-template.service';
import { LessonTemplateComponent } from './lesson-template.component';
import { LessonTemplateDetailComponent } from './lesson-template-detail.component';
import { LessonTemplateUpdateComponent } from './lesson-template-update.component';
import { LessonTemplateDeletePopupComponent } from './lesson-template-delete-dialog.component';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';

@Injectable({ providedIn: 'root' })
export class LessonTemplateResolve implements Resolve<ILessonTemplate> {
  constructor(private service: LessonTemplateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILessonTemplate> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LessonTemplate>) => response.ok),
        map((lessonTemplate: HttpResponse<LessonTemplate>) => lessonTemplate.body)
      );
    }
    return of(new LessonTemplate());
  }
}

export const lessonTemplateRoute: Routes = [
  {
    path: '',
    component: LessonTemplateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.lessonTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LessonTemplateDetailComponent,
    resolve: {
      lessonTemplate: LessonTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.lessonTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LessonTemplateUpdateComponent,
    resolve: {
      lessonTemplate: LessonTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.lessonTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LessonTemplateUpdateComponent,
    resolve: {
      lessonTemplate: LessonTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.lessonTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lessonTemplatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LessonTemplateDeletePopupComponent,
    resolve: {
      lessonTemplate: LessonTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'urbanZenApp.lessonTemplate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
