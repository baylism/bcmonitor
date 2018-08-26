#!/usr/bin/env bash

pod=$(kubectl get po | grep bcmonitor | awk '{print $1;}')
kubectl delete pod ${pod}
sleep 10
podnew=$(kubectl get po | grep bcmonitor | awk '{print $1;}')
kubectl logs -f ${podnew}