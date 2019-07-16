/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AddressComponentsPage, AddressDeleteDialog, AddressUpdatePage } from './address.page-object';

const expect = chai.expect;

describe('Address e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let addressUpdatePage: AddressUpdatePage;
  let addressComponentsPage: AddressComponentsPage;
  /*let addressDeleteDialog: AddressDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Addresses', async () => {
    await navBarPage.goToEntity('address');
    addressComponentsPage = new AddressComponentsPage();
    await browser.wait(ec.visibilityOf(addressComponentsPage.title), 5000);
    expect(await addressComponentsPage.getTitle()).to.eq('creditApp.address.home.title');
  });

  it('should load create Address page', async () => {
    await addressComponentsPage.clickOnCreateButton();
    addressUpdatePage = new AddressUpdatePage();
    expect(await addressUpdatePage.getPageTitle()).to.eq('creditApp.address.home.createOrEditLabel');
    await addressUpdatePage.cancel();
  });

  /* it('should create and save Addresses', async () => {
        const nbButtonsBeforeCreate = await addressComponentsPage.countDeleteButtons();

        await addressComponentsPage.clickOnCreateButton();
        await promise.all([
            addressUpdatePage.setMainStreetInput('mainStreet'),
            addressUpdatePage.setSecondaryStreetInput('secondaryStreet'),
            addressUpdatePage.setNumberInput('number'),
            addressUpdatePage.setCityInput('city'),
            addressUpdatePage.setProvinceInput('province'),
            addressUpdatePage.setCountryInput('country'),
            addressUpdatePage.setPostalCodeInput('5'),
            addressUpdatePage.addressTypeSelectLastOption(),
            addressUpdatePage.customerSelectLastOption(),
        ]);
        expect(await addressUpdatePage.getMainStreetInput()).to.eq('mainStreet', 'Expected MainStreet value to be equals to mainStreet');
        expect(await addressUpdatePage.getSecondaryStreetInput()).to.eq('secondaryStreet', 'Expected SecondaryStreet value to be equals to secondaryStreet');
        expect(await addressUpdatePage.getNumberInput()).to.eq('number', 'Expected Number value to be equals to number');
        expect(await addressUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
        expect(await addressUpdatePage.getProvinceInput()).to.eq('province', 'Expected Province value to be equals to province');
        expect(await addressUpdatePage.getCountryInput()).to.eq('country', 'Expected Country value to be equals to country');
        expect(await addressUpdatePage.getPostalCodeInput()).to.eq('5', 'Expected postalCode value to be equals to 5');
        await addressUpdatePage.save();
        expect(await addressUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Address', async () => {
        const nbButtonsBeforeDelete = await addressComponentsPage.countDeleteButtons();
        await addressComponentsPage.clickOnLastDeleteButton();

        addressDeleteDialog = new AddressDeleteDialog();
        expect(await addressDeleteDialog.getDialogTitle())
            .to.eq('creditApp.address.delete.question');
        await addressDeleteDialog.clickOnConfirmButton();

        expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
