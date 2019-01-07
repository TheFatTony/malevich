import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ParticipantDto} from "../_transfer/participantDto";
import {map} from "rxjs/operators";
import {TraderDto} from "../_transfer/traderDto";
import {GalleryDto} from "../_transfer";

@Injectable({
  providedIn: 'root'
})
export class ParticipantService {

  private url = environment.baseUrl + 'participant';

  constructor(private http: HttpClient) {
  }

  getCurrent() {
    return this.http
      .get<ParticipantDto>(this.url + '/current')
      // .pipe(map<any, ParticipantDto>(data => {
      //   if (!data) return null;
      //
      //   let participant = data as ParticipantDto;
      //
      //   // todo replace with type matching
      //   if (data.person) return data as TraderDto;
      //
      //   if(data.organization) return data as GalleryDto;
      //
      //   return null;
      // }))
      // ;
  }

  private getTyped<T extends ParticipantDto>(participant: ParticipantDto, typeId:string){
    if(participant != null && participant.type && participant.type.id == typeId)
      return participant as T;

    return null;
  }

  getTraderPerson(participant: ParticipantDto){
    return this.getTyped<TraderDto>(participant, 'TP');
  }

  getGallery(participant: ParticipantDto){
    return this.getTyped<GalleryDto>(participant, 'G');
  }

  update(dto: ParticipantDto) {
    const url = this.url + '/update';
    return this.http.put(url, dto)
  }

  initInstance(dto: ParticipantDto): ParticipantDto {
    // if(dto.isOrganization && !dto.organization){
    //   dto.organization = new OrganizationDto();
    //   dto.organization.legalNameMl = new Map<string, string>();
    //   dto.organization.addresses = [new AddressDto()];
    // }
    //
    // if(!dto.isOrganization && !dto.person){
    //   dto.person = new PersonDto();
    //   dto.person.addresses = [new AddressDto()];
    // }
    //
    // if(dto.isGallery && !dto.gallery){
    //   dto.gallery = new GalleryDto();
    //   dto.gallery.descriptionMl = new Map<string, string>();
    // }

    return dto;
  }

}
