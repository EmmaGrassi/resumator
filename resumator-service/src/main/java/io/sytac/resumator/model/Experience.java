package io.sytac.resumator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

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
	private final Date endDate;
}
