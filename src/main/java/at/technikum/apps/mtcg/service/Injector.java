package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.controller.*;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.*;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.util.ArrayList;
import java.util.List;

public class Injector {



        /*
        A central place to create all classes.
        Dependency Injection via constructor injection.
         */
        public List<AbstractController> createController() {
            List<AbstractController> controllerList = new ArrayList<>();

            SessionService sessionService = new SessionService();
            UserRepository userRepository = new DatabaseUserRepository();
            UserService userService = new UserService(userRepository);
            UserController userController = new UserController(userService, sessionService);
            TradeRepository tradeRepository = new DatabaseTradingRepository();
            TradingService tradingService = new TradingService(tradeRepository);
            TradingController tradingController = new TradingController(sessionService, tradingService, userService);
            CardRepository cardRepository = new DatabaseCardRepository();
            CardService cardService = new CardService(cardRepository);
            CardController cardController = new CardController(cardService, userService, sessionService);
            BattleRepository battleRepository = new DatabaseBattleRepository();
            BattleService battleService = new BattleService(battleRepository);
            BattleController battleController = new BattleController(battleService, userService, sessionService);
            DeckRepository deckRepository = new DatabaseDeckRepository();
            DeckService deckService = new DeckService(deckRepository);
            DeckController deckController = new DeckController(sessionService, cardService, deckService);
            PackageRepository packageRepository = new DatabasePackageRepository();
            PackageService packageService = new PackageService(packageRepository);
            PackageController packageController = new PackageController(packageService);
            ScoreboardController scoreboardController = new ScoreboardController(sessionService, userService);
            SessionController sessionController = new SessionController(userService, sessionService);
            StatsController statsController = new StatsController(sessionService, userService);
            StackRepository stackRepository = new DatabaseStackRepository();
            StackService stackService = new StackService(stackRepository);
            TransactionController transactionController = new TransactionController(sessionService, packageService, stackService);

            controllerList.add(userController);
            controllerList.add(tradingController);
            controllerList.add(cardController);
            controllerList.add(battleController);
            controllerList.add(deckController);
            controllerList.add(packageController);
            controllerList.add(scoreboardController);
            controllerList.add(sessionController);
            controllerList.add(statsController);
            controllerList.add(transactionController);


            return controllerList;
        }
}
