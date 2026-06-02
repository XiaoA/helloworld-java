package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.AddendumType;

import java.util.List;
import java.util.UUID;

public record AddendumDto(
	UUID id,
	String addendumTitle,
	String addendumText,
	AddendumType addendumType,
	String rNumber,
	List<String> deptInformation

) {
}
