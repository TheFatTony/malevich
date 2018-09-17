package io.malevich.server;

import io.malevich.server.utils.TestApplicationContextInitializer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MalevichServerApplication.class)
@ContextConfiguration(initializers = TestApplicationContextInitializer.class)
public class AppTest {

	@ClassRule
	public static MySQLContainer mysql = new MySQLContainer();

	@Test
	public void contextLoads() {
	}

}
