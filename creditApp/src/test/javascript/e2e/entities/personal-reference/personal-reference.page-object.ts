import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class PersonalReferenceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-personal-reference div table .btn-danger'));
  title = element.all(by.css('jhi-personal-reference div h2#page-heading span')).first();

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

export class PersonalReferenceUpdatePage {
  pageTitle = element(by.id('jhi-personal-reference-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  lastnameInput = element(by.id('field_lastname'));
  genreSelect = element(by.id('field_genre'));
  emailInput = element(by.id('field_email'));
  birthDateInput = element(by.id('field_birthDate'));
  relationSelect = element(by.id('field_relation'));
  customerSelect = element(by.id('field_customer'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setLastnameInput(lastname) {
    await this.lastnameInput.sendKeys(lastname);
  }

  async getLastnameInput() {
    return await this.lastnameInput.getAttribute('value');
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

  async setRelationSelect(relation) {
    await this.relationSelect.sendKeys(relation);
  }

  async getRelationSelect() {
    return await this.relationSelect.element(by.css('option:checked')).getText();
  }

  async relationSelectLastOption(timeout?: number) {
    await this.relationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async customerSelectLastOption(timeout?: number) {
    await this.customerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async customerSelectOption(option) {
    await this.customerSelect.sendKeys(option);
  }

  getCustomerSelect(): ElementFinder {
    return this.customerSelect;
  }

  async getCustomerSelectedOption() {
    return await this.customerSelect.element(by.css('option:checked')).getText();
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

export class PersonalReferenceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-personalReference-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-personalReference'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
