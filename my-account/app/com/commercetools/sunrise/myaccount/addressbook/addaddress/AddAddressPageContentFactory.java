package com.commercetools.sunrise.myaccount.addressbook.addaddress;

import com.commercetools.sunrise.common.forms.UserFeedback;
import com.commercetools.sunrise.myaccount.addressbook.AddressBookAddressFormBean;
import com.commercetools.sunrise.myaccount.addressbook.AddressBookAddressFormBeanFactory;
import io.sphere.sdk.customers.Customer;
import play.data.Form;

import javax.inject.Inject;

public class AddAddressPageContentFactory {

    @Inject
    private UserFeedback userFeedback;
    @Inject
    private AddressBookAddressFormBeanFactory addressBookAddressFormBeanFactory;

    public AddAddressPageContent create(final Customer customer, final Form<?> form) {
        final AddAddressPageContent content = new AddAddressPageContent();
        fillNewAddressForm(content, form);
        return content;
    }

    protected void fillNewAddressForm(final AddAddressPageContent content, final Form<?> form) {
        final AddressBookAddressFormBean bean = addressBookAddressFormBeanFactory.create(form);
        userFeedback.getErrors().ifPresent(bean::setErrors);
        content.setNewAddressForm(bean);
    }


}