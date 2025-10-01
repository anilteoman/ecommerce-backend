# =========================================================
# AŞAMA 1: BUILD AŞAMASI (JAR dosyasını oluşturur)
# =========================================================
# Maven ve JDK içeren bir imaj kullanıyoruz.
FROM maven:3.9-eclipse-temurin-17 AS build

# Docker imajı içindeki çalışma dizinini belirliyoruz.
WORKDIR /app

# Proje dosyalarını imaja kopyalıyoruz.
COPY . .

# Maven'ı kullanarak projenin paketlenmesini sağlıyoruz.
# JAR dosyası /app/target/ klasöründe oluşacaktır.
RUN mvn clean install -DskipTests

# =========================================================
# AŞAMA 2: ÇALIŞTIRMA AŞAMASI (Minimal imajı kullanır)
# =========================================================
# Uygulamayı çalıştırmak için sadece JRE (Java Runtime Environment) içeren,
# minimal ve güvenli bir imaj kullanıyoruz (JRE 17).
FROM eclipse-temurin:17-jre-focal

# JAR dosyasını build aşamasından (target klasöründen) kopyalıyoruz.
# Projenizin tam JAR adını (artifactId ve version) kullanmalısınız:
# ÖRNEK: ecomm-0.0.1-SNAPSHOT.jar
# Lütfen bu adı kendi projenizdeki target klasöründen kontrol edin!
COPY --from=build /app/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Uygulamanın çalışacağı portu belirtiyoruz.
EXPOSE 8080

# Uygulamayı başlatma komutu.
ENTRYPOINT ["java", "-jar", "app.jar"]