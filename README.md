# Mira-Coinflip

Player-to-player 50/50 coinflip games for the Mira Paper server suite.

Mira-Coinflip is designed for item wagers, matched Vault-backed money wagers, escrowed stakes, player-controlled self-exclusion, match history and secure reward collection.

## Current Release

**v0.1.0-dev** — development foundations for Paper/Minecraft **26.2** (Java 25) and **1.18.2** (Java 17).

**Not production ready.** The current development code provides the `/cf` lobby placeholder and persistent self-exclusion. Wager creation, escrow, rolling, payouts, history and collections are **not implemented or enabled**. No tested release JAR has been published.

[View releases](https://github.com/FiveSOCE/Mira-Coinflip/releases)

## JAR Downloads

**No downloadable JAR has been published yet.** The development branches have not been confirmed to build successfully, and no verified JAR release asset exists. A workflow page or source-code archive is **not** a JAR download.

When build-verified releases are published, their **direct `.jar` download links** will be listed here, separately for:

- **Paper 26.2 (Java 25)** — [development source](https://github.com/FiveSOCE/Mira-Coinflip/tree/feature/coinflip-26.2)
- **Paper 1.18.2 (Java 17)** — [compatibility source](https://github.com/FiveSOCE/Mira-Coinflip/tree/compat/1.18)

[Published GitHub releases](https://github.com/FiveSOCE/Mira-Coinflip/releases) (currently no verified stable JAR).

Do not install a source archive or assume that a GitHub Actions page downloads a plugin JAR.

## Requirements

- Paper 26.2 with Java 25, or Paper 1.18.2 with Java 17, depending on branch
- Vault and a Vault-compatible economy provider for planned **money** wagers
- No MiraCore dependency
- No economy dependency for planned **item-only** wagers

## Coinflip Games

A game is designed to support:

- a single creator and a single challenger
- exactly 50/50 winning odds
- item wagers of up to 10 inventory items per player
- creator approval of a challenger's proposed item offer
- money stakes matched exactly 1:1
- locked, persistent escrow before a listing becomes available
- game cancellation and return of uncommitted stakes
- a rolling animation that reveals, but cannot alter, the selected winner
- persistent winning-item storage and safe later collection
- history, wins, losses and calculated win percentage
- recovery of pending games and settlements after server restarts or crashes

Settlement must be idempotent and recovery-safe to avoid duplicate payments or lost stakes. The gameplay features above are **planned**, not yet released.

## Money Coinflips

Players will create money listings with `/cf <amount>`. The challenger must match the exact amount. Money is intended to enter escrow before the listing or acceptance commits.

Vault integration must fail safely if the economy provider is unavailable.

## Item Coinflips

Players will use `/cf hand` to choose up to 10 inventory items. Challengers can propose item offers, with the original creator required to approve the precise offer before the game rolls.

Won items will be retained in persistent storage until collected using the GUI or `/cf collect`.

## Self-Exclusion

Players can self-exclude from coinflip participation with:

```text
/cf exclude <days>
```

The minimum duration is **10 days**. Longer exclusions are supported and an existing exclusion cannot be shortened.

Self-exclusion records are stored in:

```text
plugins/Mira-Coinflip/self-exclusions.yml
```

This is implemented in the development branches.

## Commands

| Command | Permission | Purpose |
| --- | --- | --- |
| `/cf` | `miracoinflip.use` | Opens the coinflip lobby GUI (placeholder implemented). |
| `/cf hand` | `miracoinflip.use` | Creates an item coinflip (planned). |
| `/cf <amount>` | `miracoinflip.use` | Creates a matched money coinflip (planned). |
| `/cf collect` | `miracoinflip.use` | Collects stored winnings (planned). |
| `/cf cancel` | `miracoinflip.use` | Cancels an open game and refunds escrow (planned). |
| `/cf history` | `miracoinflip.use` | Shows match history and win percentage (planned). |
| `/cf exclude <days>` | `miracoinflip.use` | Sets self-exclusion for at least 10 days (implemented). |

## Permissions

| Permission | Default | Purpose |
| --- | --- | --- |
| `miracoinflip.use` | Everyone | Access to Mira-Coinflip commands. |

There are no administrative commands for influencing winners or changing the odds.

## Data Files

```text
config.yml          - plugin settings and message configuration
self-exclusions.yml - persistent self-exclusion end dates
```

Persistent match escrow, history and reward queues are planned but are not yet stored by the development implementation.

## MiraCore Integration

Mira-Coinflip is **standalone** and does not require MiraCore.

## Building

Check out the target branch, install the corresponding JDK and Gradle, then run:

```bash
gradle clean build
```

The output JAR is created in `build/libs/`. GitHub Actions is configured to upload branch-specific JAR artifacts. Passing builds have not yet been confirmed.

## License

Copyright © 2026 FiveSOCE. **All Rights Reserved.**

[View LICENSE](https://github.com/FiveSOCE/Mira-Coinflip/blob/feature/coinflip-26.2/LICENSE).
