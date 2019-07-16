/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ProductComponentsPage, ProductDeleteDialog, ProductUpdatePage } from './product.page-object';

const expect = chai.expect;

describe('Product e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productUpdatePage: ProductUpdatePage;
  let productComponentsPage: ProductComponentsPage;
  /*let productDeleteDialog: ProductDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Products', async () => {
    await navBarPage.goToEntity('product');
    productComponentsPage = new ProductComponentsPage();
    await browser.wait(ec.visibilityOf(productComponentsPage.title), 5000);
    expect(await productComponentsPage.getTitle()).to.eq('creditApp.bankMsProduct.home.title');
  });

  it('should load create Product page', async () => {
    await productComponentsPage.clickOnCreateButton();
    productUpdatePage = new ProductUpdatePage();
    expect(await productUpdatePage.getPageTitle()).to.eq('creditApp.bankMsProduct.home.createOrEditLabel');
    await productUpdatePage.cancel();
  });

  /* it('should create and save Products', async () => {
        const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

        await productComponentsPage.clickOnCreateButton();
        await promise.all([
            productUpdatePage.setMnemonicInput('mnemonic'),
            productUpdatePage.setNameInput('name'),
            productUpdatePage.setDescriptionInput('description'),
            productUpdatePage.categorySelectLastOption(),
            productUpdatePage.setStartDateInput('2000-12-31'),
            productUpdatePage.setEndDateInput('2000-12-31'),
            productUpdatePage.stateSelectLastOption(),
            productUpdatePage.setInterestRateInput('5'),
            productUpdatePage.bankingEntitySelectLastOption(),
        ]);
        expect(await productUpdatePage.getMnemonicInput()).to.eq('mnemonic', 'Expected Mnemonic value to be equals to mnemonic');
        expect(await productUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await productUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await productUpdatePage.getStartDateInput()).to.eq('2000-12-31', 'Expected startDate value to be equals to 2000-12-31');
        expect(await productUpdatePage.getEndDateInput()).to.eq('2000-12-31', 'Expected endDate value to be equals to 2000-12-31');
        expect(await productUpdatePage.getInterestRateInput()).to.eq('5', 'Expected interestRate value to be equals to 5');
        await productUpdatePage.save();
        expect(await productUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Product', async () => {
        const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
        await productComponentsPage.clickOnLastDeleteButton();

        productDeleteDialog = new ProductDeleteDialog();
        expect(await productDeleteDialog.getDialogTitle())
            .to.eq('creditApp.bankMsProduct.delete.question');
        await productDeleteDialog.clickOnConfirmButton();

        expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
