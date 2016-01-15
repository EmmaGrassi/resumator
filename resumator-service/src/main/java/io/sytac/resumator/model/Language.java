package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * The language of an employee
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class Language {

	private final String name;
	private final String proficiency;

	@JsonCreator
	public Language(@JsonProperty("name") String name, @JsonProperty("proficiency") String proficiency) {
		this.name = name;
		this.proficiency = proficiency;
	}

	public String getName() {
		return name;
	}

	public String getProficiency() {
		return proficiency;
	}
}
