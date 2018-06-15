#!/usr/bin/env bash
ENTRIES="entries/*.md"
ENCRYPTED="encrypted/"

# Sanity checking
if [[ ! -e ${ENCRYPTED} ]]; then
    mkdir ${ENCRYPTED}
fi

for f in ${ENTRIES};
do
    enc="${ENCRYPTED}$(basename $f)"
    enc="${enc%.*}.txt"

    if [[ ! -e $enc ]]; then
        rot13 < $f > $enc
        printf "Encrypting $(basename $f)...\n"
    fi
done
