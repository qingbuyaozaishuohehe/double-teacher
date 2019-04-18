package com.rzlearn.base.support;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * <p>ClassName:Resources</p>
 * <p>Description:加载配置</p>
 * @author JiPeigong	
 * @date 2018年4月11日
 * @since
 * @see
 */
public final class Resources {
	/** 国际化信息 */
	private static final Map<String, ResourceBundle> MESSAGES = new HashMap<String, ResourceBundle>();

	/** 国际化信息 */
	public static String getMessage(String key, String language, Object... params) {
		Locale locale = new Locale(language);
		ResourceBundle message = MESSAGES.get(locale.getLanguage());
		if (message == null) {
			synchronized (MESSAGES) {
				message = MESSAGES.get(locale.getLanguage());
				if (message == null) {
					message = ResourceBundle.getBundle("i18n/messages", locale);
					MESSAGES.put(locale.getLanguage(), message);
				}
			}
		}
		if (params != null && params.length > 0) {
			return String.format(message.getString(key), params);
		}
		return message.getString(key);
	}

	/** 清除国际化信息 */
	public static void flushMessage() {
		MESSAGES.clear();
	}
}
