package br.com.tarefas.service;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.entities.Tarefa;
import br.com.tarefas.record.PessoaGastosRecord;
import br.com.tarefas.record.PessoaRecord;
import br.com.tarefas.record.PessoaRecordData;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.PessoaRepository;
import br.com.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Pessoa> getAllPessoas() {
        return repository.findAll();
    }

    @Transactional
    public Pessoa updatePessoa(Long id, PessoaRecord pessoaRecord) {
        Optional<Pessoa> findPessoa = repository.findById(id);

        if (findPessoa.isPresent()) {
            Pessoa pessoa = findPessoa.get();

            pessoa.setNome(pessoaRecord.nome());

            Optional<Departamento> departamento = departamentoService.findById(pessoaRecord.departamento().id());

            if (departamento.isPresent()) {
                pessoa.setDepartamento(departamento.get());
                departamentoRepository.save(pessoa.getDepartamento());
                return repository.save(pessoa);
            }
            throw new RuntimeException("O departamento não existe");
        }
        throw new RuntimeException("A pessoa não foi encontrada");
    }

    @Transactional
    public Pessoa criaPessoa(PessoaRecord pessoaRecord) {
        Pessoa pessoa = new Pessoa(pessoaRecord);

        Optional<Departamento> departamento = departamentoService.findById(pessoaRecord.departamento().id());

        if (departamento.isPresent()) {
            pessoa.setDepartamento(departamento.get());

            return repository.save(pessoa);
        }

        throw new RuntimeException("O departamento não existe");
    }

    @Transactional
    public void deletaPessoa(Long id) {
        Optional<Pessoa> optionalPessoa = repository.findById(id);

        if (optionalPessoa.isPresent()) {
            Pessoa pessoa = optionalPessoa.get();

            if (!pessoa.getTarefas().isEmpty()) {
                for (Tarefa tarefa : pessoa.getTarefas()) {
                    tarefa.setPessoa(null);
                    tarefaRepository.save(tarefa);
                }
            }

            repository.delete(pessoa);
        } else {
            throw new RuntimeException("Não foi encontrada pessoa com id " + id);
        }
    }

    public Optional<Pessoa> findById(Long id) {
        return repository.findById(id);
    }

    public List<PessoaRecordData> listarPessoas() {
        return repository.findAll().stream()
                .map(pessoa -> {
                    List<Tarefa> tarefas = pessoa.getTarefas();
                    double totalHorasGastas = tarefas != null ? tarefas.stream()
                            .filter(Objects::nonNull)
                            .mapToDouble(tarefa -> tarefa.getDuracao() != null ? tarefa.getDuracao() : 0.0) // Handle null duracao
                            .sum() : 0.0;
                    return new PessoaRecordData(pessoa.getNome(), pessoa.getDepartamento().getTitulo(), totalHorasGastas);
                })
                .collect(Collectors.toList());
    }

    public List<PessoaGastosRecord> buscarPessoasPorNomeEPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByNomeContainingIgnoreCase(nome).stream()
                .map(pessoa -> {
                    double mediaHorasGastas = pessoa.getTarefas().stream()
                            .filter(tarefa -> tarefa.getPrazo().isAfter(dataInicio) && tarefa.getPrazo().isBefore(dataFim))
                            .mapToDouble(Tarefa::getDuracao)
                            .average()
                            .orElse(0.0);
                    return new PessoaGastosRecord(nome, dataInicio, dataFim, mediaHorasGastas);
                })
                .collect(Collectors.toList());
    }
}
