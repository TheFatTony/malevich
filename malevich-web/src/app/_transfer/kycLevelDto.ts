export type KycLevel = 'T_TIER0' | 'T_TIER1' | 'T_TIER2' | 'G_TIER0' | 'G_TIER1';

export class KycLevelDto {
  id: KycLevel;
  level: number;
}
