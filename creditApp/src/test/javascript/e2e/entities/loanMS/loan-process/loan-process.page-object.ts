import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class LoanProcessComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-loan-process div table .btn-danger'));
  title = element.all(by.css('jhi-loan-process div h2#page-heading span')).first();

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

export class LoanProcessUpdatePage {
  pageTitle = element(by.id('jhi-loan-process-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  requestedAmountInput = element(by.id('field_requestedAmount'));
  givenAmountInput = element(by.id('field_givenAmount'));
  loanPeriodInput = element(by.id('field_loanPeriod'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  justificationInput = element(by.id('field_justification'));
  debtorIdentificationInput = element(by.id('field_debtorIdentification'));
  debtorIdentificationTypeInput = element(by.id('field_debtorIdentificationType'));
  debtorCountryInput = element(by.id('field_debtorCountry'));
  bankingEntityMnemonicInput = element(by.id('field_bankingEntityMnemonic'));
  bankingProductMnemonicInput = element(by.id('field_bankingProductMnemonic'));
  rulesEngineResultInput = element(by.id('field_rulesEngineResult'));
  loanProcessStatusSelect = element(by.id('field_loanProcessStatus'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setRequestedAmountInput(requestedAmount) {
    await this.requestedAmountInput.sendKeys(requestedAmount);
  }

  async getRequestedAmountInput() {
    return await this.requestedAmountInput.getAttribute('value');
  }

  async setGivenAmountInput(givenAmount) {
    await this.givenAmountInput.sendKeys(givenAmount);
  }

  async getGivenAmountInput() {
    return await this.givenAmountInput.getAttribute('value');
  }

  async setLoanPeriodInput(loanPeriod) {
    await this.loanPeriodInput.sendKeys(loanPeriod);
  }

  async getLoanPeriodInput() {
    return await this.loanPeriodInput.getAttribute('value');
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

  async setJustificationInput(justification) {
    await this.justificationInput.sendKeys(justification);
  }

  async getJustificationInput() {
    return await this.justificationInput.getAttribute('value');
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

  async setBankingEntityMnemonicInput(bankingEntityMnemonic) {
    await this.bankingEntityMnemonicInput.sendKeys(bankingEntityMnemonic);
  }

  async getBankingEntityMnemonicInput() {
    return await this.bankingEntityMnemonicInput.getAttribute('value');
  }

  async setBankingProductMnemonicInput(bankingProductMnemonic) {
    await this.bankingProductMnemonicInput.sendKeys(bankingProductMnemonic);
  }

  async getBankingProductMnemonicInput() {
    return await this.bankingProductMnemonicInput.getAttribute('value');
  }

  getRulesEngineResultInput(timeout?: number) {
    return this.rulesEngineResultInput;
  }
  async setLoanProcessStatusSelect(loanProcessStatus) {
    await this.loanProcessStatusSelect.sendKeys(loanProcessStatus);
  }

  async getLoanProcessStatusSelect() {
    return await this.loanProcessStatusSelect.element(by.css('option:checked')).getText();
  }

  async loanProcessStatusSelectLastOption(timeout?: number) {
    await this.loanProcessStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

export class LoanProcessDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-loanProcess-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-loanProcess'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
