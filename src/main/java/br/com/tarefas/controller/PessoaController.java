package br.com.tarefas.controller;

import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.record.PessoaRecord;
import br.com.tarefas.record.TarefaRecord;
import br.com.tarefas.service.PessoaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity<List<PessoaRecord>> getAllPessoas() {
        List<Pessoa> pessoas = service.getAllPessoas();

        List<PessoaRecord> pessoasRecord = pessoas.stream().map(PessoaRecord::new).collect(Collectors.toList());

        return ResponseEntity.ok(pessoasRecord);
    }

    @PostMapping
    public ResponseEntity<PessoaRecord> adicionaPessoa(@Valid @RequestBody PessoaRecord pessoaRecord, UriComponentsBuilder uriBuilder) {
        Pessoa pessoa = service.criaPessoa(pessoaRecord);

        var uri = uriBuilder.path("/pessoas/{id}").buildAndExpand(pessoa.getId()).toUri();

        return ResponseEntity.created(uri).body(new PessoaRecord(pessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePessoa(@PathVariable Long id, @RequestBody PessoaRecord pessoa) {
        try {
            Pessoa updatedPessoa = service.updatePessoa(id, pessoa);

            return ResponseEntity.ok(new PessoaRecord(updatedPessoa));
        } catch (EntityNotFoundException e) {
            // Handle the case where the Pessoa with the given id is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa not found with id: " + id);
        } catch (Exception e) {
            // Handle other potential errors
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating Pessoa");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePessoa(@PathVariable Long id) {
        service.deletaPessoa(id);
        return ResponseEntity.noContent().build();
    }

}
