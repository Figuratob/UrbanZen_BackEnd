import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UrbanZenSharedModule } from 'app/shared';
import {
  BookingComponent,
  BookingDetailComponent,
  BookingUpdateComponent,
  BookingDeletePopupComponent,
  BookingDeleteDialogComponent,
  bookingRoute,
  bookingPopupRoute
} from './';

const ENTITY_STATES = [...bookingRoute, ...bookingPopupRoute];

@NgModule({
  imports: [UrbanZenSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BookingComponent,
    BookingDetailComponent,
    BookingUpdateComponent,
    BookingDeleteDialogComponent,
    BookingDeletePopupComponent
  ],
  entryComponents: [BookingComponent, BookingUpdateComponent, BookingDeleteDialogComponent, BookingDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UrbanZenBookingModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
