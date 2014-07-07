#!/bin/sh
#http://stackoverflow.com/questions/6854127/filter-logcat-to-get-only-the-messages-from-my-application-in-android
adb logcat | grep `adb shell ps | grep com.kineticsproject.spokecalculator.android | cut -c10-15`

