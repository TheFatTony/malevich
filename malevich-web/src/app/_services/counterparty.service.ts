import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {CounterpartyDto} from "../_transfer/counterpartyDto";
import {GalleryDto, OrganizationDto, PersonDto} from "../_transfer";
import {AddressDto} from "../_transfer/addressDto";

@Injectable({
  providedIn: 'root'
})
export class CounterpartyService {

  private url = environment.baseUrl + 'counterparty';

  constructor(private http: HttpClient) {
  }

  getCurrent() {
    return this.http
      .get<CounterpartyDto>(this.url + '/current');
  }

  update(dto: CounterpartyDto) {
    const url = this.url + '/update';
    return this.http.put(url, dto)
  }
  
  initInstance(dto: CounterpartyDto) : CounterpartyDto{
    if(dto.isOrganization && !dto.organization){
      dto.organization = new OrganizationDto();
      dto.organization.legalNameMl = new Map<string, string>();
      dto.organization.addresses = [new AddressDto()];
    }

    if(!dto.isOrganization && !dto.person){
      dto.person = new PersonDto();
      dto.person.addresses = [new AddressDto()];
    }

    if(dto.isGallery && !dto.gallery){
      dto.gallery = new GalleryDto();
      dto.gallery.descriptionMl = new Map<string, string>();
    }

    return dto;
  }

}
