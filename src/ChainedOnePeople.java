import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;

public class ChainedOnePeople {

	public static String LILY = "lily";

    public static void createChainedOnePeople() {

		PersonAPI person = Global.getFactory().createPerson();
		person.setId(ChainedOnePeople.LILY);
		person.setImportance(PersonImportance.MEDIUM);
		person.setFaction(Factions.INDEPENDENT);
		person.setGender(Gender.FEMALE);
		person.setRankId(Ranks.CITIZEN);
		person.setPostId(Ranks.POST_CITIZEN);
		person.getName().setFirst("Lily");
		person.getName().setLast("Barath");
		person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "lily"));

		MarketAPI market = Global.getSector().getEconomy().getMarket("ancyra_market");
		market.getCommDirectory().addPerson(person, 0);
		market.addPerson(person);
		
		Global.getSector().getImportantPeople().addPerson(person);
		
	}

}


