/*
** kirk.c -- writes to a message queue
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

int trgovacid;
int papirid;

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
    printf("Trgovac je za stolom\n");
    printf("Pusac s papirom je za stolom\n");
    struct my_msgbuf buf;
    int msqid;
    key_t key;
    
    for(;;){
	    char text[]="Trgovac:Stavio sam duhan i sibice!";

	    key = 11111;
	    if ((papirid = msgget(key, 0600 | IPC_CREAT)) == -1) {
		perror("msgget");
		exit(1);
	    }
	    
	    memcpy(buf.mtext, text, strlen(text)+1);
	    buf.mtype = 1;
	    
	    if (msgsnd(papirid, (struct msgbuf *)&buf, strlen(text)+1, 0) == -1){
		    perror("msgsnd");
		    }
	    printf("Trgovac:Stavio sam duhan i sibice!\n");
	    key = 54321;
    
	    if ((trgovacid = msgget(key, 0600 | IPC_CREAT)) == -1) { /* connect to the queue */
		perror("msgget");
		exit(1);
	    }
	    
	    sigset(SIGINT, retreat);
	    if (msgrcv(trgovacid, (struct msgbuf *)&buf, sizeof(buf)-sizeof(long), 4, 0) == -1) {
            	perror("msgrcv");
            	exit(1);
        	}
        	
            printf("Trgovac: received: \"%s\". \nUzivaj!\n", buf.mtext);
	    sleep(1);
    }
    
    return 0;
}
