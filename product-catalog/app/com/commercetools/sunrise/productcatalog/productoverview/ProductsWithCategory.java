package com.commercetools.sunrise.productcatalog.productoverview;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;

import javax.annotation.Nullable;

public final class ProductsWithCategory extends Base {

    private final PagedSearchResult<ProductProjection> products;
    @Nullable
    private final Category category;

    private ProductsWithCategory(final PagedSearchResult<ProductProjection> products, @Nullable final Category category) {
        this.products = products;
        this.category = category;
    }

    public PagedSearchResult<ProductProjection> getProducts() {
        return products;
    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    public static ProductsWithCategory of(final PagedSearchResult<ProductProjection> products, @Nullable final Category category) {
        return new ProductsWithCategory(products, category);
    }
}
