package com.quasar.model.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author emmanuel
 *
 */
@Data
@ToString
public class CreateUserRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("username")
	@ApiModelProperty(example = "Juan", value = "Username of the new user",
	dataType = "String", allowableValues="range[37,37]", required = true)
	private String username;

	@JsonProperty("password")
	@ApiModelProperty(example = "pass12345", value = "Password of the new user",
	dataType = "String", allowableValues="range[37,37]", required = true)
	private String password;
	
}
