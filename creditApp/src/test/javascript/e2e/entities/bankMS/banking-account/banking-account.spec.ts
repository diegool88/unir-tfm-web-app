/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { BankingAccountComponentsPage, BankingAccountDeleteDialog, BankingAccountUpdatePage } from './banking-account.page-object';

const expect = chai.expect;

describe('BankingAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bankingAccountUpdatePage: BankingAccountUpdatePage;
  let bankingAccountComponentsPage: BankingAccountComponentsPage;
  let bankingAccountDeleteDialog: BankingAccountDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BankingAccounts', async () => {
    await navBarPage.goToEntity('banking-account');
    bankingAccountComponentsPage = new BankingAccountComponentsPage();
    await browser.wait(ec.visibilityOf(bankingAccountComponentsPage.title), 5000);
    expect(await bankingAccountComponentsPage.getTitle()).to.eq('creditApp.bankMsBankingAccount.home.title');
  });

  it('should load create BankingAccount page', async () => {
    await bankingAccountComponentsPage.clickOnCreateButton();
    bankingAccountUpdatePage = new BankingAccountUpdatePage();
    expect(await bankingAccountUpdatePage.getPageTitle()).to.eq('creditApp.bankMsBankingAccount.home.createOrEditLabel');
    await bankingAccountUpdatePage.cancel();
  });

  it('should create and save BankingAccounts', async () => {
    const nbButtonsBeforeCreate = await bankingAccountComponentsPage.countDeleteButtons();

    await bankingAccountComponentsPage.clickOnCreateButton();
    await promise.all([
      bankingAccountUpdatePage.setNumberInput('5'),
      bankingAccountUpdatePage.accountTypeSelectLastOption(),
      bankingAccountUpdatePage.setCurrentBalanceInput('5'),
      bankingAccountUpdatePage.setAvailableBalanceInput('5'),
      bankingAccountUpdatePage.setCustomerIdentificationInput('customerIdentification'),
      bankingAccountUpdatePage.setCustomerIdentificationTypeInput('customerIdentificationType'),
      bankingAccountUpdatePage.setCustomerCountryInput('customerCountry')
    ]);
    expect(await bankingAccountUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');
    expect(await bankingAccountUpdatePage.getCurrentBalanceInput()).to.eq('5', 'Expected currentBalance value to be equals to 5');
    expect(await bankingAccountUpdatePage.getAvailableBalanceInput()).to.eq('5', 'Expected availableBalance value to be equals to 5');
    expect(await bankingAccountUpdatePage.getCustomerIdentificationInput()).to.eq(
      'customerIdentification',
      'Expected CustomerIdentification value to be equals to customerIdentification'
    );
    expect(await bankingAccountUpdatePage.getCustomerIdentificationTypeInput()).to.eq(
      'customerIdentificationType',
      'Expected CustomerIdentificationType value to be equals to customerIdentificationType'
    );
    expect(await bankingAccountUpdatePage.getCustomerCountryInput()).to.eq(
      'customerCountry',
      'Expected CustomerCountry value to be equals to customerCountry'
    );
    await bankingAccountUpdatePage.save();
    expect(await bankingAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bankingAccountComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BankingAccount', async () => {
    const nbButtonsBeforeDelete = await bankingAccountComponentsPage.countDeleteButtons();
    await bankingAccountComponentsPage.clickOnLastDeleteButton();

    bankingAccountDeleteDialog = new BankingAccountDeleteDialog();
    expect(await bankingAccountDeleteDialog.getDialogTitle()).to.eq('creditApp.bankMsBankingAccount.delete.question');
    await bankingAccountDeleteDialog.clickOnConfirmButton();

    expect(await bankingAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
