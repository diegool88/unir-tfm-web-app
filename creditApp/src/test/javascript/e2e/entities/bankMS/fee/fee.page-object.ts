import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-fee div table .btn-danger'));
  title = element.all(by.css('jhi-fee div h2#page-heading span')).first();

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

export class FeeUpdatePage {
  pageTitle = element(by.id('jhi-fee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  mnemonicInput = element(by.id('field_mnemonic'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  interestRateInput = element(by.id('field_interestRate'));
  uniqueValueInput = element(by.id('field_uniqueValue'));
  productSelect = element(by.id('field_product'));

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

  async setInterestRateInput(interestRate) {
    await this.interestRateInput.sendKeys(interestRate);
  }

  async getInterestRateInput() {
    return await this.interestRateInput.getAttribute('value');
  }

  async setUniqueValueInput(uniqueValue) {
    await this.uniqueValueInput.sendKeys(uniqueValue);
  }

  async getUniqueValueInput() {
    return await this.uniqueValueInput.getAttribute('value');
  }

  async productSelectLastOption(timeout?: number) {
    await this.productSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productSelectOption(option) {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption() {
    return await this.productSelect.element(by.css('option:checked')).getText();
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

export class FeeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-fee-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-fee'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
