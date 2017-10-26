package com.olympiarpg.orpg.util;

import static com.olympiarpg.orpg.util.Abilities.*;

public enum PClass {
    VANGUARD(190,0.2f, new Abilities[] {Sunderstrike, Charge, WarCry, BattleStandard, CombatMaster, Kick, Juggernaught, Slash, BloodThirst, MightyBlow, Enrage, PainTrain}),
    OCCULTIST(180, 0.25f, new Abilities[] {Plague, DeathsGrip, Sickness, Slow, Curse, LifeSap, DarkForm, Bonespear, RotFlesh, CursedGround, ToxicCloud, Pestilence}),
    HUNTSMAN(130, 0.35f, new Abilities[] {IronHail, Scavenge, CrippleShot, GrapplingHook, Hunter, BearTrap, IceArrow, Bite, NoDrawback, ExplosiveArrow, EnderArrow, ArrowStorm}),
    ENGINEER(160, 0.3f, new Abilities[] {FragGrenade, JetBoost, GPS, Repair, MechSuit, Landmine, SelfDestruct, Electrocute, Shotguns, SpringRazor, StunGrenade, FaradayCage}),
    SHADE(120, 0.35f, new Abilities[] {BlackDart, Backflip, Dodge, Eviscerate, DoubleJump, Bleed, Shadowstalk, Web, AssassinsCredence, Dash, Shadowsphere, Bladewraith}),
    WARDEN(150, 0.3f, new Abilities[] {ChillBlast, IceWallReal, Encase, Propulsion, Windrunner, ForcePush, RidingTheWinds, SnowCannon, IceArmour, Icewall, Icebolt, Blizzard}),
    WARLOCK(140, 0.3f, new Abilities[] {Firebal, DragonWings, SootPuff, BlazingFeet, Nethercore, DragonsBreath, BurningTrail, Ignition, PhoenixHeart, Firestorm, Flamethrower, Pyroclasm}),
    ASTERITE(130, 0.3f, new Abilities[] {Asterblaster, Wormhole, StellarBurst, ForceField, CorporealEnlightenment, Smite, AstralShield, PurpleRain, ArcaneAura, EmpyrealSky, ArcaneFire, Singularity}),
    MYSTIC(120, 0.35f, new Abilities[] {Sporepatch, Remedy, Lick, RejuvinatingDownpour, SoulMist, Bite, Shrooms, Entangle, ActualCannibal, Thornbush, FeyDrift, StrengthOfTheWild}),
    NONE(20, 0.2f, new Abilities[]{});

    public final double health;
    public final float speed;
    public final Abilities[] abilities;

    private PClass(double health, float speed, Abilities[] abilities) {
        this.health = health;
        this.speed = speed;
        this.abilities = abilities;

    }
}
