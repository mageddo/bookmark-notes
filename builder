#!/bin/sh

set -e

CUR_DIR=`pwd`
APP_VERSION=$(cat VERSION)
REPO_URL=mageddo/bookmark-notes

apply_version(){

  sed -i -E "s/BOOKMARK_NOTES_VERSION=.+/BOOKMARK_NOTES_VERSION=$APP_VERSION/g" Dockerfile.java
  sed -i -E "s/BOOKMARK_NOTES_VERSION=.+/BOOKMARK_NOTES_VERSION=$APP_VERSION/g" Dockerfile.linux

}

case $1 in

  apply-version )

    apply_version

  ;;

  build )

    echo "> starting build $APP_VERSION"
    ./gradlew clean heartbeat build nativeImage copyStatics --info
    echo "> build success"

  ;;

  validate-release )

    if git rev-parse "$APP_VERSION^{}" >/dev/null 2>&1; then
      echo "> Version already exists $APP_VERSION"
      exit 3
    fi

  ;;

  upload-release )

    DESC=$(cat RELEASE-NOTES.md | awk 'BEGIN {RS="|"} {print substr($0, 0, index(substr($0, 3), "#"))}' | sed ':a;N;$!ba;s/\n/\\r\\n/g')

    SOURCE_FILE="build/native-image/dist/bookmark-notes.zip"
    TARGET_FILE=bookmark-notes-linux-amd64-$APP_VERSION.zip

    cp ${SOURCE_FILE} ${TARGET_FILE}

    SOURCE_FILE_2="build/distributions/bookmark-notes.zip"
    TARGET_FILE_2=bookmark-notes-$APP_VERSION.zip
    cp ${SOURCE_FILE_2} ${TARGET_FILE_2}

    github-cli release mageddo bookmark-notes $APP_VERSION $CURRENT_BRANCH "${DESC}" ${TARGET_FILE} ${TARGET_FILE_2}

  ;;

esac
