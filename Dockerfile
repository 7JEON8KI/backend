# 사용할 Java 베이스 이미지
FROM openjdk:11-jdk as build

# 작업 디렉토리 설정
WORKDIR /backend

# Maven 빌드를 위한 파일 복사
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Maven을 사용해 애플리케이션 빌드
RUN ./mvnw install -DskipTests

# Tomcat 베이스 이미지
FROM tomcat:9.0-jdk11-openjdk

# Tomcat의 기본 웹앱 삭제
RUN rm -rf /usr/local/tomcat/webapps/*

# 빌드 단계에서 생성된 WAR 파일을 Tomcat의 webapps 디렉토리로 복사
COPY --from=build /backend/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Tomcat 실행
CMD ["catalina.sh", "run"]
