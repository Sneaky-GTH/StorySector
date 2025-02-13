package campaign.missions;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BattleAPI;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoAPI.CargoItemType;
import com.fs.starfarer.api.campaign.ai.ModularFleetAIAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.Abilities;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.ids.People;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.missions.academy.GATransverseJump.Stage;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetQuality;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetSize;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerNum;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerQuality;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SalvageSpecialAssigner;
import com.fs.starfarer.api.impl.campaign.rulecmd.AddRemoveCommodity;
import com.fs.starfarer.api.impl.campaign.rulecmd.AddShip;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin.DebrisFieldParams;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin.DebrisFieldSource;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Misc.Token;
import campaign.people.ChainedOnePeople;

public class ChainedOne extends HubMissionWithSearch {

    private static final Logger log = Global.getLogger(ChainedOne.class);

    static {
        log.setLevel(Level.ALL);
    }

    public static enum Stage {
        MEET_LILY,
        FIND_INFO,
        GO_TO_PLANET,
        GO_TO_PROFESSOR,
        RETURN_TO_LILY,
        OBTAIN_DISGUISE,
        OBTAIN_ACCESS,
        HEAD_TO_LILY,
        COMPLETED,
        }

    protected PersonAPI lily;
    protected PersonAPI receptionist;
    protected PersonAPI librarian;

    protected MarketAPI galatia;
    protected MarketAPI baires;
    protected MarketAPI volturn;

    protected int xpReward;

    @Override
    protected boolean create(MarketAPI createdAt, boolean barEvent) {

        log.debug("[STSEC] Test 1234");

        log.debug("[STSEC] Please...?!");


        // if already accepted by the player, abort
        if (!setGlobalReference("$stsec_chone_ref", "$stsec_chone_inProgress")) {
            return false;
        }

        log.debug("[STSEC] Let's do this thing!!!!!");

        // Check if the important things arent gone for some god-knows what reason
        lily = getImportantPerson(ChainedOnePeople.STSEC_LILY_ID);
        if (lily == null) return false;

        receptionist = getImportantPerson(ChainedOnePeople.STSEC_RECEPTIONIST_ID);
        if (receptionist == null) return false;

        volturn = getMarket("volturn");

        log.debug("[STSEC] We got to setting the Story Mission!");

        // start the mission. I think
        setStoryMission();
        setStartingStage(Stage.MEET_LILY);
        addSuccessStages(Stage.COMPLETED);

        log.debug("[STSEC] We set the Story Mission!");

        xpReward = 5000;

        //makeImportant(galatia, "$stsec_chone_meetLilyForFirstTime", Stage.MEET_LILY);

        log.debug("[STSEC] Made this stuff important.");

        connectWithGlobalFlag(Stage.MEET_LILY, Stage.FIND_INFO, "$stsec_chone_FindInfo");

        setStageOnGlobalFlag(Stage.COMPLETED, "$stsec_chone_completed");

        log.debug("[STSEC] Returning true....");

        return true;

    }

    protected void updateInteractionDataImpl() {
        set("$stsec_chone_stage", getCurrentStage());
    }

    @Override
    protected boolean callAction(String action, String ruleId, final InteractionDialogAPI dialog,
                                 List<Token> params, final Map<String, MemoryAPI> memoryMap) {

        if ("shootEm".equals(action))
        {
            Global.getSoundPlayer().playSound("storyevent_diktat_execution", 1, 1, Global.getSoundPlayer().getListenerPos(), new Vector2f());
            return true;
        }
        log.debug("[STSEC] callAction fired, resorting to default...");


        return super.callAction(action, ruleId, dialog, params, memoryMap);
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height) {
        log.debug("[STSEC] addDescription fired...");
        float opad = 10f;
        Color h = Misc.getHighlightColor();

        //info.addImage(robed_man.getPortraitSprite(), width, 128, opad);

        if (currentStage == Stage.MEET_LILY) {
            info.addPara("Find a device on the Galatia Academy network on which you can run the program given to you.", opad);
        }

    }

    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad) {
        log.debug("[STSEC] addNextStepText fired...");

        Color h = Misc.getHighlightColor();
        if (currentStage == Stage.MEET_LILY) {
            info.addPara("Run the program on a device in the Academy network.", tc, pad);
            return true;
        }

        return false;
    }


    @Override
    public String getBaseName() {
        return "The Chained One";
    }

    @Override
    public String getPostfixForState() {
        if (startingStage != null) {
            return "";
        }
        return super.getPostfixForState();
    }

}