#!/usr/bin/env bash
egrep -r "^# " entries/ | grep -vf titles_filter | sort | less
