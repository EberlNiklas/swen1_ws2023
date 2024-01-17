package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Deck;
import at.technikum.apps.mtcg.repository.DeckRepository;
import at.technikum.apps.mtcg.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DeckServiceTest {

    @InjectMocks
    private DeckService deckService;

    @Mock
    private DeckRepository deckRepository;

    private String playerId;
    private List<String> cards;


    @BeforeEach
    void setUp() {
        deckRepository = mock(DeckRepository.class);
        deckService = new DeckService(deckRepository);
    }

    @Test
    void save_deckAndUserId_deckIsSaved() {
        // Arrange
        Deck deck = new Deck("1", "Card2", "Deck1"); // Hier sollten tatsächliche Deck-Objekte verwendet werden
        String userId = "user123";

        // Act
        deckService.save(deck, userId);

        // Assert
        verify(deckRepository).save(deck, userId);
    }

    @Test
    public void testCheckIfCardsMatchUser() {
        when(deckRepository.checkIfCardsMatchUser(cards, playerId)).thenReturn(true);

        boolean result = deckService.checkIfCardsMatchUser(cards, playerId);

        assertTrue(result);
    }

    @Test
    public void testCardIsAvailableForTrade() {
        String cardId = "card1";

        when(deckRepository.cardIsAvailableForTrade(cardId)).thenReturn(true);

        boolean result = deckService.cardIsAvailableForTrade(cardId);

        assertTrue(result);
    }

}
