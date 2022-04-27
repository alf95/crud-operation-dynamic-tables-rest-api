package org.jooq.example.spring;

import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Optionally, add additional configuration beans
 *
 * @author Lukas Eder
 */
@Configuration
public class Config {

	/**
	 * Add custom jOOQ configuration.
	 * <p>
	 * The {@link DefaultConfigurationCustomizer} type has been added in Spring Boot
	 * 2.5 to facilitate customising the out of the box provided jOOQ
	 * {@link DefaultConfiguration}.
	 */
	@Bean
	public DefaultConfigurationCustomizer configurationCustomiser() {
		return c -> c.settings()
				.withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED);
	}
}
