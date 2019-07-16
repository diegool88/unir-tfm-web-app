import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AddressComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-address div table .btn-danger'));
  title = element.all(by.css('jhi-address div h2#page-heading span')).first();

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

export class AddressUpdatePage {
  pageTitle = element(by.id('jhi-address-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  mainStreetInput = element(by.id('field_mainStreet'));
  secondaryStreetInput = element(by.id('field_secondaryStreet'));
  numberInput = element(by.id('field_number'));
  cityInput = element(by.id('field_city'));
  provinceInput = element(by.id('field_province'));
  countryInput = element(by.id('field_country'));
  postalCodeInput = element(by.id('field_postalCode'));
  addressTypeSelect = element(by.id('field_addressType'));
  customerSelect = element(by.id('field_customer'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMainStreetInput(mainStreet) {
    await this.mainStreetInput.sendKeys(mainStreet);
  }

  async getMainStreetInput() {
    return await this.mainStreetInput.getAttribute('value');
  }

  async setSecondaryStreetInput(secondaryStreet) {
    await this.secondaryStreetInput.sendKeys(secondaryStreet);
  }

  async getSecondaryStreetInput() {
    return await this.secondaryStreetInput.getAttribute('value');
  }

  async setNumberInput(number) {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput() {
    return await this.numberInput.getAttribute('value');
  }

  async setCityInput(city) {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput() {
    return await this.cityInput.getAttribute('value');
  }

  async setProvinceInput(province) {
    await this.provinceInput.sendKeys(province);
  }

  async getProvinceInput() {
    return await this.provinceInput.getAttribute('value');
  }

  async setCountryInput(country) {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput() {
    return await this.countryInput.getAttribute('value');
  }

  async setPostalCodeInput(postalCode) {
    await this.postalCodeInput.sendKeys(postalCode);
  }

  async getPostalCodeInput() {
    return await this.postalCodeInput.getAttribute('value');
  }

  async setAddressTypeSelect(addressType) {
    await this.addressTypeSelect.sendKeys(addressType);
  }

  async getAddressTypeSelect() {
    return await this.addressTypeSelect.element(by.css('option:checked')).getText();
  }

  async addressTypeSelectLastOption(timeout?: number) {
    await this.addressTypeSelect
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

export class AddressDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-address-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-address'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
