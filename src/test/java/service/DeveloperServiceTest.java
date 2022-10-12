package service;

import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.repository.DeveloperRepository;
import net.coldlight.dbcrudapp.service.DeveloperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DeveloperService developerService;

    @Test
    public void createDeveloperTest(){
        //given
        String firstName = "Igor";
        String lastName = "Popovich";
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);

        //when
        when(developerRepository.save(developer)).thenReturn(developer);

        //then
        assertEquals(developer, developerService.createDeveloper(firstName, lastName));

    }


}
