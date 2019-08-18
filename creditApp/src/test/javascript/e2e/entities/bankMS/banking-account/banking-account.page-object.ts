import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class BankingAccountComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-banking-account div table .btn-danger'));
  title = element.all(by.css('jhi-banking-account div h2#page-heading span')).first();

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

export class BankingAccountUpdatePage {
  pageTitle = element(by.id('jhi-banking-account-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numberInput = element(by.id('field_number'));
  accountTypeSelect = element(by.id('field_accountType'));
  currentBalanceInput = element(by.id('field_currentBalance'));
  availableBalanceInput = element(by.id('field_availableBalance'));
  customerIdentificationInput = element(by.id('field_customerIdentification'));
  customerIdentificationTypeInput = element(by.id('field_customerIdentificationType'));
  customerCountryInput = element(by.id('field_customerCountry'));
  bankingEntityMnemonicInput = element(by.id('field_bankingEntityMnemonic'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumberInput(number) {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput() {
    return await this.numberInput.getAttribute('value');
  }

  async setAccountTypeSelect(accountType) {
    await this.accountTypeSelect.sendKeys(accountType);
  }

  async getAccountTypeSelect() {
    return await this.accountTypeSelect.element(by.css('option:checked')).getText();
  }

  async accountTypeSelectLastOption(timeout?: number) {
    await this.accountTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setCurrentBalanceInput(currentBalance) {
    await this.currentBalanceInput.sendKeys(currentBalance);
  }

  async getCurrentBalanceInput() {
    return await this.currentBalanceInput.getAttribute('value');
  }

  async setAvailableBalanceInput(availableBalance) {
    await this.availableBalanceInput.sendKeys(availableBalance);
  }

  async getAvailableBalanceInput() {
    return await this.availableBalanceInput.getAttribute('value');
  }

  async setCustomerIdentificationInput(customerIdentification) {
    await this.customerIdentificationInput.sendKeys(customerIdentification);
  }

  async getCustomerIdentificationInput() {
    return await this.customerIdentificationInput.getAttribute('value');
  }

  async setCustomerIdentificationTypeInput(customerIdentificationType) {
    await this.customerIdentificationTypeInput.sendKeys(customerIdentificationType);
  }

  async getCustomerIdentificationTypeInput() {
    return await this.customerIdentificationTypeInput.getAttribute('value');
  }

  async setCustomerCountryInput(customerCountry) {
    await this.customerCountryInput.sendKeys(customerCountry);
  }

  async getCustomerCountryInput() {
    return await this.customerCountryInput.getAttribute('value');
  }

  async setBankingEntityMnemonicInput(bankingEntityMnemonic) {
    await this.bankingEntityMnemonicInput.sendKeys(bankingEntityMnemonic);
  }

  async getBankingEntityMnemonicInput() {
    return await this.bankingEntityMnemonicInput.getAttribute('value');
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

export class BankingAccountDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bankingAccount-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bankingAccount'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
