import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.dev";
import {HttpClient} from "@angular/common/http";
import {ParticipantDto} from "../_transfer/participantDto";
import {OrganizationDto, PersonDto} from "../_transfer";
import {AddressDto} from "../_transfer/addressDto";
import {TranslateService} from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})
export class ParticipantService {

  private url = environment.baseUrl + 'participant';

  constructor(private http: HttpClient,
              private translate: TranslateService) {
  }

  getCurrent() {
    return this.http
      .get<ParticipantDto>(this.url + '/current');
  }

  isTraderPerson(participant: ParticipantDto) {
    return participant != null && participant.type != null && participant.type.id == 'TP';
  }

  isTraderOrganization(participant: ParticipantDto) {
    return participant != null && participant.type != null && participant.type.id == 'TO';
  }

  isGallery(participant: ParticipantDto) {
    return participant != null && participant.type != null && participant.type.id == 'G';
  }

  isOrganization(participant: ParticipantDto) {
    return this.isGallery(participant) || this.isTraderOrganization(participant);
  }

  private isPerson(participant: ParticipantDto) {
    return this.isTraderPerson(participant);
  }

  update(dto: ParticipantDto) {
    const url = this.url + '/update';
    return this.http.put(url, dto)
  }

  initInstance(dto: ParticipantDto): ParticipantDto {
    if (!dto.addresses || !dto.addresses.length)
      dto.addresses = [new AddressDto()];

    if (this.isOrganization(dto)) {
      dto.organization = dto.organization || new OrganizationDto();
      dto.organization.legalNameMl = dto.organization.legalNameMl || new Map<string, string>();
    }

    if (this.isPerson(dto) && !dto.person) {
      dto.person = new PersonDto();
    }

    if (this.isGallery(dto)) {
      dto.titleMl = dto.titleMl || new Map<string, string>();
      dto.descriptionMl = dto.descriptionMl || new Map<string, string>();
    }

    return dto;
  }

  getName(participant: ParticipantDto) {
    if (participant.person && participant.person.firstName && participant.person.lastName) {
      return `${participant.person.firstName} ${participant.person.lastName}`
    } else if (participant.organization && participant.organization.legalNameMl) {
      return participant.organization.legalNameMl[this.translate.currentLang] || participant.organization.legalNameMl['en'];
    }

    return null;
  }

}
