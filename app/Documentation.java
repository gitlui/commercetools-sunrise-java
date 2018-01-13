import com.commercetools.sunrise.core.components.ControllerComponent;
import com.commercetools.sunrise.core.hooks.Hook;
import com.commercetools.sunrise.core.hooks.application.HttpRequestStartedHook;
import com.commercetools.sunrise.core.hooks.application.PageDataHook;
import com.commercetools.sunrise.core.hooks.ctprequests.ProductProjectionSearchHook;
import com.commercetools.sunrise.productcatalog.home.SunriseHomeController;
import com.commercetools.sunrise.productcatalog.products.SunriseProductDetailController;
import com.commercetools.sunrise.productcatalog.products.SunriseProductsController;

import java.util.Locale;

/**
 * <p>Sunrise Framework provides overridable controllers, a plugin architecture and default components to speed up the web shop development.</p>
 *
 * <h3>Overridable Controllers</h3>
 *
 * request scoped, implicitly for others
 *
 * <ul>
 *    <li>{@link SunriseHomeController}</li>
 *    <li>{@link SunriseProductDetailController}</li>
 *    <li>{@link SunriseProductsController}</li>
 * </ul>
 *
 * <h3>Controller Components</h3>
 * {@link ControllerComponent}s are plugins for controllers which communicate with the controller on the one hand
 * via dependency-injected fields like {@link Locale} and on the other hand via {@link Hook}s of the controller.
 * Controller components implement the hooks of the controllers where they need to do something. For example implementing {@link PageDataHook}
 * enables to change the content that will be rendered. The hook is called when all asynchronous requests are completed.
 * The hook {@link HttpRequestStartedHook} enables to do something when a request comes in like log stuff, call external systems.
 * The hook {@link ProductProjectionSearchHook} enables to modify a search request for example to add extension paths.
 *
 */
public class Documentation {
}
