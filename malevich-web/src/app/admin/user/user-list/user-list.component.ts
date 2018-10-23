import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {UserDto} from '../../../_transfer';
import {UsersService} from '../../_services/users.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  users: UserDto[];

  constructor(private usersService: UsersService) {
    $.jqx.theme = 'metrodark';
  }

  getUsers(): void {
    this.usersService
      .getUsers()
      .subscribe(
        data => (this.users = data)
      );
  }

  ngOnInit() {
    this.getUsers();
  }
}
