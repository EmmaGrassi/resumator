package io.sytac.resumator.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.sytac.resumator.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
	
	@NotBlank(message = "companyName is mandatory")
	private final String companyName;
	@NotBlank(message = "Experience title is mandatory")
	private final String title;
	@NotBlank(message = "Experience city is mandatory")
	private final String city;
	@NotBlank(message = "Experience country  is mandatory")
	private final String country;
	@NotBlank(message = "shortDescription is mandatory")
	private final String shortDescription;
	@NotEmpty(message = "technologies is mandatory")
	private final List<String> technologies;
	@NotEmpty(message = "methodologies is mandatory")
	private final List<String> methodologies;
	@NotNull(message = "Experience startDate is mandatory")
	@Past(message="Experience start Date can not be in the future")
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
