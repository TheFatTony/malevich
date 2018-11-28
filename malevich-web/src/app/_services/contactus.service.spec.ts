import {inject, TestBed} from '@angular/core/testing';
import {ContactUsService} from './contactus.service';


describe('ContactUsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ContactUsService]
    });
  });

  it('should be created', inject([ContactUsService], (service: ContactUsService) => {
    expect(service).toBeTruthy();
  }));
});
