package dev.yeferson.tu_estilo_nube_BE;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TuEstiloNubeBeApplicationTests {

	@Test
	void contextLoads() {
	}

}
