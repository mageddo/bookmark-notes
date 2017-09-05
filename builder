#!/bin/sh

set -e

CUR_DIR=$PWD

case $1 in

	pull-all )
		git pull
		for i in `git submodule | awk '{print $2}'`; do
				echo "pulling $i"
				cd $i
				git pull
				cd $CUR_DIR

		done;
	;;

	build )

		echo "starting build"
		VERSION=`cat VERSION`

		rm -rf $BUILD_PATH/* && \
		cd $APP_PATH && \
		go test -cover=false \
			./bk-api/.../ && \
		go build -v -o $BUILD_PATH/bk-api && \
		sh -c "cd $BUILD_PATH && tar -acvf bk-api-$VERSION.tgz *"

		echo "build success"

	;;

esac
