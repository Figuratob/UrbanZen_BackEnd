import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'teacher',
        loadChildren: () => import('./teacher/teacher.module').then(m => m.UrbanZenTeacherModule)
      },
      {
        path: 'lesson-template',
        loadChildren: () => import('./lesson-template/lesson-template.module').then(m => m.UrbanZenLessonTemplateModule)
      },
      {
        path: 'lesson',
        loadChildren: () => import('./lesson/lesson.module').then(m => m.UrbanZenLessonModule)
      },
      {
        path: 'booking',
        loadChildren: () => import('./booking/booking.module').then(m => m.UrbanZenBookingModule)
      },
      {
        path: 'studio',
        loadChildren: () => import('./studio/studio.module').then(m => m.UrbanZenStudioModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UrbanZenEntityModule {}
