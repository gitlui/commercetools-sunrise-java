package common.template.engine.handlebars;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.commercetools.sunrise.common.controllers.PageData;
import com.commercetools.sunrise.common.template.cms.CmsService;
import com.commercetools.sunrise.common.template.engine.TemplateNotFoundException;
import com.commercetools.sunrise.common.template.engine.TemplateEngine;
import common.template.engine.TestablePageData;
import com.commercetools.sunrise.common.template.engine.handlebars.HandlebarsTemplateEngine;
import com.commercetools.sunrise.common.template.i18n.I18nResolver;
import org.junit.Test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HandlebarsTemplateTest {

    private static final TemplateLoader DEFAULT_LOADER = new ClassPathTemplateLoader("/templates");
    private static final TemplateLoader OVERRIDE_LOADER = new ClassPathTemplateLoader("/templates/override");
    private static final I18nResolver I18N_MESSAGES = ((locale, i18nIdentifier, hashArgs) -> Optional.empty());
    private static final CmsService CMS_SERVICE = ((locales, cmsIdentifier) -> completedFuture(Optional.empty()));
    private static final List<Locale> LOCALES = emptyList();
    private static final PageData SOME_PAGE_DATA = new TestablePageData();

    @Test
    public void rendersTemplateWithPartial() throws Exception {
        testTemplate("template", defaultHandlebars(), html ->
                assertThat(html)
                        .contains("<title>foo</title>")
                        .contains("<h1>bar</h1>")
                        .contains("<h2></h2>")
                        .contains("<p>default partial</p>")
                        .contains("<ul></ul>")
        );
    }

    @Test
    public void rendersOverriddenTemplateUsingOverriddenAndDefaultPartials() throws Exception {
        testTemplate("template", handlebarsWithOverride(), html ->
                assertThat(html)
                        .contains("overridden template")
                        .contains("overridden partial")
                        .contains("another default partial")
        );
    }

    @Test
    public void rendersDefaultTemplateUsingOverriddenAndDefaultPartials() throws Exception {
        testTemplate("anotherTemplate", handlebarsWithOverride(), html ->
                assertThat(html)
                        .contains("default template")
                        .contains("overridden partial")
                        .contains("another default partial")
        );
    }

    @Test
    public void throwsExceptionWhenTemplateNotFound() throws Exception {
        assertThatThrownBy(() -> renderTemplate("unknown", defaultHandlebars()))
                .isInstanceOf(TemplateNotFoundException.class);
    }

    private static TemplateEngine defaultHandlebars() {
        return HandlebarsTemplateEngine.of(singletonList(DEFAULT_LOADER), I18N_MESSAGES, CMS_SERVICE);
    }

    private static TemplateEngine handlebarsWithOverride() {
        return HandlebarsTemplateEngine.of(asList(OVERRIDE_LOADER, DEFAULT_LOADER), I18N_MESSAGES, CMS_SERVICE);
    }

    private static void testTemplate(final String templateName, final TemplateEngine templateEngine, final Consumer<String> test) {
        final String html = renderTemplate(templateName, templateEngine);
        test.accept(html);
    }

    private static String renderTemplate(final String templateName, final TemplateEngine templateEngine) {
        return templateEngine.render(templateName, SOME_PAGE_DATA, LOCALES);
    }
}
