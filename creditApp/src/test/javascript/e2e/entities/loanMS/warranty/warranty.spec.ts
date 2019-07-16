/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { WarrantyComponentsPage, WarrantyDeleteDialog, WarrantyUpdatePage } from './warranty.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Warranty e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let warrantyUpdatePage: WarrantyUpdatePage;
  let warrantyComponentsPage: WarrantyComponentsPage;
  /*let warrantyDeleteDialog: WarrantyDeleteDialog;*/
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Warranties', async () => {
    await navBarPage.goToEntity('warranty');
    warrantyComponentsPage = new WarrantyComponentsPage();
    await browser.wait(ec.visibilityOf(warrantyComponentsPage.title), 5000);
    expect(await warrantyComponentsPage.getTitle()).to.eq('creditApp.loanMsWarranty.home.title');
  });

  it('should load create Warranty page', async () => {
    await warrantyComponentsPage.clickOnCreateButton();
    warrantyUpdatePage = new WarrantyUpdatePage();
    expect(await warrantyUpdatePage.getPageTitle()).to.eq('creditApp.loanMsWarranty.home.createOrEditLabel');
    await warrantyUpdatePage.cancel();
  });

  /* it('should create and save Warranties', async () => {
        const nbButtonsBeforeCreate = await warrantyComponentsPage.countDeleteButtons();

        await warrantyComponentsPage.clickOnCreateButton();
        await promise.all([
            warrantyUpdatePage.setNameInput('name'),
            warrantyUpdatePage.setDescriptionInput('description'),
            warrantyUpdatePage.setValueInput('5'),
            warrantyUpdatePage.setDocumentInput(absolutePath),
            warrantyUpdatePage.setDebtorIdentificationInput('debtorIdentification'),
            warrantyUpdatePage.setDebtorIdentificationTypeInput('debtorIdentificationType'),
            warrantyUpdatePage.setDebtorCountryInput('debtorCountry'),
            // warrantyUpdatePage.loanProcessSelectLastOption(),
        ]);
        expect(await warrantyUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await warrantyUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
        expect(await warrantyUpdatePage.getValueInput()).to.eq('5', 'Expected value value to be equals to 5');
        expect(await warrantyUpdatePage.getDocumentInput()).to.endsWith(fileNameToUpload, 'Expected Document value to be end with ' + fileNameToUpload);
        expect(await warrantyUpdatePage.getDebtorIdentificationInput()).to.eq('debtorIdentification', 'Expected DebtorIdentification value to be equals to debtorIdentification');
        expect(await warrantyUpdatePage.getDebtorIdentificationTypeInput()).to.eq('debtorIdentificationType', 'Expected DebtorIdentificationType value to be equals to debtorIdentificationType');
        expect(await warrantyUpdatePage.getDebtorCountryInput()).to.eq('debtorCountry', 'Expected DebtorCountry value to be equals to debtorCountry');
        await warrantyUpdatePage.save();
        expect(await warrantyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await warrantyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Warranty', async () => {
        const nbButtonsBeforeDelete = await warrantyComponentsPage.countDeleteButtons();
        await warrantyComponentsPage.clickOnLastDeleteButton();

        warrantyDeleteDialog = new WarrantyDeleteDialog();
        expect(await warrantyDeleteDialog.getDialogTitle())
            .to.eq('creditApp.loanMsWarranty.delete.question');
        await warrantyDeleteDialog.clickOnConfirmButton();

        expect(await warrantyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
