import {Component, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from 'jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid';
import {User} from '../../../_transfer';
import {UsersService} from '../../_services/users.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  @ViewChild('myGrid') myGrid: jqxGridComponent;
  users: User[];

  constructor(private usersService: UsersService) { }

  getUsers(): void {
    this.usersService
      .getUsers()
      .subscribe(
        heroes => (this.users = heroes)
      );
  }

  ngOnInit() {
    this.getUsers();
  }
}
