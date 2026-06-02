package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.AddendumType;

public record CreateAddendumRequestDto(
	String rNumber,
	Object deptInformation,
	String addendumText,
	AddendumType addendumType,
	String addendumTitle
) {
}
