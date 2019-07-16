/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { FeeComponentsPage, FeeDeleteDialog, FeeUpdatePage } from './fee.page-object';

const expect = chai.expect;

describe('Fee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let feeUpdatePage: FeeUpdatePage;
  let feeComponentsPage: FeeComponentsPage;
  /*let feeDeleteDialog: FeeDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Fees', async () => {
    await navBarPage.goToEntity('fee');
    feeComponentsPage = new FeeComponentsPage();
    await browser.wait(ec.visibilityOf(feeComponentsPage.title), 5000);
    expect(await feeComponentsPage.getTitle()).to.eq('creditApp.bankMsFee.home.title');
  });

  it('should load create Fee page', async () => {
    await feeComponentsPage.clickOnCreateButton();
    feeUpdatePage = new FeeUpdatePage();
    expect(await feeUpdatePage.getPageTitle()).to.eq('creditApp.bankMsFee.home.createOrEditLabel');
    await feeUpdatePage.cancel();
  });

  /* it('should create and save Fees', async () => {
        const nbButtonsBeforeCreate = await feeComponentsPage.countDeleteButtons();

        await feeComponentsPage.clickOnCreateButton();
        await promise.all([
            feeUpdatePage.setMnemonicInput('mnemonic'),
            feeUpdatePage.setNameInput('name'),
            feeUpdatePage.setDescriptionInput('description'),
            feeUpdatePage.setInterestRateInput('5'),
            feeUpdatePage.setUniqueValueInput('5'),
            // feeUpdatePage.productSelectLastOption(),
        ]);
        expect(await feeUpdatePage.getMnemonicInput()).to.eq('mnemonic', 'Expected Mnemonic value to be equals to mnemonic');
        expect(await feeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await feeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await feeUpdatePage.getInterestRateInput()).to.eq('5', 'Expected interestRate value to be equals to 5');
        expect(await feeUpdatePage.getUniqueValueInput()).to.eq('5', 'Expected uniqueValue value to be equals to 5');
        await feeUpdatePage.save();
        expect(await feeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await feeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Fee', async () => {
        const nbButtonsBeforeDelete = await feeComponentsPage.countDeleteButtons();
        await feeComponentsPage.clickOnLastDeleteButton();

        feeDeleteDialog = new FeeDeleteDialog();
        expect(await feeDeleteDialog.getDialogTitle())
            .to.eq('creditApp.bankMsFee.delete.question');
        await feeDeleteDialog.clickOnConfirmButton();

        expect(await feeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
