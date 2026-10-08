# Validation

Validated with Java 17 and Maven on 2026-10-09 (Asia/Kolkata).

- Maven verify: passed.
- Workflow tests: 4 passed.
- Packaged application startup: passed on loopback.
- HTTP checks: creation, state transition, stale-version rejection and invalid-input rejection passed.
- Rule inventory: 25 assigned positive rule IDs with checked source anchors and rule hashes.
- SAST findings: expected only; the custom extractor/analyzer has not been run against these sources.

The shared suite folder contains build/startup logs. `negative-controls.csv` describes narrowly scoped safe contrasts; unrelated audit rules can still report there.
