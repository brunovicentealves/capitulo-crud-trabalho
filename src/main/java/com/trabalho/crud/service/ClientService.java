package com.trabalho.crud.service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.trabalho.crud.dto.ClientDTO;
import com.trabalho.crud.entities.Client;
import com.trabalho.crud.exceptions.DataBaseException;
import com.trabalho.crud.exceptions.TrabalhoFinalCrudNotFoundException;
import com.trabalho.crud.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {

		Page<Client> client = clientRepository.findAll(pageRequest);

		return client.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {

		Optional<Client> client = clientRepository.findById(id);

		if (!client.isPresent()) {

			throw new TrabalhoFinalCrudNotFoundException("Não encontrou categoria");
		}

		return new ClientDTO(client.get());
	}

	@Transactional
	public ClientDTO saveClient(ClientDTO dto) {

		Client entity = new Client();
		copyDtoToEntity(dto, entity);

		entity = clientRepository.save(entity);

		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO updateClient(Long id, ClientDTO dto) {
		try {

			Client entity = clientRepository.getOne(id);

			copyDtoToEntity(dto, entity);

			entity = clientRepository.save(entity);

			return new ClientDTO(entity);

		} catch (EntityNotFoundException e) {

			throw new TrabalhoFinalCrudNotFoundException("Não conseguiu salvar a Categoria");

		}

	}

	public void deleteClient(Long id) {
		try {
			clientRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {

			throw new TrabalhoFinalCrudNotFoundException("Não conseguiu deletar ");

		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("violação de integridade");
		}

	}

	private void copyDtoToEntity(ClientDTO dto, Client entity) {

		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		entity.setIncome(dto.getIncome());

	}

}
