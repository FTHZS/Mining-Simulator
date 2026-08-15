# Mining Simulator

A console mining game with persistent accounts, built around a weighted-rarity drop table — 35 minerals across 3 collections, each obtainable in up to 4 rarity variants.

## About

A text-based mining game where you log in, mine for minerals, and build up an inventory and stats over time. 35 minerals span three collections — Earth, Imaginary, and Air — and each mineral can additionally drop in one of 4 rarity variants (Normal, Ionized, Spectral, Transdimensional), stacking on top of the mineral's own base rarity for another full layer of "how lucky did you get." Rarer minerals print randomized flavor-text as you uncover them, and a limited-time event system periodically shifts drop odds toward a specific collection.

## The LCM-based drop table

The interesting part isn't the game loop, it's how the drop table is built. Rather than hardcoding drop percentages, each mineral gets a rarity *ratio* — some as skewed as `1 : 5 : 672` — and a dedicated `LCMCalculator` class computes the least common multiple across every ratio in a collection, turning them into a single fair, integer-sized pool to roll against. That means adding a new mineral with an arbitrary rarity never requires rebalancing anything else by hand: the pool size just derives from whatever ratios already exist.

This is the same instinct that shows up again in Greed Island's `RarityPool` — don't hardcode probabilities, derive them from ratios so new content always slots in correctly. Different implementation, same core idea.

## How it works

- Log in or sign up — accounts persist to a local file, parsed with a small hand-rolled format (`|| username { ... }`) rather than JSON or serialization
- Mine to earn minerals across the three rarity collections
- Rare minerals trigger short, randomized flavor-text events as you uncover them
- A limited-time "event" system temporarily shifts drop odds toward a specific collection
- Inventory and account stats (blocks mined, rarest ore found) round out the loop
- Shop is on the menu but not implemented yet — it still just prints "Method not implemented." Leaving that honest rather than hiding it.

## Tech

Java · custom drop-table math (GCD/LCM) · hand-rolled file-based persistence

## Status

Playable, with one event pool live. More themed events (Air, Void, Space) and the Shop are planned next.

---

*Built by [Abhinav Biju](https://abhinavbijuportfolio.onrender.com) — see the [full case study](https://abhinavbijuportfolio.onrender.com/projects/mining-simulator) for the complete mineral index and drop odds.*
