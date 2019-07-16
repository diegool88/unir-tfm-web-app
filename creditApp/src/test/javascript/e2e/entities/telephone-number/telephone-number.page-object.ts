import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class TelephoneNumberComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-telephone-number div table .btn-danger'));
  title = element.all(by.css('jhi-telephone-number div h2#page-heading span')).first();

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

export class TelephoneNumberUpdatePage {
  pageTitle = element(by.id('jhi-telephone-number-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  countryCodeInput = element(by.id('field_countryCode'));
  regionCodeInput = element(by.id('field_regionCode'));
  numberInput = element(by.id('field_number'));
  addressSelect = element(by.id('field_address'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCountryCodeInput(countryCode) {
    await this.countryCodeInput.sendKeys(countryCode);
  }

  async getCountryCodeInput() {
    return await this.countryCodeInput.getAttribute('value');
  }

  async setRegionCodeInput(regionCode) {
    await this.regionCodeInput.sendKeys(regionCode);
  }

  async getRegionCodeInput() {
    return await this.regionCodeInput.getAttribute('value');
  }

  async setNumberInput(number) {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput() {
    return await this.numberInput.getAttribute('value');
  }

  async addressSelectLastOption(timeout?: number) {
    await this.addressSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async addressSelectOption(option) {
    await this.addressSelect.sendKeys(option);
  }

  getAddressSelect(): ElementFinder {
    return this.addressSelect;
  }

  async getAddressSelectedOption() {
    return await this.addressSelect.element(by.css('option:checked')).getText();
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

export class TelephoneNumberDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-telephoneNumber-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-telephoneNumber'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
