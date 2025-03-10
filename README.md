### Run test

```bash
# run all tests
mvn test 

# run one test
mvn test -Dtest=JsonTemplateTest 

# run tests by tags
mvn test -Dgroups="smoke-test"

mvn test -Dgroups="ui-test"

mvn test -Dgroups=api-test

mvn test -Dgroups="github, api-test"

mvn test -DexcludedGroups="slow"
```
