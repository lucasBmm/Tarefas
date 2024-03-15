package br.com.tarefas.service;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.record.PessoaRecord;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Pessoa> getAllPessoas() {
        return repository.findAll();
    }

    @Transactional
    public Pessoa updatePessoa(Long id, PessoaRecord pessoaRecord) {
        Pessoa pessoa = repository.getById(id);

        pessoa.setNome(pessoaRecord.nome());

        Departamento departamento = departamentoService.findById(pessoa.getDepartamento().getId());

        pessoa.setDepartamento(departamento);

        departamentoRepository.save(pessoa.getDepartamento());

        return repository.save(pessoa);
    }

    @Transactional
    public Pessoa criaPessoa(PessoaRecord pessoaRecord) {
        Pessoa pessoa = new Pessoa(pessoaRecord);

        departamentoRepository.save(pessoa.getDepartamento());

        return repository.save(pessoa);
    }

    public void deletaPessoa(Long id) {
        Pessoa pessoa = repository.getReferenceById(id);

        repository.delete(pessoa);
    }
}
