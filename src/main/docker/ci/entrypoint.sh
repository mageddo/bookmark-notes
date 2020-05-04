#!/usr/bin/env bash

set -e

/app/builder validate-release || exit 0
/app/builder apply-version && /app/builder build && /app/builder upload-release

exec "$@"
