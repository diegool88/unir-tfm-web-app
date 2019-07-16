import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AmortizationTableComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-amortization-table div table .btn-danger'));
  title = element.all(by.css('jhi-amortization-table div h2#page-heading span')).first();

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

export class AmortizationTableUpdatePage {
  pageTitle = element(by.id('jhi-amortization-table-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  orderInput = element(by.id('field_order'));
  dueDateInput = element(by.id('field_dueDate'));
  amountInput = element(by.id('field_amount'));
  interestInput = element(by.id('field_interest'));
  loanProcessSelect = element(by.id('field_loanProcess'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setOrderInput(order) {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput() {
    return await this.orderInput.getAttribute('value');
  }

  async setDueDateInput(dueDate) {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput() {
    return await this.dueDateInput.getAttribute('value');
  }

  async setAmountInput(amount) {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput() {
    return await this.amountInput.getAttribute('value');
  }

  async setInterestInput(interest) {
    await this.interestInput.sendKeys(interest);
  }

  async getInterestInput() {
    return await this.interestInput.getAttribute('value');
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

export class AmortizationTableDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-amortizationTable-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-amortizationTable'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
