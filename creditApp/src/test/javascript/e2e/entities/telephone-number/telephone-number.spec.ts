/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TelephoneNumberComponentsPage, TelephoneNumberDeleteDialog, TelephoneNumberUpdatePage } from './telephone-number.page-object';

const expect = chai.expect;

describe('TelephoneNumber e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let telephoneNumberUpdatePage: TelephoneNumberUpdatePage;
  let telephoneNumberComponentsPage: TelephoneNumberComponentsPage;
  /*let telephoneNumberDeleteDialog: TelephoneNumberDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TelephoneNumbers', async () => {
    await navBarPage.goToEntity('telephone-number');
    telephoneNumberComponentsPage = new TelephoneNumberComponentsPage();
    await browser.wait(ec.visibilityOf(telephoneNumberComponentsPage.title), 5000);
    expect(await telephoneNumberComponentsPage.getTitle()).to.eq('creditApp.telephoneNumber.home.title');
  });

  it('should load create TelephoneNumber page', async () => {
    await telephoneNumberComponentsPage.clickOnCreateButton();
    telephoneNumberUpdatePage = new TelephoneNumberUpdatePage();
    expect(await telephoneNumberUpdatePage.getPageTitle()).to.eq('creditApp.telephoneNumber.home.createOrEditLabel');
    await telephoneNumberUpdatePage.cancel();
  });

  /* it('should create and save TelephoneNumbers', async () => {
        const nbButtonsBeforeCreate = await telephoneNumberComponentsPage.countDeleteButtons();

        await telephoneNumberComponentsPage.clickOnCreateButton();
        await promise.all([
            telephoneNumberUpdatePage.setCountryCodeInput('5'),
            telephoneNumberUpdatePage.setRegionCodeInput('5'),
            telephoneNumberUpdatePage.setNumberInput('5'),
            telephoneNumberUpdatePage.addressSelectLastOption(),
        ]);
        expect(await telephoneNumberUpdatePage.getCountryCodeInput()).to.eq('5', 'Expected countryCode value to be equals to 5');
        expect(await telephoneNumberUpdatePage.getRegionCodeInput()).to.eq('5', 'Expected regionCode value to be equals to 5');
        expect(await telephoneNumberUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');
        await telephoneNumberUpdatePage.save();
        expect(await telephoneNumberUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await telephoneNumberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last TelephoneNumber', async () => {
        const nbButtonsBeforeDelete = await telephoneNumberComponentsPage.countDeleteButtons();
        await telephoneNumberComponentsPage.clickOnLastDeleteButton();

        telephoneNumberDeleteDialog = new TelephoneNumberDeleteDialog();
        expect(await telephoneNumberDeleteDialog.getDialogTitle())
            .to.eq('creditApp.telephoneNumber.delete.question');
        await telephoneNumberDeleteDialog.clickOnConfirmButton();

        expect(await telephoneNumberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
