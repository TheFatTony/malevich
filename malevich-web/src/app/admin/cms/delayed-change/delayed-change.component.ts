import {Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {jqxGridComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxgrid";
import {TranslateService} from "@ngx-translate/core";
import {DelayedChangeDto} from "../../../_transfer/delayedChangeDto";
import {DelayedChangeService} from "../../../_services/delayed-change.service";
import {jqxWindowComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxwindow";

@Component({
  selector: 'app-delayed-change',
  templateUrl: './delayed-change.component.html',
  styleUrls: ['./delayed-change.component.css']
})
export class DelayedChangeComponent implements OnInit, OnDestroy {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('viewWindow') viewWindow: jqxWindowComponent;
  @ViewChild('declineWindow') declineWindow: jqxWindowComponent;


  delayedChanges: DelayedChangeDto[];
  delayedChange: DelayedChangeDto;

  private x: number;
  private y: number;

  comment: string;

  columns: any[] =
    [
      {datafield: 'Id', width: '5%', columntype: 'textbox'},
      {datafield: 'TypeId', width: '40%', columntype: 'textbox'},
      {datafield: 'User', width: '40%', columntype: 'textbox'},
      {
        datafield: 'View', width: '5%', columntype: 'button',
        cellsrenderer: (): string => {
          return 'View';
        },
        buttonclick: (row: number): void => {
          this.delayedChange = this.delayedChanges.find(data => data.id === this.myGrid.getrowdata(row).Id);
          this.viewWindow.position({x: this.x, y: this.y});
          this.viewWindow.open();
        }
      },
      {
        datafield: 'Approve', width: '5%', columntype: 'button',
        cellsrenderer: (): string => {
          return 'Approve';
        },
        buttonclick: (row: number): void => {
          this.delayedChange = this.delayedChanges.find(data => data.id === this.myGrid.getrowdata(row).Id);
          this.delayedChangeService.approveChange(this.delayedChange).subscribe(() => {
            this.getDelayedChanges();
          });
        }
      },
      {
        datafield: 'Decline', width: '5%', columntype: 'button',
        cellsrenderer: (): string => {
          return 'Decline';
        },
        buttonclick: (row: number): void => {
          this.delayedChange = this.delayedChanges.find(data => data.id === this.myGrid.getrowdata(row).Id);
          this.comment = "";
          this.declineWindow.position({x: this.x, y: this.y});
          this.declineWindow.open();
        }
      }
    ];

  constructor(private delayedChangeService: DelayedChangeService, public translate: TranslateService) {
  }

  ngOnInit() {
    this.getDelayedChanges();
  }

  ngOnDestroy(): void {
    this.viewWindow.close();
    this.declineWindow.close();
  }

  getDelayedChanges(): void {
    this.delayedChangeService
      .getDelayedChanges()
      .subscribe(
        data => (this.delayedChanges = data)
      );
  }

  sendButton(): void {
    this.delayedChange.comment = this.comment;
    this.delayedChangeService.declineChange(this.delayedChange).subscribe(() => {
      this.getDelayedChanges();
      this.declineWindow.close();
    });
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

}
