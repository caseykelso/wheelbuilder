#!/bin/sh
export PATH=$PATH=${ANDROID_HOME}/tools
expect -c '
set timeout -1;
spawn android  update sdk --no-ui --filter platform-tools,android-19,extra-android-support ;
expect {
    "Do you accept the license" { exp_send "y\r" ; exp_continue }
    eof
}
'

