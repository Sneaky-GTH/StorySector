import com.fs.starfarer.api.BaseModPlugin;
import campaign.people.ChainedOnePeople;

public class StSecModPlugin extends BaseModPlugin {
    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();
    }

    @Override
    public void onNewGame() {
        super.onNewGameAfterTimePass();
        ChainedOnePeople.createChainedOnePeople();
    }

    // You can add more methods from ModPlugin here.
}