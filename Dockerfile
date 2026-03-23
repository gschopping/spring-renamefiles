FROM adoptopenjdk/openjdk16:alpine-jre
WORKDIR /app

ENV PATH="/opt/exiftool:$PATH"


RUN set -eux \
  && java -version \
  && apk update \
  && apk upgrade \
  # install perl and curl \
  && apk add --no-cache curl perl \
  && curl --version \
  && perl -v \
  # install exiftool \
  && mkdir -p /opt/exiftool \
  && cd /opt/exiftool \
  && EXIFTOOL_VERSION=`curl -s https://exiftool.org/ver.txt` \
  && EXIFTOOL_ARCHIVE=Image-ExifTool-${EXIFTOOL_VERSION}.tar.gz \
  && curl -s -O https://exiftool.org/$EXIFTOOL_ARCHIVE \
  && CHECKSUM=`curl -s https://exiftool.org/checksums.txt | grep SHA1\(${EXIFTOOL_ARCHIVE} | awk -F'= ' '{print $2}'` \
  && echo "${CHECKSUM}  ${EXIFTOOL_ARCHIVE}" | /usr/bin/sha1sum -c -s - \
  && tar xzf $EXIFTOOL_ARCHIVE --strip-components=1 \
  && rm -f $EXIFTOOL_ARCHIVE \
  && exiftool -ver

COPY target/renamefiles-api-0.0.1-SNAPSHOT.jar /app/api.jar
RUN mkdir /app/config
RUN mkdir /app/files
RUN mkdir /app/log
#CMD java -cp /app/api.jar nl.schoepping.renamefiles/RenamefilesApi
ENTRYPOINT ["java","-jar","/app/api.jar"]