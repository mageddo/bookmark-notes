#!/bin/sh

set -e

CUR_DIR=`pwd`
APP_VERSION=$(cat VERSION)
REPO_URL=mageddo/bookmark-notes

API_PATH=${API_PATH:-$CUR_DIR}
BUILD_PATH=${BUILD_PATH:-$API_PATH/build}

if [ "$REPO_TOKEN" = "" ] ; then echo "REPO_TOKEN cannot be empty"; exit 1; fi

create_release(){

	PAYLOAD=`echo '{
		"tag_name": "VERSION",
		"target_commitish": "TARGET",
		"name": "VERSION",
		"body": "",
		"draft": false,
		"prerelease": true
	}' | sed -e "s/VERSION/$APP_VERSION/" | sed -e "s/TARGET/$CURRENT_BRANCH/"` && \
	TAG_ID=`curl -i -s -f -X POST "https://api.github.com/repos/$REPO_URL/releases?access_token=$REPO_TOKEN" \
--data "$PAYLOAD" | grep -o -E 'id": [0-9]+'| awk '{print $2}' | head -n 1`

}

upload_file(){
	curl --data-binary "@$SOURCE_FILE" -i -w '\n' -f -s -X POST -H 'Content-Type: application/octet-stream' \
"https://uploads.github.com/repos/$REPO_URL/releases/$TAG_ID/assets?name=$TARGET_FILE&access_token=$REPO_TOKEN"
}

apply_version(){

	sed -i -E "s/(defreitas\\/bookmark-notes:)[0-9]+\.[0-9]+\.[0-9]+/\1$APP_VERSION/g" docker-compose.yml
	sed -i -E "s/download\\/([0-9]+\.[0-9]+\.[0-9]+)\\/(bk-api-[0-9]+\.[0-9]+\.[0-9]+)/download\\/$APP_VERSION\\/bk-api-$APP_VERSION/g" Dockerfile

}

case $1 in

	apply-version )

		apply_version

	;;

	build )

		echo "starting build"

		go get -v github.com/golang/dep/cmd/dep && \
		rm -rf ${BUILD_PATH}/* && \
		cd ${API_PATH} && \
		dep ensure -v && \
		go test -cover=false ./.../ && \
		go build -v -o ${BUILD_PATH}/bk-api && \
		sh -c "cd ${BUILD_PATH} && tar -acvf bk-api-$APP_VERSION.tgz *"

		echo "build success"

	;;

	upload-release )

		# setup user
		ACTUAL_USER=`git config user.name || echo ''`
		ACTUAL_EMAIL=`git config user.email || echo ''`
		AUTHOR="${ACTUAL_USER:-CI BOT} <${ACTUAL_EMAIL:-ci-bot@mageddo.com}>"
		REMOTE="https://${REPO_TOKEN}@github.com/${REPO_URL}.git"

		git checkout -b build_branch ${CURRENT_BRANCH}
		echo "> Repository added, currentBranch=${CURRENT_BRANCH}"

		git commit --author="$AUTHOR" -am "Releasing ${APP_VERSION}" # if there is nothing to commit the program will exits
		git tag ${APP_VERSION}
		git push "$REMOTE" "build_branch:${CURRENT_BRANCH}"
		git status
		echo "> Branch pushed - Branch $CURRENT_BRANCH"

		create_release

		echo "> Release created with id $TAG_ID"

		SOURCE_FILE="build/bk-api-$APP_VERSION.tgz"
		TARGET_FILE=bk-api-$APP_VERSION.tgz
		echo "> Source file hash"
		md5sum $SOURCE_FILE && ls -lha $SOURCE_FILE

		upload_file

	;;

esac
