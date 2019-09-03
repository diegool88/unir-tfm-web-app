/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomerComponentsPage, CustomerDeleteDialog, CustomerUpdatePage } from './customer.page-object';

const expect = chai.expect;

describe('Customer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerUpdatePage: CustomerUpdatePage;
  let customerComponentsPage: CustomerComponentsPage;
  /*let customerDeleteDialog: CustomerDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Customers', async () => {
    await navBarPage.goToEntity('customer');
    customerComponentsPage = new CustomerComponentsPage();
    await browser.wait(ec.visibilityOf(customerComponentsPage.title), 5000);
    expect(await customerComponentsPage.getTitle()).to.eq('creditApp.customer.home.title');
  });

  it('should load create Customer page', async () => {
    await customerComponentsPage.clickOnCreateButton();
    customerUpdatePage = new CustomerUpdatePage();
    expect(await customerUpdatePage.getPageTitle()).to.eq('creditApp.customer.home.createOrEditLabel');
    await customerUpdatePage.cancel();
  });

  /* it('should create and save Customers', async () => {
        const nbButtonsBeforeCreate = await customerComponentsPage.countDeleteButtons();

        await customerComponentsPage.clickOnCreateButton();
        await promise.all([
            customerUpdatePage.setFirstnameInput('firstname'),
            customerUpdatePage.setSecondNameInput('secondName'),
            customerUpdatePage.setLastnameInput('lastname'),
            customerUpdatePage.setSecondLastnameInput('secondLastname'),
            customerUpdatePage.identificationTypeSelectLastOption(),
            customerUpdatePage.setIdentificationNumberInput('identificationNumber'),
            customerUpdatePage.genreSelectLastOption(),
            customerUpdatePage.setEmailInput('email'),
            customerUpdatePage.setBirthDateInput('2000-12-31'),
            customerUpdatePage.setCountryInput('country'),
            customerUpdatePage.setClientSinceInput('2000-12-31'),
            customerUpdatePage.setMonthlyIncomeInput('5'),
            customerUpdatePage.userSelectLastOption(),
        ]);
        expect(await customerUpdatePage.getFirstnameInput()).to.eq('firstname', 'Expected Firstname value to be equals to firstname');
        expect(await customerUpdatePage.getSecondNameInput()).to.eq('secondName', 'Expected SecondName value to be equals to secondName');
        expect(await customerUpdatePage.getLastnameInput()).to.eq('lastname', 'Expected Lastname value to be equals to lastname');
        expect(await customerUpdatePage.getSecondLastnameInput()).to.eq('secondLastname', 'Expected SecondLastname value to be equals to secondLastname');
        expect(await customerUpdatePage.getIdentificationNumberInput()).to.eq('identificationNumber', 'Expected IdentificationNumber value to be equals to identificationNumber');
        expect(await customerUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
        expect(await customerUpdatePage.getBirthDateInput()).to.eq('2000-12-31', 'Expected birthDate value to be equals to 2000-12-31');
        expect(await customerUpdatePage.getCountryInput()).to.eq('country', 'Expected Country value to be equals to country');
        expect(await customerUpdatePage.getClientSinceInput()).to.eq('2000-12-31', 'Expected clientSince value to be equals to 2000-12-31');
        expect(await customerUpdatePage.getMonthlyIncomeInput()).to.eq('5', 'Expected monthlyIncome value to be equals to 5');
        await customerUpdatePage.save();
        expect(await customerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Customer', async () => {
        const nbButtonsBeforeDelete = await customerComponentsPage.countDeleteButtons();
        await customerComponentsPage.clickOnLastDeleteButton();

        customerDeleteDialog = new CustomerDeleteDialog();
        expect(await customerDeleteDialog.getDialogTitle())
            .to.eq('creditApp.customer.delete.question');
        await customerDeleteDialog.clickOnConfirmButton();

        expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
