package org.rsol.microapp.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Document(value = "order_collection")
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
@ToString
public class OrderModel implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
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

	@Getter
	@Setter
	private String createdBy;
	@Getter
	@Setter
	private String createdDate;
	@Getter
	@Setter
	private String updatedBy;
	@Getter
	@Setter
	private String updatedDate;

}
