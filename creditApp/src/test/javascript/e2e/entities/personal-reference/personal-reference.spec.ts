/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PersonalReferenceComponentsPage,
  PersonalReferenceDeleteDialog,
  PersonalReferenceUpdatePage
} from './personal-reference.page-object';

const expect = chai.expect;

describe('PersonalReference e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personalReferenceUpdatePage: PersonalReferenceUpdatePage;
  let personalReferenceComponentsPage: PersonalReferenceComponentsPage;
  /*let personalReferenceDeleteDialog: PersonalReferenceDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PersonalReferences', async () => {
    await navBarPage.goToEntity('personal-reference');
    personalReferenceComponentsPage = new PersonalReferenceComponentsPage();
    await browser.wait(ec.visibilityOf(personalReferenceComponentsPage.title), 5000);
    expect(await personalReferenceComponentsPage.getTitle()).to.eq('creditApp.personalReference.home.title');
  });

  it('should load create PersonalReference page', async () => {
    await personalReferenceComponentsPage.clickOnCreateButton();
    personalReferenceUpdatePage = new PersonalReferenceUpdatePage();
    expect(await personalReferenceUpdatePage.getPageTitle()).to.eq('creditApp.personalReference.home.createOrEditLabel');
    await personalReferenceUpdatePage.cancel();
  });

  /* it('should create and save PersonalReferences', async () => {
        const nbButtonsBeforeCreate = await personalReferenceComponentsPage.countDeleteButtons();

        await personalReferenceComponentsPage.clickOnCreateButton();
        await promise.all([
            personalReferenceUpdatePage.setNameInput('name'),
            personalReferenceUpdatePage.setLastnameInput('lastname'),
            personalReferenceUpdatePage.genreSelectLastOption(),
            personalReferenceUpdatePage.setEmailInput('email'),
            personalReferenceUpdatePage.setBirthDateInput('2000-12-31'),
            personalReferenceUpdatePage.relationSelectLastOption(),
            personalReferenceUpdatePage.customerSelectLastOption(),
        ]);
        expect(await personalReferenceUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await personalReferenceUpdatePage.getLastnameInput()).to.eq('lastname', 'Expected Lastname value to be equals to lastname');
        expect(await personalReferenceUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
        expect(await personalReferenceUpdatePage.getBirthDateInput()).to.eq('2000-12-31', 'Expected birthDate value to be equals to 2000-12-31');
        await personalReferenceUpdatePage.save();
        expect(await personalReferenceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await personalReferenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last PersonalReference', async () => {
        const nbButtonsBeforeDelete = await personalReferenceComponentsPage.countDeleteButtons();
        await personalReferenceComponentsPage.clickOnLastDeleteButton();

        personalReferenceDeleteDialog = new PersonalReferenceDeleteDialog();
        expect(await personalReferenceDeleteDialog.getDialogTitle())
            .to.eq('creditApp.personalReference.delete.question');
        await personalReferenceDeleteDialog.clickOnConfirmButton();

        expect(await personalReferenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
