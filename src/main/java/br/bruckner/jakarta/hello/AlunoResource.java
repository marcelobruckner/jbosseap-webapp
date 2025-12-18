package br.bruckner.jakarta.hello;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("alunos")
public class AlunoResource {

	@Inject
	private AlunoService alunoService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Aluno> list() {
		return alunoService.listarTodos();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response novoAluno(@Valid Aluno aluno){
		Aluno novoAluno = alunoService.salvar(aluno);

		return Response.status(Response.Status.CREATED).entity(novoAluno).build();
	}

}