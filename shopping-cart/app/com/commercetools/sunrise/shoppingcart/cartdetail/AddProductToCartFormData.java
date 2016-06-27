package com.commercetools.sunrise.shoppingcart.cartdetail;

import io.sphere.sdk.models.Base;
import play.data.validation.Constraints;

public class AddProductToCartFormData extends Base implements AddProductToCartFormDataLike {
    @Constraints.Required
    private String productId;
    @Constraints.Required
    private Integer variantId;
    @Constraints.Required
    private Long quantity;

    public AddProductToCartFormData() {
    }

    @Override
    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    @Override
    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(final Integer variantId) {
        this.variantId = variantId;
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }
}
