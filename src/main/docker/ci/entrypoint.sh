#!/usr/bin/env bash

set -e

export JAVA_HOME="${GRAALVM_TARGET_DIR}/graalvm-ce-19.3.0/"
/app/builder validate-release
/app/builder apply-version && /app/builder build && /app/builder upload-release

exec "$@"
