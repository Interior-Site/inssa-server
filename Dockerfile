# openjdk8 base image
FROM openjdk:17-oracle

# directory 생성
#RUN mkdir -p /usr/app/
# jar 파일이 복사되는 위치
ENV APP_HOME=/usr/app/
# 작업 시작 위치
WORKDIR $APP_HOME
# jar 파일 복사
COPY build/libs/*.jar app.jar
# cf docker push, random port 사용할 수 없다
EXPOSE 8080
# 실행
CMD ["java", "-jar", "app.jar"]