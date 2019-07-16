/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AmortizationTableComponentsPage,
  AmortizationTableDeleteDialog,
  AmortizationTableUpdatePage
} from './amortization-table.page-object';

const expect = chai.expect;

describe('AmortizationTable e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let amortizationTableUpdatePage: AmortizationTableUpdatePage;
  let amortizationTableComponentsPage: AmortizationTableComponentsPage;
  /*let amortizationTableDeleteDialog: AmortizationTableDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AmortizationTables', async () => {
    await navBarPage.goToEntity('amortization-table');
    amortizationTableComponentsPage = new AmortizationTableComponentsPage();
    await browser.wait(ec.visibilityOf(amortizationTableComponentsPage.title), 5000);
    expect(await amortizationTableComponentsPage.getTitle()).to.eq('creditApp.loanMsAmortizationTable.home.title');
  });

  it('should load create AmortizationTable page', async () => {
    await amortizationTableComponentsPage.clickOnCreateButton();
    amortizationTableUpdatePage = new AmortizationTableUpdatePage();
    expect(await amortizationTableUpdatePage.getPageTitle()).to.eq('creditApp.loanMsAmortizationTable.home.createOrEditLabel');
    await amortizationTableUpdatePage.cancel();
  });

  /* it('should create and save AmortizationTables', async () => {
        const nbButtonsBeforeCreate = await amortizationTableComponentsPage.countDeleteButtons();

        await amortizationTableComponentsPage.clickOnCreateButton();
        await promise.all([
            amortizationTableUpdatePage.setOrderInput('5'),
            amortizationTableUpdatePage.setDueDateInput('2000-12-31'),
            amortizationTableUpdatePage.setAmountInput('5'),
            amortizationTableUpdatePage.setInterestInput('5'),
            amortizationTableUpdatePage.loanProcessSelectLastOption(),
        ]);
        expect(await amortizationTableUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
        expect(await amortizationTableUpdatePage.getDueDateInput()).to.eq('2000-12-31', 'Expected dueDate value to be equals to 2000-12-31');
        expect(await amortizationTableUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
        expect(await amortizationTableUpdatePage.getInterestInput()).to.eq('5', 'Expected interest value to be equals to 5');
        await amortizationTableUpdatePage.save();
        expect(await amortizationTableUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await amortizationTableComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last AmortizationTable', async () => {
        const nbButtonsBeforeDelete = await amortizationTableComponentsPage.countDeleteButtons();
        await amortizationTableComponentsPage.clickOnLastDeleteButton();

        amortizationTableDeleteDialog = new AmortizationTableDeleteDialog();
        expect(await amortizationTableDeleteDialog.getDialogTitle())
            .to.eq('creditApp.loanMsAmortizationTable.delete.question');
        await amortizationTableDeleteDialog.clickOnConfirmButton();

        expect(await amortizationTableComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
