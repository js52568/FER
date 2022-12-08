/*
** spock.c -- reads from a message queue
*/

#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <time.h>
#include <errno.h>
#include <string.h>
#define _XOPEN_SOURCE 500
#include <signal.h>


struct my_msgbuf {
    long mtype;
    char mtext[200];
};

int papirid;
int trgovacid;

void retreat(int failure) 
{
    if (msgctl(trgovacid, IPC_RMID, NULL) == -1) {
        perror("msgctl");
        exit(1);
    }
    if (msgctl(papirid, IPC_RMID, NULL) == -1) {
        perror("msgctl");
        exit(1);
    }
    exit(0);
}

int main(void)
{
    struct my_msgbuf buf;
    key_t key;

    key = 11111;
    
    if ((papirid = msgget(key, 0600 | IPC_CREAT)) == -1) { /* connect to the queue */
        perror("msgget");
        exit(1);
    }
    
    sigset(SIGINT, retreat);
    
    for(;;) { /* Spock never quits to his captain! */
        if (msgrcv(papirid, (struct msgbuf *)&buf, sizeof(buf)-sizeof(long), 1, 0) == -1) {
            perror("msgrcv");
            exit(1);
        }
        printf("Papir pusac: received: \"%s\". \nUzet cu!\n", buf.mtext);
        char text[]="Papir pusac:Uzimam sastojke,motam i pusim";

	    key = 54321;
	    if ((trgovacid = msgget(key, 0600 | IPC_CREAT)) == -1) {
		perror("msgget");
		exit(1);
	    }
	    
	    memcpy(buf.mtext, text, strlen(text)+1);
	    buf.mtype = 4;
	    
	    if (msgsnd(trgovacid, (struct msgbuf *)&buf, strlen(text)+1, 0) == -1){
		    perror("msgsnd");
		    }
	    sleep(15);
       
    }

    return 0;
}
