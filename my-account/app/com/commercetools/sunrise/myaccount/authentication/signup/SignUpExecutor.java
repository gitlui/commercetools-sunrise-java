package com.commercetools.sunrise.myaccount.authentication.signup;

import com.google.inject.ImplementedBy;
import io.sphere.sdk.customers.CustomerSignInResult;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ImplementedBy(DefaultSignUpExecutor.class)
@FunctionalInterface
public interface SignUpExecutor extends Function<SignUpFormData, CompletionStage<CustomerSignInResult>> {

    @Override
    CompletionStage<CustomerSignInResult> apply(final SignUpFormData formData);
}