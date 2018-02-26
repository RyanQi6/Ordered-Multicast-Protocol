# Unicast-Multicast-Protocol
This is a project that implements a basic chat room based on total-ordering multicast and causal-ordering multicast under simulted transfer delay.

## Instruction:
#### 1.Compile the project and open several terminals in the path where config file exits.
#### 2.Lauch a process: `java main process_id`.
#### 3.unicast: `send target_id message`.
#### 4.multicast: `send message`.

Feel free to change the delay and process parameters in the config file.

## Introduction in theory:
Unicast Protocol gives the private channel between a pair of users.
Multicast Protocol gives a broadcast channel, in which a user sends a message and everyone in the chatroom can receive the message. There are two orderings for Multicast Protocol, Total Ordering and Causal Ordering. 

The Total Ordering refers to an message deliver ordering where all users keep the same for the receive order, that is, if user A sends message m1, m2, andm3, user B and user C would deliver the messages in a same specifc order, e.g. m2, m1, m3 (this order is necessarily the same with the orginal sending order because of the delay in transferring).

The Causal Ordering refers to an message deliver ordering where all users delivers the message consitent with the user's sending order, or specifically, the happen-before rule. e.g. If user A sends message m1, m2, and m3, then user B sends message m4, though it might be possible that user C receives the m4 even before m1 arrives, the deliver order of user C should still be m1, m2, m3, and m4.
