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

		rm -rf build/ && \
		mkdir -p build/ && \
		cd bk-api/ && \
		git submodule init && \
		git submodule update && \
		cd src/ && \
		go test -cover=false \
			./github.com/mageddo/bookmark-notes/.../ && \
		go build -v -o ../../build/bk-api && \
		cd ../../build/ && \
		tar -cvf bk-api-$VERSION.tgz * && \
		cd ../

		echo "build success"

	;;

esac
