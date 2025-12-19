package br.com.fiap.fasfoodpessoas.infraestructure.web.api.controllers;

import java.net.URI;
import java.util.Optional;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.in.PessoaServicePort;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.PessoaRequestDto;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.PessoaResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Service
@RestController
@RequestMapping("/api")
@Tag(name = "Pessoas", description = "end-point para gerenciar os clientes e funcionarios")
public class PessoaController {
    private final PessoaServicePort pessoaServicePort;

    public PessoaController(PessoaServicePort pessoaServicePort) {
        this.pessoaServicePort = pessoaServicePort;
    }


    @PostMapping("/v1/pessoa")
    @Operation(summary = "Cadastra pessoas", description = "Cadastra os clientes e funcionarios")
    public ResponseEntity<PessoaResponseDto> cadastrarPessoa(@Valid @RequestBody PessoaRequestDto pessoaRequestDto) {
        PessoaModel pessoaModel = pessoaServicePort.cadastraPessoa(toModel(pessoaRequestDto));
        PessoaResponseDto pessoaResponseDto = toResponseDto(pessoaModel);
        return ResponseEntity.created(URI.create("/api/v1/pessoa/" + pessoaResponseDto.cdDocPessoa()))
                .body(pessoaResponseDto);
    }

    @GetMapping("/v1/pessoa/{cdDocPessoa}")
    @Operation(summary = "Busca pessoa", description = "Busca o cliente ou funcionario por documento")
    public ResponseEntity<PessoaResponseDto> buscarPessoaPorCpf(@PathVariable String cdDocPessoa) {
        Optional<PessoaModel> pessoaModel = pessoaServicePort.buscaPessoaPorCpf(cdDocPessoa);
        if(pessoaModel.isPresent()) {
            PessoaResponseDto pessoaResponseDto = toResponseDto(pessoaModel.get());
            return  ResponseEntity.ok(pessoaResponseDto);
        }

        return ResponseEntity.noContent().build();
    }

    private PessoaModel toModel(PessoaRequestDto pessoaRequestDto) {
        return new PessoaModel(pessoaRequestDto.cdDocPessoa(),
                pessoaRequestDto.nmPessoa(),
                pessoaRequestDto.tpPessoa(),
                pessoaRequestDto.dsEmail());
    }

    private PessoaResponseDto toResponseDto(PessoaModel pessoaModel) {
        return new PessoaResponseDto(pessoaModel);
    }
}