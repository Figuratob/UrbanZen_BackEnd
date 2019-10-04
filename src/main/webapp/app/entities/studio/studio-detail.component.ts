import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudio } from 'app/shared/model/studio.model';

@Component({
  selector: 'jhi-studio-detail',
  templateUrl: './studio-detail.component.html'
})
export class StudioDetailComponent implements OnInit {
  studio: IStudio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studio }) => {
      this.studio = studio;
    });
  }

  previousState() {
    window.history.back();
  }
}
