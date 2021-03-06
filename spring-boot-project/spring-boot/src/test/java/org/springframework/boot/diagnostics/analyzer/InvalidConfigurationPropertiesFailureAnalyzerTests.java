/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.diagnostics.analyzer;

import org.junit.Test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.InvalidConfigurationPropertiesException;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InvalidConfigurationPropertiesFailureAnalyzer}
 *
 * @author Madhura Bhave
 */
public class InvalidConfigurationPropertiesFailureAnalyzerTests {

	private final InvalidConfigurationPropertiesFailureAnalyzer analyzer = new InvalidConfigurationPropertiesFailureAnalyzer();

	@Test
	public void analysisForInvalidConfigurationOfConfigurationProperties() {
		FailureAnalysis analysis = performAnalysis();
		assertThat(analysis.getDescription()).isEqualTo(getDescription());
		assertThat(analysis.getAction())
				.isEqualTo("Remove either @ConfigurationProperties or @Component from "
						+ TestProperties.class);
	}

	private String getDescription() {
		return TestProperties.class.getName()
				+ " is annotated with @ConfigurationProperties and @Component"
				+ ". This may cause the @ConfigurationProperties bean to be registered twice.";
	}

	private FailureAnalysis performAnalysis() {
		FailureAnalysis analysis = this.analyzer
				.analyze(new InvalidConfigurationPropertiesException(TestProperties.class,
						Component.class));
		assertThat(analysis).isNotNull();
		return analysis;
	}

	@ConfigurationProperties
	@Component
	private static class TestProperties {

	}

}
