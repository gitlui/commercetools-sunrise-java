package productcatalog.services;

import common.categories.JsonUtils;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryServiceImplTest {

    private static final CategoryTree categories = CategoryTree.of(JsonUtils.readJson("categoryQueryResult.json", CategoryQuery.resultTypeReference()).getResults());
    private static final CategoryService categoryService = new CategoryServiceImpl(categories);

    @Test
    public void getSiblingCategories() throws Exception {
        final Category shoulderBags = categories.findById("30d79426-a17a-4e63-867e-ec31a1a33416").get();
        final Category handBags = categories.findById("9a584ee8-a45a-44e8-b9ec-e11439084687").get();
        final Category clutches = categories.findById("a9c9ebd8-e6ff-41a6-be8e-baa07888c9bd").get();
        final Category satchels = categories.findById("30d79426-a17a-4e63-867e-ec31a1a33416").get();
        final Category shoppers = categories.findById("bd83e288-77de-4c3a-a26c-8384af715bbb").get();
        final Category wallets = categories.findById("d2f9a2da-db3e-4ee8-8192-134ebbc7fe4a").get();
        final Category backpacks = categories.findById("46249239-8f0f-48a9-b0a0-d29b37fc617f").get();
        final Category slingBags = categories.findById("8e052705-7810-4528-ba77-00094b87a69a").get();

        final List<Category> shoulderBagSiblings = categoryService.getSiblings(singletonList(shoulderBags.toReference()));
        final List<Category> handBagSiblings = categoryService.getSiblings(singletonList(handBags.toReference()));
        final List<Category> combinedSiblings = categoryService.getSiblings(asList(shoulderBags.toReference(), handBags.toReference()));

        assertThat(shoulderBagSiblings)
                .containsExactly(clutches, satchels, shoppers, handBags, wallets, backpacks, slingBags);
        assertThat(handBagSiblings)
                .containsExactly(clutches, satchels, shoppers, handBags, wallets, backpacks, slingBags);
        assertThat(combinedSiblings)
                .containsExactly(clutches, satchels, shoppers, handBags, wallets, backpacks, slingBags);
    }


}