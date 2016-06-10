package shoppingcart;

import com.commercetools.sunrise.common.contexts.UserContext;
import com.commercetools.sunrise.common.models.ProductAttributeBean;
import com.commercetools.sunrise.common.models.ProductAttributeBeanFactoryInjectless;
import com.commercetools.sunrise.common.models.ProductDataConfig;
import io.sphere.sdk.carts.LineItem;
import wedecidelatercommon.ProductReverseRouter;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class LineItemBean extends MiniCartLineItemBean {

    private List<ProductAttributeBean> attributes;

    public LineItemBean() {
    }

    public LineItemBean(final LineItem lineItem, final ProductDataConfig productDataConfig,
                        final UserContext userContext, final ProductReverseRouter reverseRouter) {
        super(lineItem, userContext, reverseRouter);
        this.attributes = lineItem.getVariant().getAttributes().stream()
                .filter(attr -> productDataConfig.getSelectableAttributes().contains(attr.getName()))
                .map(attr -> ProductAttributeBeanFactoryInjectless.create(attr, userContext, productDataConfig))
                .collect(toList());
    }

    public List<ProductAttributeBean> getAttributes() {
        return attributes;
    }

    public void setAttributes(final List<ProductAttributeBean> attributes) {
        this.attributes = attributes;
    }
}
