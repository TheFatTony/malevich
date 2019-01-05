import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {UserDto} from '../../../_transfer';
import {UsersService} from '../../_services/users.service';
import {jqxWindowComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow';
import {UserPasswordDto} from '../../../_transfer/userPasswordDto';
import jqxInput = jqwidgets.jqxInput;

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('lockWindow') lockWindow: jqxWindowComponent;
  @ViewChild('passwordWindow') passwordWindow: jqxWindowComponent;
  @ViewChild('myInput') myInput: jqxInput;

  selectedRowIndex: number = -1;
  users: UserDto[];
  userPassword: UserPasswordDto;
  user: UserDto;

  x: number;
  y: number;

  constructor(private usersService: UsersService) {
  }

  ngOnInit() {
    this.userPassword = new UserPasswordDto();
    this.user = new UserDto();
    this.getUsers();
  }

  getUsers(): void {
    this.usersService
      .getUsers()
      .subscribe(
        data => (this.users = data)
      );
  }

  openLockWindow() {
    this.lockWindow.width(310);
    this.lockWindow.height(240);
    this.lockWindow.open();
    this.lockWindow.move(this.x, this.y);
  }

  openPasswordWindow() {
    this.passwordWindow.width(310);
    this.passwordWindow.height(240);
    this.passwordWindow.open();
    this.passwordWindow.move(this.x, this.y);
  }

  onGridRowSelect($event: any) {
    this.selectedRowIndex = $event.args.rowindex;
  }

  sendButton() {
    let locked = this.users.splice(this.selectedRowIndex)[0];
    this.user.name = locked.name;
    this.user.activityFlag = locked.activityFlag;
    this.usersService.lockUser(this.user).subscribe(() => {
      this.getUsers();
    });
    this.lockWindow.close();
    this.myGrid.refresh();
  }

  cancelButton() {
    this.lockWindow.close();
  }

  submitButton() {
    let change = this.users.splice(this.selectedRowIndex)[0];
    this.userPassword.name = change.name;
    this.usersService.setPassword(this.userPassword).subscribe(() => {
      this.myInput.val('');
      this.getUsers();
    });
    this.passwordWindow.close();
    this.myGrid.refresh();
  }

  onLockWindowOpen() {
  }

  onPasswordWindowOpen() {
    this.myInput.val('');
  }

  onChange($event) {
    if (!$event)
      return;

    if (!this.userPassword.password)
      this.userPassword.password = '';

    this.userPassword.password = $event;
  }

}
