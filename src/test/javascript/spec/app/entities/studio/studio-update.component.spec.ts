/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UrbanZenTestModule } from '../../../test.module';
import { StudioUpdateComponent } from 'app/entities/studio/studio-update.component';
import { StudioService } from 'app/entities/studio/studio.service';
import { Studio } from 'app/shared/model/studio.model';

describe('Component Tests', () => {
  describe('Studio Management Update Component', () => {
    let comp: StudioUpdateComponent;
    let fixture: ComponentFixture<StudioUpdateComponent>;
    let service: StudioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [StudioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StudioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Studio(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Studio();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
