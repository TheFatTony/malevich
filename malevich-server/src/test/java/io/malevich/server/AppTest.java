package io.malevich.server;

import com.yinyang.core.server.test.BaseAppTest;
import com.yinyang.core.server.test.TestApplicationContextInitializer;
import io.malevich.server.domain.PersonEntity;
import io.malevich.server.repositories.person.PersonDao;
import io.malevich.server.services.person.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MalevichServerApplication.class)
@ContextConfiguration(initializers = TestApplicationContextInitializer.class)
public class AppTest extends BaseAppTest {


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
