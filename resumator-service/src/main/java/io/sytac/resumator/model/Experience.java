package io.sytac.resumator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The working experience of an employee
 *
 * @author Tonino Catapano
 * @author Carlo Sciolla
 * @since 0.1
 */
@Getter
@AllArgsConstructor
@ToString
public class Experience {
	
	private final String companyName;
	private final String title;
	private final String city;
	private final String country;
	private final String shortDescription;
	private final List<String> technologies;
	private final List<String> methodologies;
	private final Date startDate;
	private final Optional<Date> endDate;

	@JsonCreator
	public Experience(@JsonProperty("companyName") String companyName,
					  @JsonProperty("title") String title,
					  @JsonProperty("city") String city,
					  @JsonProperty("country") String country,
					  @JsonProperty("shortDescription") String shortDescription,
					  @JsonProperty("technologies") List<String> technologies,
					  @JsonProperty("methodologies") List<String> methodologies,
					  @JsonProperty("startDate") String startDate,
					  @JsonProperty("endDate") String endDate) {
		this(companyName, title, city, country, shortDescription, technologies, methodologies,
				DateUtils.convert(startDate), Optional.ofNullable(endDate).map(DateUtils::convert));
	}
}
