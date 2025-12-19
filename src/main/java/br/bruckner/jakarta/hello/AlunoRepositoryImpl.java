package br.bruckner.jakarta.hello;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class AlunoRepositoryImpl implements AlunoRepository {

    static List<Aluno> alunos = List.of(
            new Aluno("Maria Silva", 1L),
            new Aluno("João Souza", 2L),
            new Aluno("Ana Oliveira", 3L)
    );

    @Override
    public List<Aluno> listarTodos() {
        return alunos;
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        Aluno novo = new Aluno("Marcelo", 4L);
        alunos.add(novo);
        return novo;
    }
}
