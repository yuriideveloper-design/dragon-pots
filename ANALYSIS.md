# Neon Drift Syndicate — Analysis

## App Type
Cyberpunk street racing management RPG (offline, no backend)

## Screens
1. **Splash** — animated logo, 2.2s delay → Dashboard
2. **Dashboard** — credits, level/XP bar, featured car, quick stats & actions
3. **Car Collection** — 65 cars, grid view, search + category filter + owned filter
4. **Car Detail** — full stats, performance bars, buy/sell/select actions
5. **Garage** — 7 buildings (Workshop, Paint Booth, Dyno Room, Tuning Lab, Parts Warehouse, Nitro Station, Showcase), each with 5 upgrade levels
6. **Race Events** — 40 events, 7 race types, type filter, entry fee + reputation gating
7. **Crew** — 30 members (6 roles), hire/fire, active bonus display
8. **Marketplace** — Cars tab + Parts tab, buy/sell with search
9. **Achievements** — 25 achievements, grouped by category, progress bars
10. **Statistics** — full player analytics, fleet breakdown by category
11. **Settings** — sound/haptics/visual toggles, reset progress
12. **More Menu** — hub for Garage, Crew, Marketplace, Statistics, Settings

## Navigation
Bottom nav bar: Home → Cars → Race → Achievements → More (hub)
NavController with string routes. Splash pops itself on transition.

## Data
- 65 cars across 8 categories, 12 manufacturers, 5 rarity tiers
- 40 race events across 7 types (Sprint, Circuit, Drift, Time Attack, Night Race, Highway Run, Elite Tournament)
- 30 crew members across 6 roles (Mechanic, Tuner, Hacker, Scout, Driver, Manager)
- 120 upgrade parts across 9 types
- 25 achievements across 5 categories

## Architecture
- **State**: Single `GameViewModel (AndroidViewModel)` + `StateFlow<GameState>`
- **Persistence**: `SharedPreferences` via `GameRepository`
- **Navigation**: Navigation Compose 2.7.7
- **UI**: Jetpack Compose + Material3

## Design
- AMOLED black background (#000000)
- Neon Cyan (#00FFFF), Neon Pink (#FF006E), Neon Purple (#8B00FF), Electric Blue (#0080FF)
- Neon borders on cards, glassmorphism surfaces, gradient hero banners
- Fullscreen immersive mode (system bars hidden)
