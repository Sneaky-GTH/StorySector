package campaign.people;

import campaign.missions.ChainedOne;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class ChainedOnePeople {


	public static PersonAPI stsec_lily;
	public static PersonAPI stsec_receptionist;
	public static PersonAPI stsec_librarian;

	public static String STSEC_LILY_ID = "stsec_lily";
	public static String STSEC_RECEPTIONIST_ID = "stsec_ga_receptionist";
	public static String STSEC_LIBRARIAN_ID = "stsec_ga_librarian";

    public static void createChainedOnePeople() {

		stsec_lily = Global.getFactory().createPerson();
		stsec_lily.setId(ChainedOnePeople.STSEC_LILY_ID);
		stsec_lily.setImportance(PersonImportance.MEDIUM);
		stsec_lily.setFaction(Factions.INDEPENDENT);
		stsec_lily.setGender(Gender.FEMALE);
		stsec_lily.setRankId(Ranks.CITIZEN);
		stsec_lily.setPostId(Ranks.POST_CITIZEN);
		stsec_lily.getName().setFirst("<???>");
		stsec_lily.getName().setLast("<???>");
		stsec_lily.setPortraitSprite(Global.getSettings().getSpriteName("characters", "lily"));
		Global.getSector().getImportantPeople().addPerson(stsec_lily);

		stsec_receptionist = Global.getFactory().createPerson();
		stsec_receptionist.setId(ChainedOnePeople.STSEC_RECEPTIONIST_ID);
		stsec_receptionist.setImportance(PersonImportance.MEDIUM);
		stsec_receptionist.setFaction(Factions.INDEPENDENT);
		stsec_receptionist.setGender(Gender.FEMALE);
		stsec_receptionist.setRankId(Ranks.CITIZEN);
		stsec_receptionist.setPostId(Ranks.POST_CITIZEN);
		stsec_receptionist.getName().setFirst("May");
		stsec_receptionist.getName().setLast("Droudierre");
		stsec_receptionist.setPortraitSprite(Global.getSettings().getSpriteName("characters", "stsec_ga_receptionist"));
		Global.getSector().getImportantPeople().addPerson(stsec_receptionist);

		stsec_librarian = Global.getFactory().createPerson();
		stsec_librarian.setId(ChainedOnePeople.STSEC_LIBRARIAN_ID);
		stsec_librarian.setImportance(PersonImportance.MEDIUM);
		stsec_librarian.setFaction(Factions.INDEPENDENT);
		stsec_librarian.setGender(Gender.MALE);
		stsec_librarian.setRankId(Ranks.CITIZEN);
		stsec_librarian.setPostId(Ranks.POST_CITIZEN);
		stsec_librarian.getName().setFirst("Rene");
		stsec_librarian.getName().setLast("Falscher");
		stsec_librarian.setPortraitSprite(Global.getSettings().getSpriteName("characters", "stsec_ga_receptionist"));
		Global.getSector().getImportantPeople().addPerson(stsec_librarian);

		
	}

}


