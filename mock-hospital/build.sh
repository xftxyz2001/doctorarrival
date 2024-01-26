cd ../doctorarrival
./mvnw clean install -DskipTests
cd ../mock-hospital
./mvnw clean package -DskipTests
