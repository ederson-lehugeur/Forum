package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.dto.TopicoDetalhesDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.TopicoAtualizacaoForm;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDto> findAll(String nomeCurso) {
		List<Topico> topicos;

		if (nomeCurso != null) {
			topicos = topicoRepository.findByCursoNome(nomeCurso);
		} else {
			topicos = topicoRepository.findAll();
		}

		return TopicoDto.converter(topicos);
	}

	@GetMapping("/{id}")
	public TopicoDetalhesDto findById(@PathVariable Long id) {
		Topico topico = topicoRepository.getOne(id);

		return new TopicoDetalhesDto(topico);
	}

	@PostMapping
	public ResponseEntity<TopicoDto> save(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converter(cursoRepository);
		topicoRepository.save(topico);

		URI	uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid TopicoAtualizacaoForm topicoForm) {
		Topico topico = topicoForm.atualizar(id, topicoRepository);

		return ResponseEntity.ok(new TopicoDto(topico));
	}

}
