import {FenergyUiPage} from './app.po';

describe('fenergy-ui App', () => {
  let page: FenergyUiPage;

  beforeEach(() => {
    page = new FenergyUiPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
