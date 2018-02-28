# Unicast-Multicast-Protocol
This is a project that implements a basic chat room using TCP based on total-ordering multicast and causal-ordering multicast under simulted transfer delay.

## Instruction:
#### 1.Compile the project, move the 'configFile' to the path where main.class exits, and open several terminals under that path.
#### 2.Lauch the processes(users) in the terminals respectively: `java   main   process_id`.
#### 3.unicast: `send   target_id   message`.
#### 4.multicast: `msend   message`.

Format of the config file is :
  ```
  min_delay(ms)  max_delay(ms)
  User_ID0  IP0  port0
  User_ID1  IP1  port1
  ...      ...   ...
```
Feel free to change the delay and process parameters in the config file.

## Introduction in theory:
Unicast Protocol gives the private channel between a pair of users. It will simulate the network delay if you are just running several processes on a single computer.
Multicast Protocol gives a broadcast channel, in which a user sends a message and everyone in the chatroom can receive the message. There are two orderings for Multicast Protocol, Total Ordering and Causal Ordering. 

The Total Ordering refers to an message deliver ordering where all users keep the same for the receive order, that is, if message m1, m2, m3 are sent and one user received in the order of m2, m1, m3, then all other users will also deliver the messages in the same order m2, m1, m3. This order is not necessarily the same with the orginal sending order because of the delay in network environment.

The Causal Ordering refers to an message deliver ordering where all users delivers the message consitent with the happen-before relation. If m1 happen before m2, then all user must deliver m1 before delivering m2. E.g. If user A sends message m1, m2, and m3, and user B delivered m1 and m2 before sending message m4, then for all other processes the relative order of m1, m2 and m3 must be preserved and m4 must be delivered after m1 and m2. For the detailed definition of happen-before relation, check https://en.wikipedia.org/wiki/Happened-before
