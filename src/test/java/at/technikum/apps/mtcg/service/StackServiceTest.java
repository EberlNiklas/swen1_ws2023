package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Stack;
import at.technikum.apps.mtcg.repository.StackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StackServiceTest {

    @InjectMocks
    private StackService stackService;

    @Mock
    private StackRepository stackRepository;

    @Test
    void findAll_returnsListOfStacks() {
        // Arrange
        List<Stack> expectedStacks = Arrays.asList(new Stack("845f0dc7-37d0-426e-994e-43fc3ac83c08", "3d908a34-5f56-4607-89eb-4c8141811af5"), new Stack("1cb6ab86-bdb2-47e5-b6e4-68c5ab389334", "b87118ad-9d8f-43c9-9343-057ca48fcfc6")); // Hier sollten tatsächliche Stack-Objekte verwendet werden
        when(stackRepository.findAll()).thenReturn(expectedStacks);

        // Act
        List<Stack> actualStacks = stackService.findAll();

        // Assert
        assertEquals(expectedStacks, actualStacks);
        verify(stackRepository).findAll();
    }

    @Test
    void testSaveStack() {
        String user_id = "3d908a34-5f56-4607-89eb-4c8141811af5";
        String card_id = "845f0dc7-37d0-426e-994e-43fc3ac83c08";

        stackService.saveCardsIntoStack(user_id, card_id);

        verify(stackRepository).saveCardsIntoStack(user_id, card_id);
    }
}
