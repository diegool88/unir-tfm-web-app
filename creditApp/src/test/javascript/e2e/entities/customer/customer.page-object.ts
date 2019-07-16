import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CustomerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-customer div table .btn-danger'));
  title = element.all(by.css('jhi-customer div h2#page-heading span')).first();

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

export class CustomerUpdatePage {
  pageTitle = element(by.id('jhi-customer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  firstnameInput = element(by.id('field_firstname'));
  secondNameInput = element(by.id('field_secondName'));
  lastnameInput = element(by.id('field_lastname'));
  secondLastnameInput = element(by.id('field_secondLastname'));
  identificationTypeSelect = element(by.id('field_identificationType'));
  identificationNumberInput = element(by.id('field_identificationNumber'));
  genreSelect = element(by.id('field_genre'));
  emailInput = element(by.id('field_email'));
  birthDateInput = element(by.id('field_birthDate'));
  countryInput = element(by.id('field_country'));
  clientSinceInput = element(by.id('field_clientSince'));
  userSelect = element(by.id('field_user'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFirstnameInput(firstname) {
    await this.firstnameInput.sendKeys(firstname);
  }

  async getFirstnameInput() {
    return await this.firstnameInput.getAttribute('value');
  }

  async setSecondNameInput(secondName) {
    await this.secondNameInput.sendKeys(secondName);
  }

  async getSecondNameInput() {
    return await this.secondNameInput.getAttribute('value');
  }

  async setLastnameInput(lastname) {
    await this.lastnameInput.sendKeys(lastname);
  }

  async getLastnameInput() {
    return await this.lastnameInput.getAttribute('value');
  }

  async setSecondLastnameInput(secondLastname) {
    await this.secondLastnameInput.sendKeys(secondLastname);
  }

  async getSecondLastnameInput() {
    return await this.secondLastnameInput.getAttribute('value');
  }

  async setIdentificationTypeSelect(identificationType) {
    await this.identificationTypeSelect.sendKeys(identificationType);
  }

  async getIdentificationTypeSelect() {
    return await this.identificationTypeSelect.element(by.css('option:checked')).getText();
  }

  async identificationTypeSelectLastOption(timeout?: number) {
    await this.identificationTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setIdentificationNumberInput(identificationNumber) {
    await this.identificationNumberInput.sendKeys(identificationNumber);
  }

  async getIdentificationNumberInput() {
    return await this.identificationNumberInput.getAttribute('value');
  }

  async setGenreSelect(genre) {
    await this.genreSelect.sendKeys(genre);
  }

  async getGenreSelect() {
    return await this.genreSelect.element(by.css('option:checked')).getText();
  }

  async genreSelectLastOption(timeout?: number) {
    await this.genreSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setBirthDateInput(birthDate) {
    await this.birthDateInput.sendKeys(birthDate);
  }

  async getBirthDateInput() {
    return await this.birthDateInput.getAttribute('value');
  }

  async setCountryInput(country) {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput() {
    return await this.countryInput.getAttribute('value');
  }

  async setClientSinceInput(clientSince) {
    await this.clientSinceInput.sendKeys(clientSince);
  }

  async getClientSinceInput() {
    return await this.clientSinceInput.getAttribute('value');
  }

  async userSelectLastOption(timeout?: number) {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

export class CustomerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-customer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-customer'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
