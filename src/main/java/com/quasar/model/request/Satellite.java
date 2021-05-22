package com.quasar.model.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author emmanuel
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Satellite implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("name")
	@ApiModelProperty(example = "kenobi", value = "Name of the satellite to which it is directed.",
	dataType = "String", required = true)
	private String name;

	@JsonProperty("distance")
	@ApiModelProperty(example = "509.9019513592785", value = "Distance to the satellite",
	dataType = "Double", required = true)
	private double distance;

	@JsonProperty("message")
	@ApiModelProperty(example = "[\"este\",\"\",\"\",\"mensaje\",\"\"]", value = "Incomplete string array of the message",
	dataType = "List<String>", required = true)
	private List<String> message;
	
}
