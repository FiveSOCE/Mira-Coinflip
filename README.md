# Mira-Coinflip

**Copyright © 2026 FiveSOCE. All Rights Reserved.**

Mira-Coinflip is an in-development player-to-player coinflip plugin for Paper. The current branch implements the base command, GUI placeholder, permission check and persisted self-exclusion, but **does not yet accept wagers**. This is deliberate: cash and item stakes must not be taken until crash-consistent escrow and recovery are complete.

## Supported builds
- `feature/coinflip-26.2`: Paper 26.2, Java 25 (development).
- `compat/1.18`: Paper 1.18.2, Java 17 (development).

## JAR download
[GitHub Actions build artifacts](https://github.com/FiveSOCE/Mira-Coinflip/actions/workflows/build.yml): select a **successful Build run** for the relevant branch, then download its artifact. **No stable tested release JAR is currently published.**

## Commands
- `/cf`: GUI launcher.
- `/cf exclude <days>`: Persist self-exclusion for a minimum of 10 days, with no option to shorten an existing exclusion.
- `/cf hand`: Planned item offer creation (not enabled).
- `/cf <amount>`: Planned 1:1 cash wagering (not enabled).
- `/cf collect`: Planned collection storage (not enabled).
- `/cf cancel`: Planned return of open escrow (not enabled).
- `/cf history`: Planned history / statistics (not enabled).

## Intended mechanics
- Exactly 50/50 secure random outcome chosen once, with animation purely visual.
- Money wagers require exact 1:1 matching; item offers have up to 10 items per stake and require creator acceptance.
- Stake removal must be persisted before an open listing becomes visible.
- Crash-consistent, idempotent settlement and reward queues; never pay twice after a restart.
- Cancellation returns unopened escrow. Active exclusion prevents participating in new wagers.
- No admin commands to set or manipulate winners.

## Dependencies
MiraCore is **not required**. Item wagering does not require an economy plugin. Planned money wagering integrates with Vault and requires a Vault-compatible economy provider. Vault is a soft dependency in plugin.yml. Economy mode must fail closed when the provider is missing.

## Build
Use JDK 25 and Gradle on the 26.2 branch:
```sh
gradle clean build
```
A GitHub Actions workflow also runs the build and uploads the JAR as an artifact. **Build and runtime tests have not been verified yet.**

## License
All Rights Reserved; see [LICENSE](LICENSE).
