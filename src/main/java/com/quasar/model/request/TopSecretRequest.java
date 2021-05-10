package com.quasar.model.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TopSecretRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Satellite> satellites;
	
}
