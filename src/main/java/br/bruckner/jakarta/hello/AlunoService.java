package br.bruckner.jakarta.hello;

import java.util.List;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AlunoService {
    // @Inject
    // private AlunoRepository dao;
    static List<Aluno> alunos = List.of(
            new Aluno("Maria Silva", 1L),
            new Aluno("João Souza", 2L),
            new Aluno("Ana Oliveira", 3L)
    );

    public List<Aluno> listarTodos(){
        // return dao.listarTodos();
        return alunos;
    }

    public Aluno salvar(Aluno aluno) {
        // return dao.salvar(aluno);
        Aluno novo = new Aluno("Marcelo", 4L);
        alunos.add(novo);
        return novo;    }
}
