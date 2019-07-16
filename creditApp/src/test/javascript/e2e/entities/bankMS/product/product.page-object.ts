import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product div table .btn-danger'));
  title = element.all(by.css('jhi-product div h2#page-heading span')).first();

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

export class ProductUpdatePage {
  pageTitle = element(by.id('jhi-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  mnemonicInput = element(by.id('field_mnemonic'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  categorySelect = element(by.id('field_category'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  stateSelect = element(by.id('field_state'));
  interestRateInput = element(by.id('field_interestRate'));
  bankingEntitySelect = element(by.id('field_bankingEntity'));

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

  async setCategorySelect(category) {
    await this.categorySelect.sendKeys(category);
  }

  async getCategorySelect() {
    return await this.categorySelect.element(by.css('option:checked')).getText();
  }

  async categorySelectLastOption(timeout?: number) {
    await this.categorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return await this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate) {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput() {
    return await this.endDateInput.getAttribute('value');
  }

  async setStateSelect(state) {
    await this.stateSelect.sendKeys(state);
  }

  async getStateSelect() {
    return await this.stateSelect.element(by.css('option:checked')).getText();
  }

  async stateSelectLastOption(timeout?: number) {
    await this.stateSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setInterestRateInput(interestRate) {
    await this.interestRateInput.sendKeys(interestRate);
  }

  async getInterestRateInput() {
    return await this.interestRateInput.getAttribute('value');
  }

  async bankingEntitySelectLastOption(timeout?: number) {
    await this.bankingEntitySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async bankingEntitySelectOption(option) {
    await this.bankingEntitySelect.sendKeys(option);
  }

  getBankingEntitySelect(): ElementFinder {
    return this.bankingEntitySelect;
  }

  async getBankingEntitySelectedOption() {
    return await this.bankingEntitySelect.element(by.css('option:checked')).getText();
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

export class ProductDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-product-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-product'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
