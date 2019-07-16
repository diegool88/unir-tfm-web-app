/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  BankingTransactionComponentsPage,
  BankingTransactionDeleteDialog,
  BankingTransactionUpdatePage
} from './banking-transaction.page-object';

const expect = chai.expect;

describe('BankingTransaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bankingTransactionUpdatePage: BankingTransactionUpdatePage;
  let bankingTransactionComponentsPage: BankingTransactionComponentsPage;
  /*let bankingTransactionDeleteDialog: BankingTransactionDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BankingTransactions', async () => {
    await navBarPage.goToEntity('banking-transaction');
    bankingTransactionComponentsPage = new BankingTransactionComponentsPage();
    await browser.wait(ec.visibilityOf(bankingTransactionComponentsPage.title), 5000);
    expect(await bankingTransactionComponentsPage.getTitle()).to.eq('creditApp.bankMsBankingTransaction.home.title');
  });

  it('should load create BankingTransaction page', async () => {
    await bankingTransactionComponentsPage.clickOnCreateButton();
    bankingTransactionUpdatePage = new BankingTransactionUpdatePage();
    expect(await bankingTransactionUpdatePage.getPageTitle()).to.eq('creditApp.bankMsBankingTransaction.home.createOrEditLabel');
    await bankingTransactionUpdatePage.cancel();
  });

  /* it('should create and save BankingTransactions', async () => {
        const nbButtonsBeforeCreate = await bankingTransactionComponentsPage.countDeleteButtons();

        await bankingTransactionComponentsPage.clickOnCreateButton();
        await promise.all([
            bankingTransactionUpdatePage.setNumberInput('5'),
            bankingTransactionUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            bankingTransactionUpdatePage.setAmmountInput('5'),
            bankingTransactionUpdatePage.transactionTypeSelectLastOption(),
            bankingTransactionUpdatePage.setExtOriginAccountInput('5'),
            bankingTransactionUpdatePage.extOriginAccountTypeSelectLastOption(),
            bankingTransactionUpdatePage.setExtOriginAccountBankInput('extOriginAccountBank'),
            bankingTransactionUpdatePage.setExtDestinationAccountInput('5'),
            bankingTransactionUpdatePage.extDestinationAccountTypeSelectLastOption(),
            bankingTransactionUpdatePage.setExtDestinationAccountBankInput('extDestinationAccountBank'),
            bankingTransactionUpdatePage.originAccountSelectLastOption(),
            bankingTransactionUpdatePage.destinationAccountSelectLastOption(),
            bankingTransactionUpdatePage.bankingEntitySelectLastOption(),
        ]);
        expect(await bankingTransactionUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');
        expect(await bankingTransactionUpdatePage.getDateInput()).to.contain('2001-01-01T02:30', 'Expected date value to be equals to 2000-12-31');
        expect(await bankingTransactionUpdatePage.getAmmountInput()).to.eq('5', 'Expected ammount value to be equals to 5');
        expect(await bankingTransactionUpdatePage.getExtOriginAccountInput()).to.eq('5', 'Expected extOriginAccount value to be equals to 5');
        expect(await bankingTransactionUpdatePage.getExtOriginAccountBankInput()).to.eq('extOriginAccountBank', 'Expected ExtOriginAccountBank value to be equals to extOriginAccountBank');
        expect(await bankingTransactionUpdatePage.getExtDestinationAccountInput()).to.eq('5', 'Expected extDestinationAccount value to be equals to 5');
        expect(await bankingTransactionUpdatePage.getExtDestinationAccountBankInput()).to.eq('extDestinationAccountBank', 'Expected ExtDestinationAccountBank value to be equals to extDestinationAccountBank');
        await bankingTransactionUpdatePage.save();
        expect(await bankingTransactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await bankingTransactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last BankingTransaction', async () => {
        const nbButtonsBeforeDelete = await bankingTransactionComponentsPage.countDeleteButtons();
        await bankingTransactionComponentsPage.clickOnLastDeleteButton();

        bankingTransactionDeleteDialog = new BankingTransactionDeleteDialog();
        expect(await bankingTransactionDeleteDialog.getDialogTitle())
            .to.eq('creditApp.bankMsBankingTransaction.delete.question');
        await bankingTransactionDeleteDialog.clickOnConfirmButton();

        expect(await bankingTransactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
