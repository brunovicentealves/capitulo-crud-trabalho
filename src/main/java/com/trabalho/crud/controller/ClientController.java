package com.trabalho.crud.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.trabalho.crud.dto.ClientDTO;
import com.trabalho.crud.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService clienteService;

	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
					@RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
					@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
					@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return ResponseEntity.ok(clienteService.findAllPaged(pageRequest));

	}

	@GetMapping("/{id}")

	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {

		return ResponseEntity.ok(clienteService.findById(id));
	}

	@PostMapping
	public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO dto) {
		dto = clienteService.saveClient(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO dto) {
		dto = clienteService.updateClient(id, dto);

		return ResponseEntity.ok(dto);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ClientDTO> deleteClient(@PathVariable Long id) {
		clienteService.deleteClient(id);
		return ResponseEntity.noContent().build();

	}

}
