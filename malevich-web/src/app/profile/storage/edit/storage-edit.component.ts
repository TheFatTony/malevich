import {Component, OnInit, ViewChild} from '@angular/core';
import {ArtworkDto, GalleryDto} from "../../../_transfer";
import {ArtworkStockDto} from "../../../_transfer/artworkStockDto";
import {ArtworkStockService} from "../../../_services/artwork-stock.service";
import {TranslateService} from "@ngx-translate/core";
import {jqxComboBoxComponent} from "jqwidgets-scripts/jqwidgets-ts/angular_jqxcombobox";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {environment} from "../../../../environments/environment.dev";

@Component({
  selector: 'app-profile-storage-edit',
  templateUrl: './storage-edit.component.html',
  styleUrls: ['./storage-edit.component.css']
})
export class StorageEditComponent implements OnInit {

  @ViewChild('addArtWorkComboBox') addArtWorkComboBox: jqxComboBoxComponent;

  public url = environment.baseUrl;

  gallery: GalleryDto;

  artworkStock: ArtworkStockDto;
  public artwork: ArtworkDto;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private artworkStockService: ArtworkStockService,
              public translate: TranslateService) {
    }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      let id = params['id'];
      this.artworkStockService.getArtworkStock(id)
        .subscribe(a => {
          this.artworkStock = a;
          this.artwork = a.artwork;
        })
    });
  }

  submit() {
    this.artworkStockService.updateArtworkStock(this.artworkStock).subscribe();
    this.router.navigate(['/profile/storage']);
  }

  cancel() {
    this.router.navigate(['/profile/storage']);
  }
}
