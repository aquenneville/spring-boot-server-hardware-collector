#!/bin/bash

function test_command() {
    exit_code=$1
	component=$2  
    if [ $exit_code -ne 0 ]; then
        echo "The command return failed for component $component"
    else  
	    echo "success - test passed!"
    fi
}

lscpu|grep -oP "(Model name:).*"|grep -oP "[^(Model name:)].*";
test_command($?, "cpu"); 
	
lsblk -l -d -n | grep -P "^(sd|hd).*"
test_command($?, "disk size");

df -h -T -t ext4
test_command($?, "disk usage");

/sbin/udevadm info --query=property --name=sda | grep "ID_SERIAL=.*" | grep -o "[^(ID_SERIAL=)].*"
test_command($?, "disk serial");

cat /sys/devices/virtual/dmi/id/board_name && cat /sys/devices/virtual/dmi/id/board_vendor
test_command($?, "motherboard);

vmstat -s -S M | grep "total memory" | grep -oP "\d+ M"
test_command($?, "memory");

hostname
test_command($?, "hostname");

ifconfig | grep -oP ".*inet \d+.\d+.\d+.\d+" | grep "[^(inet 127\.0\D.0\.1)].*" | grep -oP "[^(inet )].*"
test_command($?, "ip");

ls /sys/class/net/ | xargs -n1 bash -c 'echo $0 $(cat /sys/class/net/$0/address)'
test_command($?, "mac address");

lsb_release -a
test_command($?, "os name");