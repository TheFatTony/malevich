import {Component, OnInit} from '@angular/core';
import {AdminService} from "../_services/admin.service";

@Component({
  selector: 'app-admin-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit {

  constructor(private adminService: AdminService) {
  }

  ngOnInit() {
  }

  sendAllMail(): void {
    this.adminService.sendAllMail().subscribe();
  }

  checkBalance(): void {
    this.adminService.checkBalance().subscribe();
  }

  marketOrdersCheck(): void {
    this.adminService.marketOrdersCheck().subscribe();
  }

  revolutDepositCheck(): void {
    this.adminService.revolutDepositCheck().subscribe();
  }

  sendAllMessages(): void {
    this.adminService.sendAllMessages().subscribe();
  }

}
