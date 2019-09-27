cd ../creditApp/
./mvnw package -Pdev verify jib:dockerBuild -DskipTests
cd ../bankMS/
./mvnw package -Pprod verify jib:dockerBuild -DskipTests
cd ../loanMS/
./mvnw package -Pprod verify jib:dockerBuild -DskipTests
cd ../rulesEngineMS/
./mvnw package -Pprod verify jib:dockerBuild -DskipTests