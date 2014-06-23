#!/bin/sh
export PATH=$PATH=${ANDROID_HOME}/tools
expect -c '
set timeout -1;
spawn /Users/ckelso/android-sdk-macosx/tools/android update sdk --filter platform-tools,android-19,extra-android-support --no-ui;
expect {
    "Do you accept the license" { exp_send "y\r" ; exp_continue }
    eof
}
'

