import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class BankingEntityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-banking-entity div table .btn-danger'));
  title = element.all(by.css('jhi-banking-entity div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class BankingEntityUpdatePage {
  pageTitle = element(by.id('jhi-banking-entity-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  mnemonicInput = element(by.id('field_mnemonic'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMnemonicInput(mnemonic) {
    await this.mnemonicInput.sendKeys(mnemonic);
  }

  async getMnemonicInput() {
    return await this.mnemonicInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class BankingEntityDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bankingEntity-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bankingEntity'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
