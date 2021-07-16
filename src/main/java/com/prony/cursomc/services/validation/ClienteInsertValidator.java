package com.prony.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.prony.cursomc.domain.Cliente;
import com.prony.cursomc.domain.enums.TipoCliente;
import com.prony.cursomc.dto.ClienteNewDTO;
import com.prony.cursomc.repositories.ClienteRepository;
import com.prony.cursomc.resources.exceptions.FieldMessage;
import com.prony.cursomc.services.validation.utils.DocumentUtil;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getId()) && !DocumentUtil.isValidSsn(objDto.getCpfCnpj()))
			list.add(new FieldMessage("cpfCnpj", "CPF Inválido"));
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getId()) && !DocumentUtil.isValidTfn(objDto.getCpfCnpj()))
			list.add(new FieldMessage("cpfCnpj", "CNPJ Inválido"));
		
		List<Cliente> emails = clienteRepo.findByEmail(objDto.getEmail());
		if(!emails.isEmpty())
			list.add(new FieldMessage("email", "E-mail já cadastrado"));
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
