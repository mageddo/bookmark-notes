#!/usr/bin/env bash

set -e

export JAVA_HOME=${_JAVA_HOME}
/app/builder validate-release || exit 0
/app/builder apply-version && /app/builder build && /app/builder upload-release

exec "$@"
