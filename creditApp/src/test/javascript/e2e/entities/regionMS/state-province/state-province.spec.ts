/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { StateProvinceComponentsPage, StateProvinceDeleteDialog, StateProvinceUpdatePage } from './state-province.page-object';

const expect = chai.expect;

describe('StateProvince e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stateProvinceUpdatePage: StateProvinceUpdatePage;
  let stateProvinceComponentsPage: StateProvinceComponentsPage;
  /*let stateProvinceDeleteDialog: StateProvinceDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StateProvinces', async () => {
    await navBarPage.goToEntity('state-province');
    stateProvinceComponentsPage = new StateProvinceComponentsPage();
    await browser.wait(ec.visibilityOf(stateProvinceComponentsPage.title), 5000);
    expect(await stateProvinceComponentsPage.getTitle()).to.eq('creditApp.regionMsStateProvince.home.title');
  });

  it('should load create StateProvince page', async () => {
    await stateProvinceComponentsPage.clickOnCreateButton();
    stateProvinceUpdatePage = new StateProvinceUpdatePage();
    expect(await stateProvinceUpdatePage.getPageTitle()).to.eq('creditApp.regionMsStateProvince.home.createOrEditLabel');
    await stateProvinceUpdatePage.cancel();
  });

  /* it('should create and save StateProvinces', async () => {
        const nbButtonsBeforeCreate = await stateProvinceComponentsPage.countDeleteButtons();

        await stateProvinceComponentsPage.clickOnCreateButton();
        await promise.all([
            stateProvinceUpdatePage.setNameInput('name'),
            stateProvinceUpdatePage.countrySelectLastOption(),
        ]);
        expect(await stateProvinceUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        await stateProvinceUpdatePage.save();
        expect(await stateProvinceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await stateProvinceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last StateProvince', async () => {
        const nbButtonsBeforeDelete = await stateProvinceComponentsPage.countDeleteButtons();
        await stateProvinceComponentsPage.clickOnLastDeleteButton();

        stateProvinceDeleteDialog = new StateProvinceDeleteDialog();
        expect(await stateProvinceDeleteDialog.getDialogTitle())
            .to.eq('creditApp.regionMsStateProvince.delete.question');
        await stateProvinceDeleteDialog.clickOnConfirmButton();

        expect(await stateProvinceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
