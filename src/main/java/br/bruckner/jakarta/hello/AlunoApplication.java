package br.bruckner.jakarta.hello;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class AlunoApplication extends Application {
  // Needed to enable Jakarta REST and specify path.    
}
