import {AfterViewInit, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-profile-trader-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit, AfterViewInit {

  isEditing: boolean = false;

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    $['HSCore'].components.HSMaskedInput.init('[data-mask]');
    $['HSCore'].components.HSModalWindow.init('[data-modal-target]');
  }

  switchMode() {
    this.isEditing = !this.isEditing;
  }

}
