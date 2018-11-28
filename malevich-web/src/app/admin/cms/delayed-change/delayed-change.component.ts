import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
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
export class DelayedChangeComponent implements OnInit {

  @ViewChild('myGrid') myGrid: jqxGridComponent;
  @ViewChild('myWindow') myWindow: jqxWindowComponent;


  delayedChanges: DelayedChangeDto[];
  delayedChange: DelayedChangeDto;

  private x: number;
  private y: number;

  columns: any[] =
    [
      {datafield: 'Id', width: '25%', columntype: 'textbox'},
      {datafield: 'TypeId', width: '25%', columntype: 'textbox'},
      {
        datafield: 'View', width: '5%', columntype: 'button',
        cellsrenderer: (): string => {
          return 'View';
        },
        buttonclick: (row: number): void => {
          this.delayedChange = this.delayedChanges.find(data => data.id === this.myGrid.getrowdata(row).Id);
          this.myWindow.position({x: this.x, y: this.y});
          this.myWindow.open();
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
      }
    ];

  constructor(private delayedChangeService: DelayedChangeService, public translate: TranslateService) {
  }

  getDelayedChanges(): void {
    this.delayedChangeService
      .getDelayedChanges()
      .subscribe(
        data => (this.delayedChanges = data)
      );
  }

  ngOnInit() {
    this.getDelayedChanges();
  }

  @HostListener('mousedown', ['$event'])
  mouseHandling(event) {
    this.x = event.pageX;
    this.y = event.pageY;
  }

}
