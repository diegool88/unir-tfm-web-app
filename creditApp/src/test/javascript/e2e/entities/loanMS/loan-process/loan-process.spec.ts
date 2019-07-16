/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { LoanProcessComponentsPage, LoanProcessDeleteDialog, LoanProcessUpdatePage } from './loan-process.page-object';

const expect = chai.expect;

describe('LoanProcess e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let loanProcessUpdatePage: LoanProcessUpdatePage;
  let loanProcessComponentsPage: LoanProcessComponentsPage;
  let loanProcessDeleteDialog: LoanProcessDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LoanProcesses', async () => {
    await navBarPage.goToEntity('loan-process');
    loanProcessComponentsPage = new LoanProcessComponentsPage();
    await browser.wait(ec.visibilityOf(loanProcessComponentsPage.title), 5000);
    expect(await loanProcessComponentsPage.getTitle()).to.eq('creditApp.loanMsLoanProcess.home.title');
  });

  it('should load create LoanProcess page', async () => {
    await loanProcessComponentsPage.clickOnCreateButton();
    loanProcessUpdatePage = new LoanProcessUpdatePage();
    expect(await loanProcessUpdatePage.getPageTitle()).to.eq('creditApp.loanMsLoanProcess.home.createOrEditLabel');
    await loanProcessUpdatePage.cancel();
  });

  it('should create and save LoanProcesses', async () => {
    const nbButtonsBeforeCreate = await loanProcessComponentsPage.countDeleteButtons();

    await loanProcessComponentsPage.clickOnCreateButton();
    await promise.all([
      loanProcessUpdatePage.setNameInput('name'),
      loanProcessUpdatePage.setRequestedAmountInput('5'),
      loanProcessUpdatePage.setGivenAmountInput('5'),
      loanProcessUpdatePage.setLoanPeriodInput('5'),
      loanProcessUpdatePage.setStartDateInput('2000-12-31'),
      loanProcessUpdatePage.setEndDateInput('2000-12-31'),
      loanProcessUpdatePage.setJustificationInput('justification'),
      loanProcessUpdatePage.setDebtorIdentificationInput('debtorIdentification'),
      loanProcessUpdatePage.setDebtorIdentificationTypeInput('debtorIdentificationType'),
      loanProcessUpdatePage.setDebtorCountryInput('debtorCountry'),
      loanProcessUpdatePage.setBankingEntityMnemonicInput('bankingEntityMnemonic'),
      loanProcessUpdatePage.setBankingProductMnemonicInput('bankingProductMnemonic'),
      loanProcessUpdatePage.loanProcessStatusSelectLastOption()
    ]);
    expect(await loanProcessUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await loanProcessUpdatePage.getRequestedAmountInput()).to.eq('5', 'Expected requestedAmount value to be equals to 5');
    expect(await loanProcessUpdatePage.getGivenAmountInput()).to.eq('5', 'Expected givenAmount value to be equals to 5');
    expect(await loanProcessUpdatePage.getLoanPeriodInput()).to.eq('5', 'Expected loanPeriod value to be equals to 5');
    expect(await loanProcessUpdatePage.getStartDateInput()).to.eq('2000-12-31', 'Expected startDate value to be equals to 2000-12-31');
    expect(await loanProcessUpdatePage.getEndDateInput()).to.eq('2000-12-31', 'Expected endDate value to be equals to 2000-12-31');
    expect(await loanProcessUpdatePage.getJustificationInput()).to.eq(
      'justification',
      'Expected Justification value to be equals to justification'
    );
    expect(await loanProcessUpdatePage.getDebtorIdentificationInput()).to.eq(
      'debtorIdentification',
      'Expected DebtorIdentification value to be equals to debtorIdentification'
    );
    expect(await loanProcessUpdatePage.getDebtorIdentificationTypeInput()).to.eq(
      'debtorIdentificationType',
      'Expected DebtorIdentificationType value to be equals to debtorIdentificationType'
    );
    expect(await loanProcessUpdatePage.getDebtorCountryInput()).to.eq(
      'debtorCountry',
      'Expected DebtorCountry value to be equals to debtorCountry'
    );
    expect(await loanProcessUpdatePage.getBankingEntityMnemonicInput()).to.eq(
      'bankingEntityMnemonic',
      'Expected BankingEntityMnemonic value to be equals to bankingEntityMnemonic'
    );
    expect(await loanProcessUpdatePage.getBankingProductMnemonicInput()).to.eq(
      'bankingProductMnemonic',
      'Expected BankingProductMnemonic value to be equals to bankingProductMnemonic'
    );
    await loanProcessUpdatePage.save();
    expect(await loanProcessUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await loanProcessComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LoanProcess', async () => {
    const nbButtonsBeforeDelete = await loanProcessComponentsPage.countDeleteButtons();
    await loanProcessComponentsPage.clickOnLastDeleteButton();

    loanProcessDeleteDialog = new LoanProcessDeleteDialog();
    expect(await loanProcessDeleteDialog.getDialogTitle()).to.eq('creditApp.loanMsLoanProcess.delete.question');
    await loanProcessDeleteDialog.clickOnConfirmButton();

    expect(await loanProcessComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
