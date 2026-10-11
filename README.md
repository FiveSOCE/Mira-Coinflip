# Mira-Coinflip

**Mira-Coinflip** is a player-to-player, 50/50 coinflip minigame for Minecraft servers, developed by **FiveSOCE**.

> **Development status:** The plugin is not yet ready for production. The current development branches provide a command/GUI foundation and self-exclusion, but **real-money and item wagering, escrow, settlement, history, and reward collection are not implemented yet**. No stable release JAR is available.

## Downloads

| Minecraft target | Source / development branch | Build artifacts |
| --- | --- | --- |
| **Paper 26.2** (Java 25) | [26.2 development branch](https://github.com/FiveSOCE/Mira-Coinflip/tree/feature/coinflip-26.2) | [26.2 Actions runs](https://github.com/FiveSOCE/Mira-Coinflip/actions?query=branch%3Afeature%2Fcoinflip-26.2) |
| **Paper 1.18.2** (Java 17) | [1.18 compatibility branch](https://github.com/FiveSOCE/Mira-Coinflip/tree/compat/1.18) | [1.18 Actions runs](https://github.com/FiveSOCE/Mira-Coinflip/actions?query=branch%3Acompat%2F1.18) |

**How to download:** Open the appropriate Actions link, select a **successful** `Build` run, and download the `Mira-Coinflip-26.2` or `Mira-Coinflip-1.18` artifact under **Artifacts**. Extract the ZIP to obtain the JAR. GitHub may require you to sign in to download an Actions artifact. An artifact link is **not** proof that a successful build exists; check the run status first.

[**GitHub Releases (stable JAR downloads when published)**](https://github.com/FiveSOCE/Mira-Coinflip/releases)

There are **no verified passing builds or published stable JAR releases yet**.

## Planned features

- `/cf`: GUI displaying open coinflips, player history, win percentage and pending winnings.
- `/cf hand`: Create an item coinflip with up to 10 items.
- `/cf <amount>`: Create a money coinflip that must be matched exactly 1:1.
- Item challengers propose their stakes for the original creator to approve.
- A strictly unbiased 50/50 winner selection, with a purely visual rolling animation.
- Persistent escrow for money/items, cancellation/refunds and restart/crash recovery.
- `/cf collect`: Collect won items safely from storage.
- Player-controlled self-exclusion for a minimum of 10 days, optionally longer.
- No administrator commands for changing odds, overriding winners or otherwise influencing matches.

## Current development features

- `/cf` opens a basic placeholder GUI.
- `/cf exclude <days>` records self-exclusion, minimum 10 days.
- Unimplemented wager commands are disabled and do not take stakes.
- Neither MiraCore nor other Mira plugins are required.

## Dependencies

**MiraCore is not required.** Item-only coinflips are designed to be standalone. Planned money coinflips require **Vault** and a Vault-compatible economy provider. The economy provider is not bundled.

## Build from source

The development branches have different Java and Paper targets. Check out the relevant branch and use the matching JDK and Gradle, then run:

```sh
gradle clean build
```

The repositories' GitHub Actions workflows are intended to build and upload artifacts, but passing builds have not yet been verified.

## License

Copyright © 2026 **FiveSOCE**. **All Rights Reserved.** The development branches include the [LICENSE file](https://github.com/FiveSOCE/Mira-Coinflip/blob/feature/coinflip-26.2/LICENSE). No permission to redistribute, modify, or reuse this software is granted without written authorization.
