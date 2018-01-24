#!/usr/bin/env bash

LAST="entries/$(ls -t1 entries | head -n 1)"
CURRENT="entries/$(date +%F).md"
EDITOR="nvim"

# Run vim
if [[ -e $CURRENT ]]; then
    $EDITOR $CURRENT
else
    $EDITOR $LAST $CURRENT -p
fi
