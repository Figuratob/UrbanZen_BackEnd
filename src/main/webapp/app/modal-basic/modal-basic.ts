import { Component, Inject, Output } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';
import { Router } from '@angular/router';

@Component({
  selector: 'ngbd-modal-basic',
  templateUrl: './modal-basic.html'
})
export class NgbdModalBasic {
  closeResult: string;
  @Output()
  startDate: string;
  @Output()
  endDate: string;

  constructor(
    private modalService: NgbModal,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    @Inject(Router) public router: Router
  ) {
    this.router = router;
  }

  open(content) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }
  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
  createTimetable(startDate, endDate) {
    if (moment(startDate).isBefore(moment(endDate))) {
      console.log('created');
      this.eventManager.broadcast({
        name: 'createTimetableEvent',
        startDate: this.startDate,
        endDate: this.endDate
        // content: 'Deleted an lessonTemplate'
      });
      this.modalService.dismissAll();
      this.router.navigate(['/lesson']);
    } else {
      console.log('startDate should be before endDate');
      this.onError('urbanZenApp.lessonTemplate.errorDates');
    }
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
