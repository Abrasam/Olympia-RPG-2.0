package com.olympiarpg.orpg.util;

import com.olympiarpg.orpg.ability.asterite.*;
import com.olympiarpg.orpg.ability.engineer.*;
import com.olympiarpg.orpg.ability.huntsman.*;
import com.olympiarpg.orpg.ability.mystic.*;
import com.olympiarpg.orpg.ability.occultist.*;
import com.olympiarpg.orpg.ability.shade.*;
import com.olympiarpg.orpg.ability.vanguard.*;
import com.olympiarpg.orpg.ability.warden.*;
import com.olympiarpg.orpg.ability.warlock.*;
import com.olympiarpg.orpg.main.Ability;

public enum Abilities {
    BattleStandard(new AbilityBattleStandard()),
    BloodThirst(new AbilityBloodThirst()),
    Charge(new AbilityCharge()),
    Eviscerate(new AbilityEviscerate()),
    Juggernaught(new AbilityJuggernaught()),
    Sunderstrike(new AbilitySunderstrike()),
    ChillBlast(new AbilityChillBlast()),
    Propulsion(new AbilityPropulsion()),
    Encase(new AbilityEncase()),
    Windrunner(new AbilityWindwalker()),
    Blizzard(new AbilityBlizzard()),
    BurningTrail(new AbilityBurningTrail()),
    Ignition(new AbilityIgnition()),
    Icewall(new AbilityFlurry()),
    Firebal(new AbilityFireball()),
    Pyroclasm(new AbilityPyroclasm()),
    DragonWings(new AbilityDragonWings()),
    Nethercore(new AbilityNethercore()),
    Asterblaster(new AbilityAsterblaster()),
    EmpyrealSky(new AbilityEmpyrealSky()),
    PurpleRain(new AbilityPurpleRain()),
    Singularity(new AbilitySingularity()),
    StellarBurst(new AbilityStellarBurst()),
    Wormhole(new AbilityWormhole()),
    FeyDrift(new AbilityFeyDrift()),
    RejuvinatingDownpour(new AbilityRejuvinatingDownpour()),
    SoulMist(new AbilitySoulMist()),
    Sporepatch(new AbilitySporepatch()),
    Entangle(new AbilityEntangle()),
    StrengthOfTheWild(new AbilityStrengthOfTheWild()),
    FragGrenade(new AbilityFragGrenade()),
    JetBoost(new AbilityJetBoost()),
    SpringRazor(new AbilitySpringRazor()),
    Shotguns(new AbilityShotguns()),
    Landmine(new AbilityLandmine()),
    FaradayCage(new AbilityFaradayCage()),
    BlackDart(new AbilityBlackDart()),
    Dash(new AbilityDash()),
    Shadowstalk(new AbilityShadowstalk()),
    AssassinsCredence(new AbilityAssassinsCredence()),
    Shadowsphere(new AbilityShadowsphere()),
    Bladewraith(new AbilityBladewraith()),
    CrippleShot(new AbilityCrippleShot()),
    GrapplingHook(new AbilityGrapplingHook()),
    IronHail(new AbilityIronHail()),
    BearTrap(new AbilityBearTrap()),
    EnderArrow(new AbilityEnderArrow()),
    ArrowStorm(new AbilityArrowStorm()),
    DeathsGrip(new AbilityDeathsGrip()),
    ToxicCloud(new AbilityToxicCloud()),
    Plague(new AbilityPlague()),
    RotFlesh(new AbilityRotFlesh()),
    CursedGround(new AbilityCursedGround()),
    Pestilence(new AbilityPestilence()),
    ForceField(new AbilityForcefield()),
    CorporealEnlightenment(new AbilityCorporealEnlightenment()),
    Smite(new AbilitySmite()),
    AstralShield(new AbilityAstralShield()),
    ArcaneAura(new AbilityArcaneAura()),
    ArcaneFire(new AbilityAstralFire()),
    GPS(new AbilityGPS()),
    MechSuit(new AbilityMech()),
    SelfDestruct(new AbilitySelfDestruct()),
    Electrocute(new AbilityElectrocute()),
    StunGrenade(new AbilityStunGrenade()),
    Scavenge(new AbilityScavenge()),
    Hunter(new AbilityHunter()),
    IceArrow(new AbilityFrozenArrow()),
    Bite(new AbilityBite()),
    NoDrawback(new AbilityNoDrawback()),
    ExplosiveArrow(new AbilityExplosiveArrow()),
    Remedy(new AbilityRemedy()),
    Lick(new AbilityLick()),
    Shrooms(new AbilityShrooms()),
    ActualCannibal(new AbilityActualCannibal()),
    Thornbush(new AbilityThornyBush()),
    Sickness(new AbilitySickness()),
    Slow(new AbilitySlow()),
    Curse(new AbilityCurse()),
    LifeSap(new AbilityLifeSap()),
    DarkForm(new AbilityDarkForm()),
    Bonespear(new AbilityBoneSpear()),
    Backflip(new AbilityBackflip()),
    Dodge(new AbilityDodge()),
    Blink(new AbilityBlink()),
    Bleed(new AbilityBleed()),
    DoubleJump(new AbilityDoubleJump()),
    Web(new AbilityWeb()),
    WarCry(new AbilityWarCry()),
    CombatMaster(new AbilityMeleeMaster()),
    Kick(new AbilityKick()),
    Slash(new AbilitySlash()),
    MightyBlow(new AbilityMightyBlow()),
    Enrage(new AbilityEnrage()),
    PainTrain(new AbilityPainTrain()),
    IceWallReal(new AbilityIceWall()),
    ForcePush(new AbilityForcePush()),
    RidingTheWinds(new AbilityRideTheWinds()),
    SnowCannon(new AbilitySnowCannon()),
    IceArmour(new AbilityFrozenArmor()),
    Icebolt(new AbilityIcebolt()),
    SootPuff(new AbilitySootPuff()),
    BlazingFeet(new AbilityBurningFeet()),
    DragonsBreath(new AbilityDragonsBreath()),
    PhoenixHeart(new AbilityPhoenixHeart()),
    Firestorm(new AbilityFirestorm()),
    Flamethrower(new AbilityFlamethrower()),
    Repair(new AbilityRepair());

    public final Ability ab;

    Abilities(Ability ab) {
        this.ab = ab;
    }

    public static Ability get(String name) {
        for (Abilities ab : Abilities.values()) {
            if (ab.ab.name.equals(name)) {
                return ab.ab;
            }
        }
        return null;
    }

}
