import { ArtworksModule } from './artworks.module';

describe('ArtworksModule', () => {
  let artworksModule: ArtworksModule;

  beforeEach(() => {
    artworksModule = new ArtworksModule();
  });

  it('should create an instance', () => {
    expect(artworksModule).toBeTruthy();
  });
});
