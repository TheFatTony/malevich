export class OrganizationDto {
  id: number;
  legalName: string;
  location: string;
  legalNameMl: Map<string, string>;
  locationMl: Map<string, string>;
}
