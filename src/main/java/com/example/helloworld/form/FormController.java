package com.example.helloworld.form;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/accounts/{accountId}/forms")
public class FormController {
	private final FormService formService;

	public FormController(FormService formService) {
		this.formService = formService;
	}

	// Get Forms
	@GetMapping
	List<FormDto> getForms(@PathVariable UUID accountId) {
		return formService.listForms(accountId)
			.stream()
			.map(this::toFormDto)
			.toList();
	}

	// Get Form by ID
	@GetMapping("/{formId}")
	FormDto getFormById(@PathVariable UUID accountId, @PathVariable UUID formId) {
		return toFormDto(formService.getForm(accountId, formId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	FormDto createForm(@PathVariable UUID accountId, @Valid @RequestBody CreateFormRequestDto request) {
		return toFormDto(formService.createForm(accountId, request));
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{formId}")
	FormDto updateForm(@PathVariable UUID accountId, @PathVariable UUID formId, @Valid @RequestBody UpdateFormRequestDto request) {
		return toFormDto(formService.updateForm(accountId, formId, request));
	}

	private FormDto toFormDto(Form form) {
		return new FormDto(
		  form.getId(),
			form.getFormType(),
			form.getFormTitle()
		);
	}
}
