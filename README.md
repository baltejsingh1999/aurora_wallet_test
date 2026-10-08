# aurora-wallet

Stored-value accounts, payment receipts and legacy cryptographic/token integrations.

Java 17 / Maven / Spring Boot application with controller, service, immutable domain model, atomic in-memory repository, version conflict handling and four domain workflow tests. The default API stores data in memory; restarting clears it. These are representative test applications, not deployable production services.

## Build and run

```sh
mvn clean verify
mvn spring-boot:run
```

The normal API is `http://127.0.0.1:8093/api/payments`. POST JSON `{"account":"sample-a","merchant":"sample-b","units":2}`; use the returned UUID for GET and POST `/<id>/settle` with `{"expectedVersion":0}`.

## SAST cases

`expected-findings.csv` lists the rules, source locations, methods and extractor requirements assigned to this repository. `// EXPECT:` marks intentional cases; `SafeIntegration` provides narrowly scoped negative controls. Findings are expectations until checked against your extractor and analyzer. An anchor line identifies the relevant expression/configuration, while scanners may report elsewhere in the method.

Legacy adapter classes contain real API calls using the dependencies declared in `pom.xml`. A request object is an explicit remote source. Framework controllers expose some cases; other adapters are intended for static extraction and require external database/directory/device systems to execute. Do not invoke all adapters as a runtime test. JAX-RS resources are compiled for legacy rule coverage; the default Spring application does not register a JAX-RS runtime.

All credentials are synthetic fixture strings. Vulnerable integrations, old libraries, unsafe parsers and audit API uses are intentional. Run only in an isolated test environment. No vulnerable paths execute during domain tests.

Scan **main sources** with resolved Maven dependency types. Use raw `rule_finding_out.csv` / `finding_out.csv` to assess rule coverage: the bundle collapses findings sharing a file, line and CWE in its deduped output. ReDoS requires extractor preprocessing facts. CodeQL query availability and IDs differ from the custom DL rules.

See `VALIDATION.md` for build/runtime results and `negative-controls.csv` for scoped negative expectations.
