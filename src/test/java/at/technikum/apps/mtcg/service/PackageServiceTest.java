package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.repository.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    private String packageId;

    @BeforeEach
    public void setUp() {
        packageId = "testPackageId";
    }


    @Test
    public void testGetCards() {
        List<String> expectedCards = Arrays.asList("Card1", "Card2", "Card3");
        when(packageRepository.getCardsInPackage(packageId)).thenReturn(expectedCards);

        List<String> result = packageService.getCardsFromPackage(packageId);

        assertEquals(expectedCards, result);
    }

    @Test
    public void testDeletePackage() {
        // No return value to capture, we just want to verify the method was called
        packageService.delete(packageId);
        verify(packageRepository, times(1)).delete(packageId);
    }
}
