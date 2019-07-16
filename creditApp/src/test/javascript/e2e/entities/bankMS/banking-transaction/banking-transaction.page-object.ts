import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class BankingTransactionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-banking-transaction div table .btn-danger'));
  title = element.all(by.css('jhi-banking-transaction div h2#page-heading span')).first();

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

export class BankingTransactionUpdatePage {
  pageTitle = element(by.id('jhi-banking-transaction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numberInput = element(by.id('field_number'));
  dateInput = element(by.id('field_date'));
  ammountInput = element(by.id('field_ammount'));
  transactionTypeSelect = element(by.id('field_transactionType'));
  extOriginAccountInput = element(by.id('field_extOriginAccount'));
  extOriginAccountTypeSelect = element(by.id('field_extOriginAccountType'));
  extOriginAccountBankInput = element(by.id('field_extOriginAccountBank'));
  extDestinationAccountInput = element(by.id('field_extDestinationAccount'));
  extDestinationAccountTypeSelect = element(by.id('field_extDestinationAccountType'));
  extDestinationAccountBankInput = element(by.id('field_extDestinationAccountBank'));
  originAccountSelect = element(by.id('field_originAccount'));
  destinationAccountSelect = element(by.id('field_destinationAccount'));
  bankingEntitySelect = element(by.id('field_bankingEntity'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumberInput(number) {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput() {
    return await this.numberInput.getAttribute('value');
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return await this.dateInput.getAttribute('value');
  }

  async setAmmountInput(ammount) {
    await this.ammountInput.sendKeys(ammount);
  }

  async getAmmountInput() {
    return await this.ammountInput.getAttribute('value');
  }

  async setTransactionTypeSelect(transactionType) {
    await this.transactionTypeSelect.sendKeys(transactionType);
  }

  async getTransactionTypeSelect() {
    return await this.transactionTypeSelect.element(by.css('option:checked')).getText();
  }

  async transactionTypeSelectLastOption(timeout?: number) {
    await this.transactionTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setExtOriginAccountInput(extOriginAccount) {
    await this.extOriginAccountInput.sendKeys(extOriginAccount);
  }

  async getExtOriginAccountInput() {
    return await this.extOriginAccountInput.getAttribute('value');
  }

  async setExtOriginAccountTypeSelect(extOriginAccountType) {
    await this.extOriginAccountTypeSelect.sendKeys(extOriginAccountType);
  }

  async getExtOriginAccountTypeSelect() {
    return await this.extOriginAccountTypeSelect.element(by.css('option:checked')).getText();
  }

  async extOriginAccountTypeSelectLastOption(timeout?: number) {
    await this.extOriginAccountTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setExtOriginAccountBankInput(extOriginAccountBank) {
    await this.extOriginAccountBankInput.sendKeys(extOriginAccountBank);
  }

  async getExtOriginAccountBankInput() {
    return await this.extOriginAccountBankInput.getAttribute('value');
  }

  async setExtDestinationAccountInput(extDestinationAccount) {
    await this.extDestinationAccountInput.sendKeys(extDestinationAccount);
  }

  async getExtDestinationAccountInput() {
    return await this.extDestinationAccountInput.getAttribute('value');
  }

  async setExtDestinationAccountTypeSelect(extDestinationAccountType) {
    await this.extDestinationAccountTypeSelect.sendKeys(extDestinationAccountType);
  }

  async getExtDestinationAccountTypeSelect() {
    return await this.extDestinationAccountTypeSelect.element(by.css('option:checked')).getText();
  }

  async extDestinationAccountTypeSelectLastOption(timeout?: number) {
    await this.extDestinationAccountTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setExtDestinationAccountBankInput(extDestinationAccountBank) {
    await this.extDestinationAccountBankInput.sendKeys(extDestinationAccountBank);
  }

  async getExtDestinationAccountBankInput() {
    return await this.extDestinationAccountBankInput.getAttribute('value');
  }

  async originAccountSelectLastOption(timeout?: number) {
    await this.originAccountSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async originAccountSelectOption(option) {
    await this.originAccountSelect.sendKeys(option);
  }

  getOriginAccountSelect(): ElementFinder {
    return this.originAccountSelect;
  }

  async getOriginAccountSelectedOption() {
    return await this.originAccountSelect.element(by.css('option:checked')).getText();
  }

  async destinationAccountSelectLastOption(timeout?: number) {
    await this.destinationAccountSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async destinationAccountSelectOption(option) {
    await this.destinationAccountSelect.sendKeys(option);
  }

  getDestinationAccountSelect(): ElementFinder {
    return this.destinationAccountSelect;
  }

  async getDestinationAccountSelectedOption() {
    return await this.destinationAccountSelect.element(by.css('option:checked')).getText();
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

export class BankingTransactionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bankingTransaction-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bankingTransaction'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
