package br.bruckner.jakarta.hello;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class AlunoService {
    @Inject
    private AlunoRepository dao;
    

    public List<Aluno> listarTodos(){
        return dao.listarTodos();
    }

    public Aluno salvar(Aluno aluno) {
        return dao.salvar(aluno);
    }
}
