import { GalleriesModule } from './galleries.module';

describe('GalleriesModule', () => {
  let galleriesModule: GalleriesModule;

  beforeEach(() => {
    galleriesModule = new GalleriesModule();
  });

  it('should create an instance', () => {
    expect(galleriesModule).toBeTruthy();
  });
});
