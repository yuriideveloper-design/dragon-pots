package com.p95ea315e.complete_first_called_neon.core.data

import com.p95ea315e.complete_first_called_neon.core.model.Achievement
import com.p95ea315e.complete_first_called_neon.core.model.Car
import com.p95ea315e.complete_first_called_neon.core.model.CarCategory
import com.p95ea315e.complete_first_called_neon.core.model.CrewMember
import com.p95ea315e.complete_first_called_neon.core.model.CrewRole
import com.p95ea315e.complete_first_called_neon.core.model.Part
import com.p95ea315e.complete_first_called_neon.core.model.PartType
import com.p95ea315e.complete_first_called_neon.core.model.RaceEvent
import com.p95ea315e.complete_first_called_neon.core.model.RaceType
import com.p95ea315e.complete_first_called_neon.core.model.GlowRank

object MockData {

    val manufacturers = listOf(
        "Nissan", "Toyota", "Mazda", "Honda", "Mitsubishi",
        "Ford", "Dodge", "Chevrolet", "Lamborghini", "Ferrari",
        "McLaren", "Tesla"
    )

    val cars: List<Car> = listOf(
        // NISSAN (7)
        Car(1, "GT-R R34 V-Spec", "Nissan", CarCategory.JDM, GlowRank.LEGENDARY, 600, 320, 92, 88, 95, 90, 45, 185_000, 0xFF00FFFF),
        Car(2, "GT-R R35 Nismo", "Nissan", CarCategory.JDM, GlowRank.LEGENDARY, 600, 315, 91, 87, 94, 91, 43, 170_000, 0xFF00FFFF),
        Car(3, "370Z Nismo", "Nissan", CarCategory.JDM, GlowRank.RARE, 350, 270, 78, 80, 75, 82, 28, 45_000, 0xFF0080FF),
        Car(4, "Silvia S15 Spec-R", "Nissan", CarCategory.DRIFT, GlowRank.RARE, 250, 250, 82, 72, 85, 78, 25, 38_000, 0xFF8B00FF),
        Car(5, "180SX Type X", "Nissan", CarCategory.DRIFT, GlowRank.UNCOMMON, 205, 230, 76, 68, 80, 74, 18, 22_000, 0xFF8B00FF),
        Car(6, "Z Proto Concept", "Nissan", CarCategory.PROTOTYPE, GlowRank.EPIC, 400, 290, 86, 84, 88, 86, 35, 95_000, 0xFF00FFFF),
        Car(7, "Skyline 2000GT-R", "Nissan", CarCategory.CLASSIC, GlowRank.RARE, 160, 210, 70, 65, 60, 72, 22, 32_000, 0xFF4CAF50),

        // TOYOTA (6)
        Car(8, "Supra MK4 RZ", "Toyota", CarCategory.JDM, GlowRank.LEGENDARY, 560, 312, 90, 86, 92, 88, 44, 165_000, 0xFFFF6600),
        Car(9, "GR Supra A90", "Toyota", CarCategory.JDM, GlowRank.EPIC, 382, 285, 83, 82, 82, 84, 33, 88_000, 0xFFFF6600),
        Car(10, "AE86 Sprinter Trueno", "Toyota", CarCategory.DRIFT, GlowRank.RARE, 128, 185, 68, 70, 75, 80, 20, 28_000, 0xFF4CAF50),
        Car(11, "GR86 Premium", "Toyota", CarCategory.DRIFT, GlowRank.RARE, 235, 235, 80, 78, 78, 82, 26, 40_000, 0xFF2196F3),
        Car(12, "2000GT", "Toyota", CarCategory.CLASSIC, GlowRank.LEGENDARY, 150, 220, 72, 68, 58, 75, 35, 95_000, 0xFFFFD700),
        Car(13, "Celica GT-Four ST205", "Toyota", CarCategory.STREET, GlowRank.UNCOMMON, 255, 245, 78, 76, 72, 80, 20, 25_000, 0xFF4CAF50),

        // MAZDA (5)
        Car(14, "RX-7 FD Spirit R", "Mazda", CarCategory.JDM, GlowRank.LEGENDARY, 280, 272, 88, 85, 90, 89, 42, 155_000, 0xFFFF006E),
        Car(15, "RX-7 FC Turbo II", "Mazda", CarCategory.CLASSIC, GlowRank.UNCOMMON, 185, 225, 74, 72, 70, 76, 18, 24_000, 0xFF4CAF50),
        Car(16, "RX-8 R3", "Mazda", CarCategory.DRIFT, GlowRank.UNCOMMON, 232, 235, 76, 74, 78, 80, 19, 26_000, 0xFFFF006E),
        Car(17, "MX-5 Miata RF GT", "Mazda", CarCategory.STREET, GlowRank.COMMON, 181, 215, 74, 76, 68, 80, 12, 12_000, 0xFF4CAF50),
        Car(18, "3 MPS Mazdaspeed", "Mazda", CarCategory.STREET, GlowRank.COMMON, 256, 250, 76, 74, 70, 78, 14, 15_000, 0xFF9E9E9E),

        // HONDA (5)
        Car(19, "NSX Type S", "Honda", CarCategory.JDM, GlowRank.EPIC, 600, 308, 87, 88, 85, 90, 38, 112_000, 0xFFFF006E),
        Car(20, "Civic Type R FK8", "Honda", CarCategory.STREET, GlowRank.RARE, 306, 272, 80, 82, 76, 84, 26, 42_000, 0xFF2196F3),
        Car(21, "S2000 AP2", "Honda", CarCategory.DRIFT, GlowRank.RARE, 240, 250, 82, 80, 80, 85, 24, 36_000, 0xFFFF006E),
        Car(22, "Integra Type R DC2", "Honda", CarCategory.JDM, GlowRank.UNCOMMON, 195, 232, 78, 78, 72, 82, 18, 22_000, 0xFF4CAF50),
        Car(23, "Prelude Type SH", "Honda", CarCategory.STREET, GlowRank.COMMON, 200, 228, 75, 73, 68, 78, 13, 13_000, 0xFF9E9E9E),

        // MITSUBISHI (4)
        Car(24, "Lancer Evolution X FQ400", "Mitsubishi", CarCategory.JDM, GlowRank.EPIC, 400, 290, 88, 86, 84, 87, 36, 98_000, 0xFF2196F3),
        Car(25, "Lancer Evolution IX MR", "Mitsubishi", CarCategory.JDM, GlowRank.RARE, 320, 268, 84, 84, 80, 85, 28, 48_000, 0xFF2196F3),
        Car(26, "Eclipse GSX Turbo", "Mitsubishi", CarCategory.JDM, GlowRank.RARE, 210, 240, 78, 76, 74, 78, 22, 30_000, 0xFF9C27B0),
        Car(27, "3000GT VR-4", "Mitsubishi", CarCategory.JDM, GlowRank.UNCOMMON, 320, 260, 76, 75, 72, 76, 16, 20_000, 0xFF9E9E9E),

        // FORD (7)
        Car(28, "Mustang Shelby GT500", "Ford", CarCategory.MUSCLE, GlowRank.EPIC, 760, 295, 85, 78, 82, 78, 38, 105_000, 0xFFFF006E),
        Car(29, "GT Mk2", "Ford", CarCategory.HYPERCAR, GlowRank.LEGENDARY, 700, 348, 92, 88, 90, 87, 45, 180_000, 0xFF00FFFF),
        Car(30, "Mustang GT350R", "Ford", CarCategory.MUSCLE, GlowRank.RARE, 526, 280, 82, 82, 78, 82, 28, 52_000, 0xFF2196F3),
        Car(31, "Mustang Boss 302", "Ford", CarCategory.CLASSIC, GlowRank.RARE, 444, 265, 78, 76, 72, 76, 22, 38_000, 0xFFFFD700),
        Car(32, "Focus RS MK3", "Ford", CarCategory.STREET, GlowRank.RARE, 350, 265, 80, 82, 76, 84, 24, 34_000, 0xFF2196F3),
        Car(33, "Mustang GT Premium", "Ford", CarCategory.MUSCLE, GlowRank.UNCOMMON, 460, 250, 76, 72, 68, 72, 16, 20_000, 0xFF4CAF50),
        Car(34, "Mustang Mach-E GT", "Ford", CarCategory.ELECTRIC, GlowRank.UNCOMMON, 480, 250, 78, 74, 70, 74, 15, 18_000, 0xFF4CAF50),

        // DODGE (5)
        Car(35, "Viper ACR Final Edition", "Dodge", CarCategory.HYPERCAR, GlowRank.LEGENDARY, 645, 330, 88, 92, 88, 90, 44, 175_000, 0xFFFF0000),
        Car(36, "Challenger SRT Hellcat Redeye", "Dodge", CarCategory.MUSCLE, GlowRank.EPIC, 797, 302, 84, 74, 80, 74, 36, 92_000, 0xFFFF006E),
        Car(37, "Demon 170", "Dodge", CarCategory.MUSCLE, GlowRank.LEGENDARY, 1025, 235, 98, 72, 95, 68, 45, 185_000, 0xFFFF0000),
        Car(38, "Charger RT Scat Pack", "Dodge", CarCategory.MUSCLE, GlowRank.RARE, 485, 270, 78, 72, 74, 72, 24, 42_000, 0xFF9C27B0),
        Car(39, "Viper GTS Heritage", "Dodge", CarCategory.CLASSIC, GlowRank.EPIC, 450, 290, 80, 86, 82, 84, 30, 78_000, 0xFFFF0000),

        // CHEVROLET (6)
        Car(40, "Corvette C8 Z06", "Chevrolet", CarCategory.HYPERCAR, GlowRank.LEGENDARY, 670, 315, 90, 90, 88, 90, 44, 165_000, 0xFFFFD700),
        Car(41, "Camaro ZL1 1LE", "Chevrolet", CarCategory.MUSCLE, GlowRank.EPIC, 650, 295, 85, 82, 82, 82, 36, 96_000, 0xFFFF9800),
        Car(42, "Corvette C7 ZR1", "Chevrolet", CarCategory.HYPERCAR, GlowRank.EPIC, 755, 340, 88, 88, 86, 87, 38, 125_000, 0xFFFFD700),
        Car(43, "Camaro SS 1LE", "Chevrolet", CarCategory.MUSCLE, GlowRank.RARE, 455, 260, 78, 78, 74, 78, 25, 44_000, 0xFFFF9800),
        Car(44, "Corvette Stingray C3", "Chevrolet", CarCategory.CLASSIC, GlowRank.RARE, 350, 240, 74, 70, 68, 72, 20, 32_000, 0xFFFFD700),
        Car(45, "El Camino SS 454", "Chevrolet", CarCategory.CLASSIC, GlowRank.COMMON, 360, 225, 72, 65, 65, 68, 14, 14_000, 0xFF9E9E9E),

        // LAMBORGHINI (5)
        Car(46, "Aventador SVJ", "Lamborghini", CarCategory.HYPERCAR, GlowRank.LEGENDARY, 770, 352, 92, 90, 92, 88, 48, 420_000, 0xFFFF9800),
        Car(47, "Huracán STO", "Lamborghini", CarCategory.HYPERCAR, GlowRank.EPIC, 640, 310, 90, 92, 88, 92, 40, 220_000, 0xFF00FF00),
        Car(48, "Murciélago LP640", "Lamborghini", CarCategory.HYPERCAR, GlowRank.EPIC, 640, 340, 88, 86, 86, 85, 36, 195_000, 0xFFFF9800),
        Car(49, "Countach LPI 800-4", "Lamborghini", CarCategory.CLASSIC, GlowRank.LEGENDARY, 814, 355, 90, 82, 90, 80, 42, 280_000, 0xFF00FFFF),
        Car(50, "Diablo VT Roadster", "Lamborghini", CarCategory.CLASSIC, GlowRank.EPIC, 528, 325, 85, 82, 82, 78, 32, 145_000, 0xFFFFD700),

        // FERRARI (6)
        Car(51, "LaFerrari Aperta", "Ferrari", CarCategory.HYPERCAR, GlowRank.LEGENDARY, 963, 360, 95, 92, 96, 92, 50, 2_500_000, 0xFFFF0000),
        Car(52, "SF90 Stradale Spider", "Ferrari", CarCategory.HYPERCAR, GlowRank.LEGENDARY, 1000, 340, 94, 90, 95, 90, 48, 580_000, 0xFFFF0000),
        Car(53, "488 Pista Spider", "Ferrari", CarCategory.HYPERCAR, GlowRank.EPIC, 720, 340, 92, 90, 90, 90, 40, 355_000, 0xFFFF0000),
        Car(54, "F40 LM", "Ferrari", CarCategory.CLASSIC, GlowRank.LEGENDARY, 720, 350, 90, 84, 88, 82, 42, 385_000, 0xFFFF0000),
        Car(55, "296 GTB Assetto Fiorano", "Ferrari", CarCategory.HYPERCAR, GlowRank.RARE, 830, 330, 88, 88, 88, 88, 30, 148_000, 0xFFFF0000),
        Car(56, "Enzo Ferrari", "Ferrari", CarCategory.HYPERCAR, GlowRank.LEGENDARY, 660, 355, 92, 90, 90, 88, 44, 1_800_000, 0xFFFF0000),

        // McLAREN (5)
        Car(57, "P1 GTR", "McLaren", CarCategory.PROTOTYPE, GlowRank.LEGENDARY, 1000, 350, 95, 94, 96, 94, 50, 1_500_000, 0xFFFF9800),
        Car(58, "720S Stealth", "McLaren", CarCategory.HYPERCAR, GlowRank.EPIC, 720, 341, 92, 90, 90, 92, 40, 285_000, 0xFF00FFFF),
        Car(59, "Senna GTR", "McLaren", CarCategory.PROTOTYPE, GlowRank.LEGENDARY, 825, 340, 94, 96, 94, 96, 48, 1_200_000, 0xFFFF9800),
        Car(60, "Artura Spider", "McLaren", CarCategory.HYPERCAR, GlowRank.RARE, 700, 330, 88, 88, 86, 88, 28, 120_000, 0xFF00FFFF),
        Car(61, "Speedtail", "McLaren", CarCategory.PROTOTYPE, GlowRank.LEGENDARY, 1050, 403, 98, 86, 92, 84, 50, 2_200_000, 0xFF8B00FF),

        // TESLA (4)
        Car(62, "Roadster 2.0 SpaceX", "Tesla", CarCategory.ELECTRIC, GlowRank.LEGENDARY, 1100, 400, 100, 82, 98, 80, 48, 320_000, 0xFF00FFFF),
        Car(63, "Model S Plaid+", "Tesla", CarCategory.ELECTRIC, GlowRank.EPIC, 1020, 322, 96, 80, 95, 78, 38, 145_000, 0xFF2196F3),
        Car(64, "Model 3 Performance", "Tesla", CarCategory.ELECTRIC, GlowRank.RARE, 450, 261, 82, 78, 80, 76, 24, 48_000, 0xFF2196F3),
        Car(65, "Cybertruck Beast Mode", "Tesla", CarCategory.ELECTRIC, GlowRank.UNCOMMON, 845, 210, 82, 65, 75, 62, 18, 75_000, 0xFF9E9E9E)
    )

    val raceEvents: List<RaceEvent> = listOf(
        // SPRINT (8)
        RaceEvent(1, "Downtown Dash", RaceType.SPRINT, "Downtown Core", 2_500, 120, 5, 500, 0, 1),
        RaceEvent(2, "Harbor Sprint", RaceType.SPRINT, "Port District", 4_000, 180, 8, 800, 30, 2),
        RaceEvent(3, "Neon Alley Run", RaceType.SPRINT, "Neon Strip", 6_500, 250, 12, 1_200, 80, 3),
        RaceEvent(4, "Industrial Flash", RaceType.SPRINT, "Warehouse Zone", 9_000, 320, 15, 1_800, 150, 4),
        RaceEvent(5, "Bridge Blitz", RaceType.SPRINT, "Harbor Bridge", 13_000, 400, 20, 2_500, 250, 5),
        RaceEvent(6, "Airport Express", RaceType.SPRINT, "Restricted Zone", 20_000, 500, 28, 4_000, 400, 6),
        RaceEvent(7, "Sky Corridor", RaceType.SPRINT, "Elevated District", 28_000, 620, 35, 5_500, 600, 7),
        RaceEvent(8, "Ghost Mile", RaceType.SPRINT, "Underground", 40_000, 800, 45, 8_000, 900, 9),

        // CIRCUIT (8)
        RaceEvent(9, "City Loop I", RaceType.CIRCUIT, "Downtown Core", 3_500, 160, 7, 700, 10, 1),
        RaceEvent(10, "Harbor Circuit", RaceType.CIRCUIT, "Port District", 5_500, 220, 10, 1_100, 50, 2),
        RaceEvent(11, "Neon Grand Prix", RaceType.CIRCUIT, "Neon Strip", 8_500, 300, 16, 1_700, 100, 3),
        RaceEvent(12, "Industrial Circuit", RaceType.CIRCUIT, "Warehouse Zone", 12_000, 380, 20, 2_400, 180, 4),
        RaceEvent(13, "Bay Circuit", RaceType.CIRCUIT, "Coastal Bay", 18_000, 460, 26, 3_600, 280, 5),
        RaceEvent(14, "Viaduct GP", RaceType.CIRCUIT, "Elevated District", 26_000, 580, 34, 5_200, 500, 6),
        RaceEvent(15, "Syndicate Circuit", RaceType.CIRCUIT, "Underground", 36_000, 720, 44, 7_200, 800, 8),
        RaceEvent(16, "Grand Prix Finale", RaceType.CIRCUIT, "Champions Zone", 55_000, 900, 55, 11_000, 1100, 10),

        // DRIFT (6)
        RaceEvent(17, "Alley Drift", RaceType.DRIFT, "Back Alleys", 3_000, 140, 6, 600, 20, 1),
        RaceEvent(18, "Rooftop Slide", RaceType.DRIFT, "Elevated District", 5_000, 200, 10, 1_000, 70, 2),
        RaceEvent(19, "Neon Drift Battle", RaceType.DRIFT, "Neon Strip", 8_000, 280, 14, 1_600, 140, 3),
        RaceEvent(20, "Harbor Angle", RaceType.DRIFT, "Port District", 12_000, 360, 20, 2_400, 250, 5),
        RaceEvent(21, "Canyon Carve", RaceType.DRIFT, "Outer District", 18_000, 450, 28, 3_600, 400, 6),
        RaceEvent(22, "King of Drift", RaceType.DRIFT, "Underground", 30_000, 600, 38, 6_000, 700, 8),

        // TIME ATTACK (6)
        RaceEvent(23, "Midnight Hotlap", RaceType.TIME_ATTACK, "Downtown Core", 2_800, 130, 5, 550, 0, 1),
        RaceEvent(24, "Speed Trial I", RaceType.TIME_ATTACK, "Neon Strip", 5_000, 200, 10, 1_000, 60, 2),
        RaceEvent(25, "Sector Zero", RaceType.TIME_ATTACK, "Restricted Zone", 8_000, 280, 14, 1_600, 130, 3),
        RaceEvent(26, "Ultimate Hotlap", RaceType.TIME_ATTACK, "Champions Zone", 14_000, 380, 22, 2_800, 280, 5),
        RaceEvent(27, "Ghost Record", RaceType.TIME_ATTACK, "Underground", 22_000, 480, 30, 4_400, 480, 7),
        RaceEvent(28, "Legend Lap", RaceType.TIME_ATTACK, "Champions Zone", 38_000, 650, 42, 7_600, 850, 9),

        // NIGHT RACE (5)
        RaceEvent(29, "Midnight Cruise", RaceType.NIGHT_RACE, "Neon Strip", 4_500, 190, 9, 900, 30, 2),
        RaceEvent(30, "Shadow Sprint", RaceType.NIGHT_RACE, "Back Alleys", 7_500, 270, 14, 1_500, 100, 3),
        RaceEvent(31, "Neon Nightmare", RaceType.NIGHT_RACE, "Neon Strip", 13_000, 370, 22, 2_600, 200, 4),
        RaceEvent(32, "Black Run", RaceType.NIGHT_RACE, "Restricted Zone", 22_000, 500, 30, 4_400, 380, 6),
        RaceEvent(33, "Phantom Race", RaceType.NIGHT_RACE, "Underground", 35_000, 680, 42, 7_000, 700, 8),

        // HIGHWAY RUN (4)
        RaceEvent(34, "Freeway Flash", RaceType.HIGHWAY_RUN, "Outer District", 6_000, 240, 11, 1_200, 80, 2),
        RaceEvent(35, "Highway Outlaw", RaceType.HIGHWAY_RUN, "Coastal Highway", 11_000, 340, 18, 2_200, 200, 4),
        RaceEvent(36, "Interstate King", RaceType.HIGHWAY_RUN, "Northern Highway", 20_000, 480, 28, 4_000, 400, 6),
        RaceEvent(37, "Top Speed Run", RaceType.HIGHWAY_RUN, "Airport Freeway", 35_000, 680, 40, 7_000, 750, 9),

        // ELITE TOURNAMENT (3)
        RaceEvent(38, "City Syndicate Open", RaceType.ELITE_TOURNAMENT, "All Districts", 80_000, 1200, 70, 16_000, 500, 7),
        RaceEvent(39, "Neon Cup Championship", RaceType.ELITE_TOURNAMENT, "All Districts", 150_000, 2000, 100, 30_000, 900, 9),
        RaceEvent(40, "Ghost Syndicate Invitational", RaceType.ELITE_TOURNAMENT, "Underground", 300_000, 3500, 150, 60_000, 1500, 10)
    )

    val crewMembers: List<CrewMember> = listOf(
        // MECHANICS (5)
        CrewMember(1, "Kenji Moto", CrewRole.MECHANIC, 5, 8_000, 1_200, 1.10f),
        CrewMember(2, "Ryo Tanaka", CrewRole.MECHANIC, 8, 15_000, 2_000, 1.15f),
        CrewMember(3, "Viktor Sorokin", CrewRole.MECHANIC, 12, 25_000, 3_200, 1.22f),
        CrewMember(4, "Marco DiSilvio", CrewRole.MECHANIC, 18, 45_000, 5_500, 1.30f),
        CrewMember(5, "Ghost Wrench", CrewRole.MECHANIC, 25, 90_000, 10_000, 1.40f),

        // TUNERS (5)
        CrewMember(6, "Yuki Nakashima", CrewRole.TUNER, 4, 7_000, 1_100, 1.08f),
        CrewMember(7, "Daisuke Ito", CrewRole.TUNER, 9, 18_000, 2_400, 1.16f),
        CrewMember(8, "Alexei Volkov", CrewRole.TUNER, 14, 32_000, 4_200, 1.24f),
        CrewMember(9, "Priya Sharma", CrewRole.TUNER, 20, 65_000, 7_500, 1.35f),
        CrewMember(10, "Neural Tune", CrewRole.TUNER, 28, 130_000, 14_000, 1.48f),

        // HACKERS (5)
        CrewMember(11, "Zero-Day", CrewRole.HACKER, 6, 10_000, 1_500, 1.12f),
        CrewMember(12, "Cipher", CrewRole.HACKER, 10, 20_000, 2_800, 1.18f),
        CrewMember(13, "Phantom_X", CrewRole.HACKER, 15, 38_000, 5_000, 1.28f),
        CrewMember(14, "NullByte", CrewRole.HACKER, 22, 75_000, 9_000, 1.38f),
        CrewMember(15, "Ghost Protocol", CrewRole.HACKER, 30, 160_000, 18_000, 1.55f),

        // SCOUTS (5)
        CrewMember(16, "Kira Yamamoto", CrewRole.SCOUT, 5, 6_500, 1_000, 1.10f),
        CrewMember(17, "Lena Volkov", CrewRole.SCOUT, 8, 13_000, 1_800, 1.15f),
        CrewMember(18, "Hawk Eye", CrewRole.SCOUT, 13, 28_000, 3_500, 1.24f),
        CrewMember(19, "Suki Tanaka", CrewRole.SCOUT, 19, 55_000, 6_500, 1.34f),
        CrewMember(20, "Oracle", CrewRole.SCOUT, 27, 120_000, 13_000, 1.50f),

        // DRIVERS (5)
        CrewMember(21, "Akira Sato", CrewRole.DRIVER, 7, 12_000, 1_700, 1.12f),
        CrewMember(22, "Duke Ramirez", CrewRole.DRIVER, 11, 22_000, 3_000, 1.20f),
        CrewMember(23, "Scarlett Cruz", CrewRole.DRIVER, 16, 42_000, 5_500, 1.30f),
        CrewMember(24, "Thunder Kai", CrewRole.DRIVER, 23, 85_000, 10_500, 1.42f),
        CrewMember(25, "Legendary Ghost", CrewRole.DRIVER, 32, 180_000, 20_000, 1.60f),

        // MANAGERS (5)
        CrewMember(26, "Monica Chen", CrewRole.MANAGER, 4, 9_000, 1_400, 1.15f),
        CrewMember(27, "Santiago Reyes", CrewRole.MANAGER, 9, 18_000, 2_600, 1.20f),
        CrewMember(28, "Nadia Petrov", CrewRole.MANAGER, 15, 35_000, 4_800, 1.30f),
        CrewMember(29, "Marcus Webb", CrewRole.MANAGER, 21, 70_000, 8_500, 1.42f),
        CrewMember(30, "Shadow Director", CrewRole.MANAGER, 30, 150_000, 17_000, 1.58f)
    )

    val parts: List<Part> = buildList {
        // ENGINE (15)
        add(Part(1, "Stage 1 Engine Kit", PartType.ENGINE, GlowRank.COMMON, 5, 2_500, "Mild power increase for street builds"))
        add(Part(2, "Stage 2 Engine Kit", PartType.ENGINE, GlowRank.UNCOMMON, 10, 6_000, "Stronger internals and better compression"))
        add(Part(3, "Stage 3 Race Engine", PartType.ENGINE, GlowRank.RARE, 18, 14_000, "Full race-spec internal rebuild"))
        add(Part(4, "High-Comp Pistons", PartType.ENGINE, GlowRank.UNCOMMON, 8, 4_500, "Higher compression for more power"))
        add(Part(5, "Forged Rods", PartType.ENGINE, GlowRank.RARE, 14, 10_000, "Handles extreme boost levels"))
        add(Part(6, "Billet Crankshaft", PartType.ENGINE, GlowRank.EPIC, 24, 28_000, "Precision machined for max reliability"))
        add(Part(7, "Race Camshafts", PartType.ENGINE, GlowRank.RARE, 16, 12_000, "Aggressive cam profiles for top-end power"))
        add(Part(8, "Ported Cylinder Head", PartType.ENGINE, GlowRank.RARE, 12, 9_000, "Improved flow for more power"))
        add(Part(9, "Stroker Kit", PartType.ENGINE, GlowRank.EPIC, 28, 45_000, "Increases displacement for more torque"))
        add(Part(10, "Dry Sump Kit", PartType.ENGINE, GlowRank.EPIC, 20, 32_000, "Track-ready oil management"))
        add(Part(11, "Titanium Valves", PartType.ENGINE, GlowRank.RARE, 15, 11_000, "Lighter valves for higher RPM"))
        add(Part(12, "Race Oil Pump", PartType.ENGINE, GlowRank.UNCOMMON, 7, 3_800, "Ensures oil pressure under G-forces"))
        add(Part(13, "Engine Management ECU Tune", PartType.ENGINE, GlowRank.UNCOMMON, 9, 5_500, "Custom ECU mapping for power gains"))
        add(Part(14, "Legendary Engine Rebuild", PartType.ENGINE, GlowRank.LEGENDARY, 40, 120_000, "Full factory plus custom build"))
        add(Part(15, "Prototype Power Unit", PartType.ENGINE, GlowRank.LEGENDARY, 50, 200_000, "Experimental powertrain technology"))

        // TURBO (15)
        add(Part(16, "Stock Replacement Turbo", PartType.TURBO, GlowRank.COMMON, 5, 2_000, "Drop-in replacement with slight improvement"))
        add(Part(17, "Upgraded Intercooler", PartType.TURBO, GlowRank.UNCOMMON, 8, 4_200, "Keeps charge air cool under boost"))
        add(Part(18, "Sport Turbocharger", PartType.TURBO, GlowRank.RARE, 15, 12_000, "Faster spool and higher boost"))
        add(Part(19, "Twin-Turbo Kit", PartType.TURBO, GlowRank.EPIC, 26, 42_000, "Two turbos for relentless power"))
        add(Part(20, "Anti-Lag System", PartType.TURBO, GlowRank.RARE, 14, 10_500, "Eliminates turbo lag at all RPMs"))
        add(Part(21, "Sequential Turbo Setup", PartType.TURBO, GlowRank.EPIC, 28, 55_000, "Small and large turbos working together"))
        add(Part(22, "Ceramic Ball Bearings", PartType.TURBO, GlowRank.RARE, 10, 7_500, "Faster spooling bearings"))
        add(Part(23, "Boost Controller", PartType.TURBO, GlowRank.UNCOMMON, 7, 3_500, "Fine-tune boost levels"))
        add(Part(24, "Racing Wastegate", PartType.TURBO, GlowRank.RARE, 12, 9_500, "Precise boost control"))
        add(Part(25, "Massive Single Turbo", PartType.TURBO, GlowRank.LEGENDARY, 42, 150_000, "Maximum power at the top end"))
        add(Part(26, "Exhaust Manifold Upgrade", PartType.TURBO, GlowRank.UNCOMMON, 6, 3_200, "Better exhaust flow into turbo"))
        add(Part(27, "BOV Upgraded", PartType.TURBO, GlowRank.COMMON, 3, 1_200, "Vents boost on throttle lift"))
        add(Part(28, "Heat Wrap Kit", PartType.TURBO, GlowRank.COMMON, 4, 1_800, "Keeps heat away from components"))
        add(Part(29, "Turbo Timer", PartType.TURBO, GlowRank.COMMON, 2, 900, "Protects turbo after hard runs"))
        add(Part(30, "Triple Turbo Array", PartType.TURBO, GlowRank.LEGENDARY, 48, 220_000, "Three turbos, one insane machine"))

        // SUSPENSION (15)
        add(Part(31, "Sport Springs", PartType.SUSPENSION, GlowRank.COMMON, 4, 1_500, "Lowers ride height slightly"))
        add(Part(32, "Upgraded Shocks", PartType.SUSPENSION, GlowRank.UNCOMMON, 7, 4_000, "Better damping for aggressive driving"))
        add(Part(33, "Coilover Kit", PartType.SUSPENSION, GlowRank.RARE, 14, 11_000, "Adjustable height and damping"))
        add(Part(34, "Racing Sway Bars", PartType.SUSPENSION, GlowRank.RARE, 12, 8_500, "Reduces body roll in corners"))
        add(Part(35, "Camber Adjustment Kit", PartType.SUSPENSION, GlowRank.UNCOMMON, 8, 4_500, "Optimizes tire contact patch"))
        add(Part(36, "Racing Coilovers", PartType.SUSPENSION, GlowRank.EPIC, 22, 35_000, "Track-spec adjustable suspension"))
        add(Part(37, "Strut Tower Brace", PartType.SUSPENSION, GlowRank.COMMON, 5, 2_200, "Stiffens chassis for better response"))
        add(Part(38, "Roll Cage", PartType.SUSPENSION, GlowRank.EPIC, 18, 28_000, "Safety and rigidity for track racing"))
        add(Part(39, "Upgraded Control Arms", PartType.SUSPENSION, GlowRank.RARE, 13, 10_000, "Better suspension geometry"))
        add(Part(40, "Air Suspension System", PartType.SUSPENSION, GlowRank.EPIC, 20, 32_000, "Adjustable on-the-fly"))
        add(Part(41, "Carbon Fiber Subframe", PartType.SUSPENSION, GlowRank.LEGENDARY, 35, 95_000, "Ultralight chassis rigidity"))
        add(Part(42, "Motorsport Dampers", PartType.SUSPENSION, GlowRank.LEGENDARY, 42, 145_000, "Top-level competition suspension"))
        add(Part(43, "Anti-Roll Bar Upgrade", PartType.SUSPENSION, GlowRank.UNCOMMON, 6, 3_500, "Reduces understeer in corners"))
        add(Part(44, "Subframe Collars", PartType.SUSPENSION, GlowRank.COMMON, 3, 1_200, "Reduces flex in subframe"))
        add(Part(45, "Drift Angle Kit", PartType.SUSPENSION, GlowRank.RARE, 16, 14_000, "Extreme steering angle for drifting"))

        // TIRES (15)
        add(Part(46, "Performance Street Tires", PartType.TIRES, GlowRank.COMMON, 4, 1_800, "Better grip than stock"))
        add(Part(47, "Semi-Slick Tires", PartType.TIRES, GlowRank.UNCOMMON, 9, 5_500, "Maximum grip on dry surfaces"))
        add(Part(48, "Racing Slicks", PartType.TIRES, GlowRank.RARE, 16, 14_000, "Full race compound tires"))
        add(Part(49, "Drift Tires", PartType.TIRES, GlowRank.RARE, 12, 10_000, "Optimal for controlled slides"))
        add(Part(50, "Wet Weather Tires", PartType.TIRES, GlowRank.UNCOMMON, 8, 4_800, "Superior grip in wet conditions"))
        add(Part(51, "All-Season High Perf", PartType.TIRES, GlowRank.COMMON, 5, 2_400, "Year-round performance"))
        add(Part(52, "Competition Slicks", PartType.TIRES, GlowRank.EPIC, 24, 38_000, "Maximum grip at racing speeds"))
        add(Part(53, "Drag Radials", PartType.TIRES, GlowRank.RARE, 18, 16_000, "Maximum traction on launch"))
        add(Part(54, "Yokohama Advan Neova", PartType.TIRES, GlowRank.RARE, 15, 12_000, "Legendary street performance tire"))
        add(Part(55, "Michelin Pilot Sport Cup 2", PartType.TIRES, GlowRank.EPIC, 26, 45_000, "Factory fit on hypercars"))
        add(Part(56, "Prototype Compound", PartType.TIRES, GlowRank.LEGENDARY, 40, 120_000, "Experimental maximum grip"))
        add(Part(57, "Staggered Tire Set", PartType.TIRES, GlowRank.UNCOMMON, 7, 4_200, "Wider rears for better traction"))
        add(Part(58, "Tire Pressure Monitor", PartType.TIRES, GlowRank.COMMON, 2, 800, "Optimal pressure for performance"))
        add(Part(59, "Carbon Fiber Wheels", PartType.TIRES, GlowRank.LEGENDARY, 30, 85_000, "Eliminates unsprung weight"))
        add(Part(60, "Run-Flat Race Tires", PartType.TIRES, GlowRank.EPIC, 20, 32_000, "Handles punctures at speed"))

        // ECU (12)
        add(Part(61, "Stage 1 ECU Remap", PartType.ECU, GlowRank.COMMON, 5, 2_200, "Basic power and efficiency tuning"))
        add(Part(62, "Stage 2 ECU Tune", PartType.ECU, GlowRank.UNCOMMON, 10, 5_800, "Aggressive fueling and timing"))
        add(Part(63, "Stage 3 Race Tune", PartType.ECU, GlowRank.RARE, 18, 13_000, "Full race calibration"))
        add(Part(64, "Launch Control", PartType.ECU, GlowRank.UNCOMMON, 8, 4_500, "Perfect starts every time"))
        add(Part(65, "Anti-Lag Tune", PartType.ECU, GlowRank.RARE, 14, 10_000, "Keeps boost up on throttle lift"))
        add(Part(66, "Flat-Shift Tune", PartType.ECU, GlowRank.RARE, 12, 9_000, "Change gears without lifting"))
        add(Part(67, "Nitrous Controller", PartType.ECU, GlowRank.EPIC, 22, 35_000, "Precise nitrous delivery"))
        add(Part(68, "AI Driving ECU", PartType.ECU, GlowRank.EPIC, 26, 50_000, "Adaptive performance algorithms"))
        add(Part(69, "Traction Control Off-Switch", PartType.ECU, GlowRank.UNCOMMON, 6, 3_000, "Full power with no limits"))
        add(Part(70, "Race ABS Module", PartType.ECU, GlowRank.RARE, 15, 12_000, "Track-tuned ABS for corner entry"))
        add(Part(71, "Drift Mode ECU", PartType.ECU, GlowRank.RARE, 16, 14_000, "Oversteer on demand"))
        add(Part(72, "Legendary Race System", PartType.ECU, GlowRank.LEGENDARY, 38, 110_000, "Winning edge in every race"))

        // TRANSMISSION (12)
        add(Part(73, "Short Ratio Gear Set", PartType.TRANSMISSION, GlowRank.COMMON, 5, 2_500, "Shorter gears for faster acceleration"))
        add(Part(74, "Limited Slip Differential", PartType.TRANSMISSION, GlowRank.RARE, 16, 13_000, "Puts power down in corners"))
        add(Part(75, "Sequential Gearbox", PartType.TRANSMISSION, GlowRank.EPIC, 28, 55_000, "Lightning fast gear changes"))
        add(Part(76, "Upgraded Clutch", PartType.TRANSMISSION, GlowRank.UNCOMMON, 8, 4_200, "Handles more power without slipping"))
        add(Part(77, "Twin-Disc Clutch", PartType.TRANSMISSION, GlowRank.RARE, 14, 11_000, "For extreme power levels"))
        add(Part(78, "Differential Upgrade", PartType.TRANSMISSION, GlowRank.UNCOMMON, 9, 5_000, "Better power distribution"))
        add(Part(79, "Drift LSD", PartType.TRANSMISSION, GlowRank.RARE, 18, 15_000, "Perfect for sustained drifts"))
        add(Part(80, "Paddle Shift Conversion", PartType.TRANSMISSION, GlowRank.EPIC, 22, 38_000, "Race car feel in any car"))
        add(Part(81, "Straight-Cut Gears", PartType.TRANSMISSION, GlowRank.LEGENDARY, 36, 100_000, "Maximum power transfer"))
        add(Part(82, "Transfer Case Upgrade", PartType.TRANSMISSION, GlowRank.UNCOMMON, 7, 3_800, "Better AWD distribution"))
        add(Part(83, "Launch Control Trans", PartType.TRANSMISSION, GlowRank.RARE, 13, 10_500, "Optimized for drag starts"))
        add(Part(84, "Titanium Driveshaft", PartType.TRANSMISSION, GlowRank.EPIC, 20, 30_000, "Lightweight power delivery"))

        // BRAKES (12)
        add(Part(85, "Upgraded Brake Pads", PartType.BRAKES, GlowRank.COMMON, 4, 1_500, "Better bite and fade resistance"))
        add(Part(86, "Braided Brake Lines", PartType.BRAKES, GlowRank.COMMON, 5, 2_000, "Firm pedal feel"))
        add(Part(87, "Sport Rotors", PartType.BRAKES, GlowRank.UNCOMMON, 8, 4_500, "Improved cooling and bite"))
        add(Part(88, "Big Brake Kit", PartType.BRAKES, GlowRank.RARE, 16, 13_000, "Massive rotors for extreme decel"))
        add(Part(89, "Ceramic Brake Pads", PartType.BRAKES, GlowRank.RARE, 14, 11_000, "Low dust, high performance"))
        add(Part(90, "Track Brake Kit", PartType.BRAKES, GlowRank.EPIC, 24, 40_000, "Stops from 200mph without fade"))
        add(Part(91, "Carbon Ceramic Rotors", PartType.BRAKES, GlowRank.LEGENDARY, 38, 115_000, "Hypercar-spec braking"))
        add(Part(92, "Racing Caliper Set", PartType.BRAKES, GlowRank.EPIC, 22, 36_000, "Multi-piston clamping force"))
        add(Part(93, "Brake Bias Adjuster", PartType.BRAKES, GlowRank.RARE, 12, 9_000, "Tune front/rear balance"))
        add(Part(94, "Anti-Lock Race ABS", PartType.BRAKES, GlowRank.RARE, 15, 12_000, "Track-tuned braking"))
        add(Part(95, "Brake Cooling Ducts", PartType.BRAKES, GlowRank.UNCOMMON, 7, 3_800, "Stops brake fade on long runs"))
        add(Part(96, "Hand Brake Line Kit", PartType.BRAKES, GlowRank.UNCOMMON, 6, 3_200, "Sharper drift entry control"))

        // NITROUS (12)
        add(Part(97, "Dry Nitrous Kit 50hp", PartType.NITROUS, GlowRank.COMMON, 5, 2_000, "Simple dry shot for street use"))
        add(Part(98, "Wet Nitrous Kit 100hp", PartType.NITROUS, GlowRank.UNCOMMON, 10, 6_000, "Adds fuel for bigger shots"))
        add(Part(99, "Progressive Nitrous Kit", PartType.NITROUS, GlowRank.RARE, 16, 14_000, "Gradually increases nitrous flow"))
        add(Part(100, "Nitrous Purge System", PartType.NITROUS, GlowRank.UNCOMMON, 7, 3_500, "Eliminates dead fuel on activation"))
        add(Part(101, "Twin-Stage Nitrous", PartType.NITROUS, GlowRank.EPIC, 26, 48_000, "Two stages for maximum attack"))
        add(Part(102, "Race Nitrous Controller", PartType.NITROUS, GlowRank.RARE, 14, 10_500, "Precise nitrous control"))
        add(Part(103, "Nitrous Bottles x2", PartType.NITROUS, GlowRank.UNCOMMON, 8, 4_200, "Double the capacity"))
        add(Part(104, "High-Flow Solenoids", PartType.NITROUS, GlowRank.RARE, 12, 9_000, "More nitrous on demand"))
        add(Part(105, "Nitrous Jets Set", PartType.NITROUS, GlowRank.COMMON, 4, 1_600, "Fine-tune the flow rate"))
        add(Part(106, "Direct Port Nitrous", PartType.NITROUS, GlowRank.EPIC, 28, 58_000, "Individual cylinder injection"))
        add(Part(107, "Cold Nitrous Kit", PartType.NITROUS, GlowRank.LEGENDARY, 40, 125_000, "Maximum density for max power"))
        add(Part(108, "Nitrous Oxide Express System", PartType.NITROUS, GlowRank.LEGENDARY, 45, 180_000, "The ultimate burst of speed"))

        // VISUAL (12)
        add(Part(109, "Matte Black Wrap", PartType.VISUAL, GlowRank.COMMON, 0, 1_200, "Stealth look for night runs"))
        add(Part(110, "Chrome Blue Wrap", PartType.VISUAL, GlowRank.UNCOMMON, 0, 3_000, "Electric blue chrome finish"))
        add(Part(111, "Neon Underglow Kit", PartType.VISUAL, GlowRank.RARE, 0, 8_000, "Neon pink and cyan LED strips"))
        add(Part(112, "Carbon Fiber Hood", PartType.VISUAL, GlowRank.RARE, 2, 7_500, "Lightweight and aggressive look"))
        add(Part(113, "Wide Body Kit", PartType.VISUAL, GlowRank.EPIC, 3, 22_000, "Aggressive aero and stance"))
        add(Part(114, "Rear Wing GT3-Spec", PartType.VISUAL, GlowRank.EPIC, 4, 28_000, "Maximum downforce aero"))
        add(Part(115, "Cyberpunk Vinyl Set", PartType.VISUAL, GlowRank.RARE, 0, 9_500, "Neon circuit board patterns"))
        add(Part(116, "OZ Racing Wheels", PartType.VISUAL, GlowRank.UNCOMMON, 1, 5_500, "Lightweight performance rims"))
        add(Part(117, "VOLK TE37 Wheels", PartType.VISUAL, GlowRank.RARE, 2, 12_000, "Iconic lightweight track wheels"))
        add(Part(118, "HID Projector Headlights", PartType.VISUAL, GlowRank.UNCOMMON, 0, 4_200, "Bright and focused beam"))
        add(Part(119, "Holographic Wrap", PartType.VISUAL, GlowRank.LEGENDARY, 0, 45_000, "Shifts color in the light"))
        add(Part(120, "Full Aero Package", PartType.VISUAL, GlowRank.LEGENDARY, 5, 80_000, "Complete downforce body kit"))
    }

    val achievements: List<Achievement> = listOf(
        Achievement(1, "First Ignition", "Win your very first race", "⚡", "Racing", 1),
        Achievement(2, "Street Racer", "Win 10 races", "🏁", "Racing", 10),
        Achievement(3, "Night Predator", "Win 25 night races", "🌙", "Racing", 25),
        Achievement(4, "Circuit King", "Win 20 circuit races", "🔄", "Racing", 20),
        Achievement(5, "Drift Emperor", "Win 15 drift events", "🌀", "Racing", 15),
        Achievement(6, "Highway Ghost", "Win 10 highway runs", "🛣", "Racing", 10),
        Achievement(7, "Syndicate Champion", "Win an Elite Tournament", "🏆", "Racing", 1),

        Achievement(8, "Collector", "Own 5 cars", "🚗", "Collection", 5),
        Achievement(9, "Garage King", "Own 15 cars", "🏎", "Collection", 15),
        Achievement(10, "Fleet Commander", "Own 30 cars", "🎖", "Collection", 30),
        Achievement(11, "JDM Legend", "Own all JDM cars", "🇯🇵", "Collection", 10),
        Achievement(12, "Hypercar Elite", "Own 5 hypercar class vehicles", "💎", "Collection", 5),
        Achievement(13, "Legend Fleet", "Own 3 legendary cars", "⭐", "Collection", 3),

        Achievement(14, "First Upgrade", "Upgrade one garage building", "🔧", "Garage", 1),
        Achievement(15, "Workshop Master", "Max out the Workshop", "⚙", "Garage", 5),
        Achievement(16, "Full House", "Upgrade all garage buildings to level 3", "🏠", "Garage", 3),
        Achievement(17, "Syndicate HQ", "Max out all garage buildings", "🏭", "Garage", 5),
        Achievement(18, "Part Hoarder", "Own 20 upgrade parts", "📦", "Garage", 20),

        Achievement(19, "First Hire", "Recruit your first crew member", "👤", "Crew", 1),
        Achievement(20, "Full Crew", "Have 6 crew members hired", "👥", "Crew", 6),
        Achievement(21, "Elite Squad", "Have 12 crew members hired", "🎯", "Crew", 12),

        Achievement(22, "First Million", "Earn 1,000,000 credits total", "💰", "Economy", 1_000_000),
        Achievement(23, "Millionaire", "Hold 500,000 credits at once", "💵", "Economy", 500_000),
        Achievement(24, "Black Market Boss", "Complete 10 marketplace transactions", "🛒", "Economy", 10),
        Achievement(25, "Speed Demon", "Reach level 25", "🚀", "Economy", 25)
    )
}
