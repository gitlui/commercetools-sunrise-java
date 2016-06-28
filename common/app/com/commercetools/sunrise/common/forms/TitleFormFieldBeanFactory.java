package com.commercetools.sunrise.common.forms;

import com.commercetools.sunrise.common.contexts.UserContext;
import com.commercetools.sunrise.common.models.FormSelectableOptionBean;
import com.commercetools.sunrise.common.models.TitleFormFieldBean;
import com.commercetools.sunrise.common.template.i18n.I18nIdentifier;
import com.commercetools.sunrise.common.template.i18n.I18nResolver;
import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class TitleFormFieldBeanFactory {

    private static final String CONFIG_TITLE_OPTIONS = "form.titles";
    @Inject
    private Configuration configuration;
    @Inject
    private I18nResolver i18nResolver;
    @Inject
    private UserContext userContext;

    public TitleFormFieldBean create(final List<String> availableTitles, @Nullable final String selectedTitle) {
        final TitleFormFieldBean bean = new TitleFormFieldBean();
        fillList(bean, availableTitles, selectedTitle);
        return bean;
    }

    public TitleFormFieldBean createWithDefaultTitles(@Nullable final String selectedTitle) {
        final List<String> availableTitles = configuration.getStringList(CONFIG_TITLE_OPTIONS, emptyList());
        return create(availableTitles, selectedTitle);
    }

    protected void fillList(final TitleFormFieldBean bean, final List<String> availableTitles, final @Nullable String selectedTitle) {
        bean.setList(availableTitles.stream()
                .map(title -> titleToSelectableData(title, selectedTitle))
                .collect(toList()));
    }

    protected FormSelectableOptionBean titleToSelectableData(final String titleKey, @Nullable final String selectedTitle) {
        final FormSelectableOptionBean bean = new FormSelectableOptionBean();
        final String title = i18nResolver.getOrKey(userContext.locales(), I18nIdentifier.of(titleKey));
        bean.setLabel(title);
        bean.setValue(title);
        bean.setSelected(title.equals(selectedTitle));
        return bean;
    }
}
