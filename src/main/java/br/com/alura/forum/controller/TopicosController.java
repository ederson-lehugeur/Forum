package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
public class TopicosController {

	@Autowired
	TopicoRepository topicoRepository;

	@GetMapping("/topicos")
	public List<TopicoDto> findAll() {
		List<Topico> topicos = topicoRepository.findAll();

		return TopicoDto.converter(topicos);
	}

}
