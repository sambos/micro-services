package org.rsol.microapp.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@JsonInclude(value = Include.NON_NULL)
@ToString
@NoArgsConstructor
public class OrderCriteriaModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String Id;
	@Getter
	@Setter
	private String clientId;
	@Getter
	@Setter
	private String type;	//order type
	@Getter
	@Setter
	private String code;	// some code
	@Getter
	@Setter
	private Integer status; // status of order
}
