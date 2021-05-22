package com.quasar.enums;

import lombok.Getter;

@Getter
public enum RolesEnum {
	PREFIX("ROLE_"),
	ADMIN("ADMIN"),
	USER("USER");
	
	private String value;
	
	private RolesEnum(String value) {
		this.value = value;
	}
}
