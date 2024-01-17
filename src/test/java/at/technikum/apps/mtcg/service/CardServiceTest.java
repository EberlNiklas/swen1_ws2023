package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    private String playerId;
    private List<Card> cards;

    @BeforeEach
    public void setUp() {
        playerId = "testPlayerId";
        cards = Arrays.asList(new Card(), new Card(), new Card());
    }

    @Test
    public void testFindAll() {
        when(cardRepository.findAllCardsFromUser(playerId)).thenReturn(cards);

        List<Card> result = cardService.findAllCardsFromUser(playerId);

        assertEquals(cards, result);
    }

    @Test
    public void testSaveCard() {
        Card card = new Card("1", "Goblin", 10, "Normal", "Monster");
        when(cardRepository.save(card)).thenReturn(card);

        Card result = cardService.save(card);

        assertEquals(card, result);
    }
}
