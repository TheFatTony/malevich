package io.malevich.server;

import io.malevich.server.domain.PersonEntity;
import io.malevich.server.repositories.person.PersonDao;
import io.malevich.server.services.person.PersonService;
import io.malevich.server.transfer.PersonDto;
import io.malevich.server.utils.TestApplicationContextInitializer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MalevichServerApplication.class)
@ContextConfiguration(initializers = TestApplicationContextInitializer.class)
public class AppTest {

    @ClassRule
    public static MySQLContainer mysql = new MySQLContainer("mysql:5.7.22");

//    @ClassRule
//    public static GenericContainer redis = new GenericContainer("hyperledger/composer-playground").withExposedPorts(8080);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PersonService personService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void testPersonDao() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName("First Name");
        personEntity.setLastName("Last Name");

        personEntity = personDao.save(personEntity);

        PersonEntity dbPersonEntity = personDao.findById(personEntity.getId()).get();
        assertEquals(personEntity.getFirstName(), dbPersonEntity.getFirstName());
        assertEquals(personEntity.getLastName(), dbPersonEntity.getLastName());
    }

    @Test
    public void testPersonService() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName("First Name");
        personEntity.setLastName("Last Name");

        personEntity = personService.save(personEntity);

        PersonEntity dbPersonEntity = personService.find(personEntity.getId());
        assertEquals(personEntity.getFirstName(), dbPersonEntity.getFirstName());
        assertEquals(personEntity.getLastName(), dbPersonEntity.getLastName());
    }



}
