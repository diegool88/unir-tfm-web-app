/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { BankingEntityComponentsPage, BankingEntityDeleteDialog, BankingEntityUpdatePage } from './banking-entity.page-object';

const expect = chai.expect;

describe('BankingEntity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bankingEntityUpdatePage: BankingEntityUpdatePage;
  let bankingEntityComponentsPage: BankingEntityComponentsPage;
  let bankingEntityDeleteDialog: BankingEntityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BankingEntities', async () => {
    await navBarPage.goToEntity('banking-entity');
    bankingEntityComponentsPage = new BankingEntityComponentsPage();
    await browser.wait(ec.visibilityOf(bankingEntityComponentsPage.title), 5000);
    expect(await bankingEntityComponentsPage.getTitle()).to.eq('creditApp.bankMsBankingEntity.home.title');
  });

  it('should load create BankingEntity page', async () => {
    await bankingEntityComponentsPage.clickOnCreateButton();
    bankingEntityUpdatePage = new BankingEntityUpdatePage();
    expect(await bankingEntityUpdatePage.getPageTitle()).to.eq('creditApp.bankMsBankingEntity.home.createOrEditLabel');
    await bankingEntityUpdatePage.cancel();
  });

  it('should create and save BankingEntities', async () => {
    const nbButtonsBeforeCreate = await bankingEntityComponentsPage.countDeleteButtons();

    await bankingEntityComponentsPage.clickOnCreateButton();
    await promise.all([
      bankingEntityUpdatePage.setMnemonicInput('mnemonic'),
      bankingEntityUpdatePage.setNameInput('name'),
      bankingEntityUpdatePage.setDescriptionInput('description')
    ]);
    expect(await bankingEntityUpdatePage.getMnemonicInput()).to.eq('mnemonic', 'Expected Mnemonic value to be equals to mnemonic');
    expect(await bankingEntityUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await bankingEntityUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    await bankingEntityUpdatePage.save();
    expect(await bankingEntityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bankingEntityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BankingEntity', async () => {
    const nbButtonsBeforeDelete = await bankingEntityComponentsPage.countDeleteButtons();
    await bankingEntityComponentsPage.clickOnLastDeleteButton();

    bankingEntityDeleteDialog = new BankingEntityDeleteDialog();
    expect(await bankingEntityDeleteDialog.getDialogTitle()).to.eq('creditApp.bankMsBankingEntity.delete.question');
    await bankingEntityDeleteDialog.clickOnConfirmButton();

    expect(await bankingEntityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
