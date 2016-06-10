package com.commercetools.sunrise.productcatalog.productdetail;

import com.commercetools.sunrise.common.contexts.UserContext;
import com.commercetools.sunrise.common.controllers.ReverseRouter;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;
import com.commercetools.sunrise.productcatalog.common.BreadcrumbBeanFactory;
import com.commercetools.sunrise.productcatalog.common.ProductBeanFactory;

import javax.inject.Inject;

public class ProductDetailPageContentFactory extends Base {

    @Inject
    protected UserContext userContext;
    @Inject
    protected ReverseRouter reverseRouter;
    @Inject
    protected BreadcrumbBeanFactory breadcrumbBeanFactory;
    @Inject
    protected ProductBeanFactory productBeanFactory;

    public ProductDetailPageContent create(final ProductProjection product, final ProductVariant variant) {
        final ProductDetailPageContent content = new ProductDetailPageContent();
        fillAdditionalTitle(product, content);
        fillProduct(product, variant, content);
        fillBreadCrumb(product, variant, content);
        fillAddToCartFormUrl(content);
        return content;
    }

    protected void fillAddToCartFormUrl(final ProductDetailPageContent content) {
        content.setAddToCartFormUrl(getAddToCartUrl()); // TODO move to page meta
    }

    protected void fillAdditionalTitle(final ProductProjection product, final ProductDetailPageContent content) {
        content.setAdditionalTitle(getAdditionalTitle(product));
    }

    protected void fillBreadCrumb(final ProductProjection product, final ProductVariant variant, final ProductDetailPageContent content) {
        content.setBreadcrumb(breadcrumbBeanFactory.create(product, variant));
    }

    protected void fillProduct(final ProductProjection product, final ProductVariant variant, final ProductDetailPageContent content) {
        content.setProduct(productBeanFactory.create(product, variant));
    }

    protected String getAdditionalTitle(final ProductProjection product) {
        return product.getName().find(userContext.locales()).orElse("");
    }

    protected String getAddToCartUrl() {
        return reverseRouter.processAddProductToCartForm(userContext.locale().getLanguage()).url();
    }
}
