#!/bin/sh

set -e

CUR_DIR=`pwd`
APP_VERSION=$(cat VERSION)
REPO_URL=mageddo/bookmark-notes

API_PATH=${API_PATH:-$CUR_DIR}
BUILD_PATH=${BUILD_PATH:-$API_PATH/build}

create_release(){

  PAYLOAD=`echo '{
    "tag_name": "VERSION",
    "target_commitish": "TARGET",
    "name": "VERSION",
    "body": "",
    "draft": false,
    "prerelease": true
  }' | sed -e "s/VERSION/$APP_VERSION/" | sed -e "s|TARGET|$CURRENT_BRANCH|"` && \
  TAG_ID=`curl -i -s -f -X POST "https://api.github.com/repos/$REPO_URL/releases?access_token=$REPO_TOKEN" \
--data "$PAYLOAD" | grep -o -E 'id": [0-9]+'| awk '{print $2}' | head -n 1`

}

upload_file(){
  curl --data-binary "@$SOURCE_FILE" -i -w '\n' -f -s -X POST -H 'Content-Type: application/octet-stream' \
"https://uploads.github.com/repos/$REPO_URL/releases/$TAG_ID/assets?name=$TARGET_FILE&access_token=$REPO_TOKEN"
}

apply_version(){

  sed -i -E "s/BOOKMARK_NOTES_VERSION=.+/BOOKMARK_NOTES_VERSION=$APP_VERSION/g" Dockerfile.java
  sed -i -E "s/BOOKMARK_NOTES_VERSION=.+/BOOKMARK_NOTES_VERSION=$APP_VERSION/g" Dockerfile.linux

}

case $1 in

  apply-version )

    apply_version

  ;;

  deps )

    cd ${API_PATH} && go get -v github.com/golang/dep/cmd/dep && dep ensure -v

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

    if [ "$REPO_TOKEN" = "" ] ; then echo "REPO_TOKEN cannot be empty"; exit 1; fi

    if [ "`git config user.email || echo ''`" = "" ]; then
      echo '> custom config'
      git config user.name `git config user.name || echo 'CI BOT'`
      git config user.email `git config user.email || echo 'ci-bot@mageddo.com'`
    fi
    echo '> config'
    git config -l
    echo ''

    REMOTE="https://${REPO_TOKEN}@github.com/${REPO_URL}.git"

    git checkout -b build_branch ${CURRENT_BRANCH}
    echo "> Repository added, currentBranch=${CURRENT_BRANCH}"

    git commit -am "Releasing ${APP_VERSION}" # if there is nothing to commit the program will exits
    git tag ${APP_VERSION}
    git push "$REMOTE" "build_branch:${CURRENT_BRANCH}"
    git status
    echo "> Branch pushed - Branch $CURRENT_BRANCH"

    create_release

    echo "> Release created with id $TAG_ID"

    SOURCE_FILE="build/native-image/dist/bookmark-notes.zip"
    TARGET_FILE=bookmark-notes-linux-amd64-$APP_VERSION.zip
    echo "> Source file hash"
    md5sum $SOURCE_FILE && ls -lha $SOURCE_FILE

    upload_file

    SOURCE_FILE="build/distributions/bookmark-notes.zip"
    TARGET_FILE=bookmark-notes-$APP_VERSION.zip
    echo "> Source file hash"
    md5sum $SOURCE_FILE && ls -lha $SOURCE_FILE

    upload_file

  ;;

esac
