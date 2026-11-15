FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

ENV PATH="/opt/exiftool:/opt/dnglab:$PATH"


RUN set -eux \
  && java -version \
  && apk update \
  && apk upgrade \
  # install perl and curl \
  && apk add --no-cache curl perl wget \
  && curl --version \
  && perl -v \
  # install exiftool \
  && mkdir -p /opt/exiftool \
  && cd /opt/exiftool \
  && EXIFTOOL_VERSION=`curl -s https://exiftool.org/ver.txt` \
  && EXIFTOOL_ARCHIVE=Image-ExifTool-${EXIFTOOL_VERSION}.tar.gz \
  && wget https://downloads.sourceforge.net/project/exiftool/$EXIFTOOL_ARCHIVE \
  && CHECKSUM=`curl -s https://exiftool.org/checksums.txt | grep "SHA2-256($EXIFTOOL_ARCHIVE)" | awk -F'= ' '{print $2}'` \
  && echo "${CHECKSUM}  ${EXIFTOOL_ARCHIVE}" | sha256sum -c - \
  && tar -xzf $EXIFTOOL_ARCHIVE --strip-components=1 \
  && rm -f $EXIFTOOL_ARCHIVE \
  && exiftool -ver \
  # install dnglab \
  && mkdir -p /opt/dnglab \
  && cd /opt/dnglab \
  && DNGLAB_VERSION='v0.8.0' \
  && curl -s -O https://github.com/dnglab/dnglab/releases/download/$DNGLAB_VERSION/dnglab_linux_x64 \
  && mv dnglab_linux_x64 dnglab \
  && chmod a+x dnglab

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/api.jar
RUN mkdir /app/config
RUN mkdir /app/files
RUN mkdir /app/log
ENTRYPOINT ["java","-jar","/app/api.jar"]