package com.commercetools.sunrise.shoppingcart.discountcodes;

import com.commercetools.sunrise.core.controllers.AbstractFormAction;
import com.commercetools.sunrise.models.carts.MyCartUpdater;
import com.google.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static com.commercetools.sdk.errors.ErrorResponseExceptionUtils.isDiscountCodeNonApplicableError;

final class DefaultAddDiscountCodeFormAction extends AbstractFormAction<AddDiscountCodeFormData> implements AddDiscountCodeFormAction {

    private final AddDiscountCodeFormData formData;
    private final MyCartUpdater myCartUpdater;

    @Inject
    DefaultAddDiscountCodeFormAction(final FormFactory formFactory, final AddDiscountCodeFormData formData,
                                     final MyCartUpdater myCartUpdater) {
        super(formFactory);
        this.formData = formData;
        this.myCartUpdater = myCartUpdater;
    }

    @Override
    protected Class<? extends AddDiscountCodeFormData> formClass() {
        return formData.getClass();
    }

    @Override
    protected CompletionStage<?> onValidForm(final AddDiscountCodeFormData formData) {
        return myCartUpdater.force(formData.addDiscountCode());
    }

    @Override
    protected CompletionStage<Result> onFailedRequest(final Form<? extends AddDiscountCodeFormData> form, final Throwable throwable,
                                                      final Function<Form<? extends AddDiscountCodeFormData>, CompletionStage<Result>> onBadRequest) {
        if (isDiscountCodeNonApplicableError(throwable.getCause())) {
            form.reject("errors.notApplicableDiscountCode");
            return onBadRequest.apply(form);
        }
        return super.onFailedRequest(form, throwable, onBadRequest);
    }
}
