package com.example.helloworld.service;

import com.example.helloworld.exception.FormNotFoundException;
import com.example.helloworld.model.dto.CreateAddendumRequestDto;
import com.example.helloworld.model.entity.Addendum;
import com.example.helloworld.model.entity.Form;
import com.example.helloworld.repository.AddendumRepository;
import com.example.helloworld.repository.FormRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddendumService {
	private final AddendumRepository addendumRepository;
	private final FormRepository formRepository;

	public AddendumService(FormRepository formRepository, AddendumRepository addendumRepository) {
		this.addendumRepository = addendumRepository;
		this.formRepository = formRepository;
	}

	@Transactional
	public Addendum createAddendum(UUID formId, CreateAddendumRequestDto request) {
		Form form = getForm(formId);
		Addendum addendum = new Addendum();
		addendum.setAddendumTitle(request.addendumTitle());
		addendum.setAddendumText(request.addendumText());
		addendum.setAddendumType(request.addendumType());

		form.addAddendum(addendum);

		return addendumRepository.save(addendum);
	}

	private Form getForm(UUID formId) {
		return formRepository.findById(formId)
			.orElseThrow(() -> new FormNotFoundException(formId));
	}

}
