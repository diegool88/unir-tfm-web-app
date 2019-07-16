import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class WarrantyComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-warranty div table .btn-danger'));
  title = element.all(by.css('jhi-warranty div h2#page-heading span')).first();

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

export class WarrantyUpdatePage {
  pageTitle = element(by.id('jhi-warranty-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  valueInput = element(by.id('field_value'));
  documentInput = element(by.id('file_document'));
  debtorIdentificationInput = element(by.id('field_debtorIdentification'));
  debtorIdentificationTypeInput = element(by.id('field_debtorIdentificationType'));
  debtorCountryInput = element(by.id('field_debtorCountry'));
  loanProcessSelect = element(by.id('field_loanProcess'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setValueInput(value) {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput() {
    return await this.valueInput.getAttribute('value');
  }

  async setDocumentInput(document) {
    await this.documentInput.sendKeys(document);
  }

  async getDocumentInput() {
    return await this.documentInput.getAttribute('value');
  }

  async setDebtorIdentificationInput(debtorIdentification) {
    await this.debtorIdentificationInput.sendKeys(debtorIdentification);
  }

  async getDebtorIdentificationInput() {
    return await this.debtorIdentificationInput.getAttribute('value');
  }

  async setDebtorIdentificationTypeInput(debtorIdentificationType) {
    await this.debtorIdentificationTypeInput.sendKeys(debtorIdentificationType);
  }

  async getDebtorIdentificationTypeInput() {
    return await this.debtorIdentificationTypeInput.getAttribute('value');
  }

  async setDebtorCountryInput(debtorCountry) {
    await this.debtorCountryInput.sendKeys(debtorCountry);
  }

  async getDebtorCountryInput() {
    return await this.debtorCountryInput.getAttribute('value');
  }

  async loanProcessSelectLastOption(timeout?: number) {
    await this.loanProcessSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async loanProcessSelectOption(option) {
    await this.loanProcessSelect.sendKeys(option);
  }

  getLoanProcessSelect(): ElementFinder {
    return this.loanProcessSelect;
  }

  async getLoanProcessSelectedOption() {
    return await this.loanProcessSelect.element(by.css('option:checked')).getText();
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

export class WarrantyDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-warranty-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-warranty'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
