package com.commercetools.sunrise.shoppingcart.checkout;

import com.commercetools.sunrise.core.controllers.AbstractFormAction;
import com.commercetools.sunrise.models.carts.MyCartUpdater;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.updateactions.SetCountry;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Address;
import play.data.Form;
import play.data.FormFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

final class DefaultCheckoutAddressFormAction extends AbstractFormAction<CheckoutAddressFormData> implements CheckoutAddressFormAction {

    private final CheckoutAddressFormData formData;
    private final MyCartUpdater myCartUpdater;

    @Inject
    DefaultCheckoutAddressFormAction(final FormFactory formFactory, final CheckoutAddressFormData formData,
                                     final MyCartUpdater myCartUpdater) {
        super(formFactory);
        this.formData = formData;
        this.myCartUpdater = myCartUpdater;
    }

    @Override
    protected Class<? extends CheckoutAddressFormData> formClass() {
        return formData.getClass();
    }

    @Override
    public Form<? extends CheckoutAddressFormData> createForm() {
        if (isBillingAddressDifferent()) {
            return getFormFactory().form(formClass(), BillingAddressDifferentToShippingAddressGroup.class);
        }
        return super.createForm();
    }

    @Override
    protected CompletionStage<?> onValidForm(final CheckoutAddressFormData formData) {
        return myCartUpdater.force(buildUpdateActions(formData));
    }

    private List<UpdateAction<Cart>> buildUpdateActions(final CheckoutAddressFormData formData) {
        final List<UpdateAction<Cart>> updateActions = new ArrayList<>();
        updateActions.add(formData.setShippingAddress());
        updateActions.add(formData.setBillingAddress());
        final Optional<Address> shippingAddress = Optional.ofNullable(formData.setShippingAddress().getAddress());
        updateActions.add(SetCountry.of(shippingAddress.map(Address::getCountry).orElse(null)));
        shippingAddress
                .flatMap(address -> Optional.ofNullable(address.getEmail()))
                .ifPresent(email -> updateActions.add(SetCustomerEmail.of(email)));
        return updateActions;
    }

    private boolean isBillingAddressDifferent() {
        final String flagFieldName = "billingAddressDifferentToBillingAddress";
        final String fieldValue = getFormFactory().form().bindFromRequest().get(flagFieldName);
        return "true".equals(fieldValue);
    }
}
