package br.com.tarefas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.entities.Tarefa;
import br.com.tarefas.record.*;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DepartamentoService departamentoService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    DepartamentoRecord departamentoRecord;
    TarefaRecord tarefaRecord;
    PessoaRecord pessoaRecord;

    @BeforeEach
    public void inicializaVariaveis() {
        departamentoRecord = new DepartamentoRecord(1L, "");
        tarefaRecord = new TarefaRecord(1L, "", "", LocalDate.now(), departamentoRecord, 1L, false);
        pessoaRecord = new PessoaRecord(1L, "", departamentoRecord , Arrays.asList(tarefaRecord));
    }
    @Test
    public void testGetAllPessoas() {
        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();
        List<Pessoa> pessoas = Arrays.asList(pessoa1, pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> result = pessoaService.getAllPessoas();

        assertEquals(2, result.size());
    }

    @Test
    public void testUpdatePessoa() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setDepartamento(new Departamento());

        when(pessoaRepository.getById(id)).thenReturn(pessoa);
        when(departamentoService.findById(any())).thenReturn(Optional.of(new Departamento()));
        when(departamentoRepository.save(any())).thenReturn(new Departamento());
        when(pessoaRepository.save(any())).thenReturn(pessoa);

        Pessoa updatedPessoa = pessoaService.updatePessoa(id, pessoaRecord);

        assertNotNull(updatedPessoa);
    }

    @Test
    public void testCriaPessoaDepartamentoExiste() {
        Departamento departamento = new Departamento();
        when(departamentoService.findById(1L)).thenReturn(Optional.of(departamento));

        Pessoa pessoa = new Pessoa();
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa createdPessoa = pessoaService.criaPessoa(pessoaRecord);

        assertEquals(pessoa, createdPessoa);
    }

    @Test
    public void testCriaPessoaDepartamentoNaoExiste() {
        when(departamentoService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pessoaService.criaPessoa(pessoaRecord));
    }

    @Test
    public void testDeletaPessoa() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();

        when(pessoaRepository.getReferenceById(id)).thenReturn(pessoa);

        pessoaService.deletaPessoa(id);

        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> foundPessoa = pessoaService.findById(id);

        assertTrue(foundPessoa.isPresent());
    }

    @Test
    public void testListarPessoasComTarefas() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("John");
        pessoa1.setDepartamento(new Departamento("Department 1"));
        pessoa1.setTarefas(Arrays.asList(new Tarefa(), new Tarefa()));

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Jane");
        pessoa2.setDepartamento(new Departamento("Department 2"));

        List<Pessoa> pessoas = Arrays.asList(pessoa1, pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<PessoaRecordData> pessoaRecordDataList = pessoaService.listarPessoas();

        assertEquals(2, pessoaRecordDataList.size());
        // Add assertions specific to the case where pessoa has tarefas
    }

    @Test
    public void testListarPessoasSemarefas() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("John");
        pessoa1.setDepartamento(new Departamento("Department 1"));

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Jane");
        pessoa2.setDepartamento(new Departamento("Department 2"));

        List<Pessoa> pessoas = Arrays.asList(pessoa1, pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<PessoaRecordData> pessoaRecordDataList = pessoaService.listarPessoas();

        assertEquals(2, pessoaRecordDataList.size());
    }
    @Test
    public void testBuscarPessoasPorNomeEPeriodo() {
        String nome = "John";
        LocalDate dataInicio = LocalDate.now().minusDays(10);
        LocalDate dataFim = LocalDate.now();

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setTarefas(new ArrayList<>());

        when(pessoaRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(Optional.of(pessoa));

        List<PessoaGastosRecord> pessoaGastosRecordList = pessoaService.buscarPessoasPorNomeEPeriodo(nome, dataInicio, dataFim);

        assertEquals(1, pessoaGastosRecordList.size());
    }
}