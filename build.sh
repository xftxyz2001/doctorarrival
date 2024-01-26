# 后端
cd doctorarrival
./mvnw clean package -DskipTests

# 前端
cd ../doctorarrival-site
npm install
npm run build

cd ../doctorarrival-admin
npm install
npm run build
