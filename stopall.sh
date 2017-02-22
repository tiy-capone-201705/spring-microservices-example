#!/usr/bin/env bash
for job in `pgrep java`; do
    kill $job
done

