import {Component, OnInit} from '@angular/core';
import {ContactUsDto} from '../../_transfer/contactUsDto';
import {ContactUsService} from '../../_services/contactus.service';
import {AlertService} from 'yinyang-core';

@Component({
  selector: 'app-main-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  contactUs: ContactUsDto;

  constructor(private contactUsService: ContactUsService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.contactUs = new ContactUsDto();
  }

  submit(): void {
    this.contactUsService.save(this.contactUs).subscribe(
      data => {
        this.alertService.success(data);
      },
      error => {
        this.alertService.error(error);
      });
    this.contactUs.name = '';
    this.contactUs.emailId = '';
    this.contactUs.phone = '';
    this.contactUs.message = '';
    this.contactUs.subject = '';
  }

}
