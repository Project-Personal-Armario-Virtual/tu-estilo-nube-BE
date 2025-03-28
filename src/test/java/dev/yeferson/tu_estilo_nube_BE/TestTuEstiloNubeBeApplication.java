package dev.yeferson.tu_estilo_nube_BE;

import org.springframework.boot.SpringApplication;

public class TestTuEstiloNubeBeApplication {

	public static void main(String[] args) {
		SpringApplication.from(TuEstiloNubeBeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
