package campaign.people;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;

public class ChainedOnePeople {

	public static String STSEC_LILY = "stsec_lily";

    public static void createChainedOnePeople() {

		PersonAPI person = Global.getFactory().createPerson();
		person.setId(ChainedOnePeople.STSEC_LILY);
		person.setImportance(PersonImportance.MEDIUM);
		person.setFaction(Factions.INDEPENDENT);
		person.setGender(Gender.FEMALE);
		person.setRankId(Ranks.CITIZEN);
		person.setPostId(Ranks.POST_CITIZEN);
		person.getName().setFirst("<???>");
		person.getName().setLast("<???>");
		person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "lily"));
		
		Global.getSector().getImportantPeople().addPerson(person);
		
	}

}


