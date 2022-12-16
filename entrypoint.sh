#!/usr/bin/env bash

set -Eeuox pipefail

_is_sourced() {
	[ "${#FUNCNAME[@]}" -ge 2 ] \
		&& [ "${FUNCNAME[0]}" = '_is_sourced' ] \
		&& [ "${FUNCNAME[1]}" = 'source' ]
}

_main() {
	if [ "${1:0:1}" = '-' ]; then
		set -- server "$@"
	fi

	if [ "$1" = 'server' -a "$(id -u)" = '0' ]; then
		exec setpriv --reuid=iottah --regid=iottah --init-groups "$BASH_SOURCE" "$@"
	fi

	exec "$@"
}

if ! _is_sourced; then
	_main "$@"
fi
